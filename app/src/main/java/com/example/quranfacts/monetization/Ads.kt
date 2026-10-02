package com.example.quranfacts.monetization

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.quranfacts.BuildConfig
import com.example.quranfacts.audio.AudioHub
import com.example.quranfacts.data.Prefs
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Full-screen ads for free users, shown now and then when a fact is opened (see [AdPolicy]).
 *
 * Order matters: first Google's consent form (UMP) where the law requires one (EEA, UK, Switzerland and some
 * US states), then the Mobile Ads SDK, and only then ad requests. Subscribers skip all of it.
 * Ads are limited to the "G" (general audiences) content rating, which suits a Quran app.
 */
object Ads {
    private const val TAG = "QF-ads"

    private val started = AtomicBoolean(false)
    private var interstitial: InterstitialAd? = null
    private var loading = false
    private var rewarded: RewardedAd? = null
    private var rewardedLoading = false

    private val _privacyOptionsRequired = MutableStateFlow(false)
    /** True where the user must be able to change their consent later (shown as "Privacy choices" in More). */
    val privacyOptionsRequired: StateFlow<Boolean> = _privacyOptionsRequired.asStateFlow()

    /** Call from MainActivity.onCreate. Shows the consent form if needed, then starts ads if allowed. */
    fun gatherConsent(activity: Activity) {
        if (Premium.state.value.active) return
        val info = UserMessagingPlatform.getConsentInformation(activity)
        val params = ConsentRequestParameters.Builder().apply {
            // Debug builds only: pretend to be in the EEA to see the consent form, e.g.
            //   adb shell am start -n com.example.quranfacts/.MainActivity --ez debugConsentEea true
            if (BuildConfig.DEBUG && activity.intent.getBooleanExtra("debugConsentEea", false)) {
                info.reset()
                setConsentDebugSettings(
                    ConsentDebugSettings.Builder(activity)
                        .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                        .setForceTesting(true)
                        .build(),
                )
            }
        }.build()
        info.requestConsentInfoUpdate(
            activity,
            params,
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { error ->
                    if (error != null) Log.w(TAG, "Consent form: ${error.message}")
                    _privacyOptionsRequired.value =
                        info.privacyOptionsRequirementStatus == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED
                    if (info.canRequestAds()) start(activity)
                }
            },
            { error -> Log.w(TAG, "Consent info update: ${error.message}") },
        )
        // Consent given in an earlier session is enough to start right away, while the update runs.
        if (info.canRequestAds()) start(activity)
    }

    /** Lets the user change their ad consent choices (required where [privacyOptionsRequired] is true). */
    fun showPrivacyOptions(activity: Activity) {
        UserMessagingPlatform.showPrivacyOptionsForm(activity) { error ->
            if (error != null) Log.w(TAG, "Privacy options: ${error.message}")
        }
    }

    private fun start(context: Context) {
        if (!started.compareAndSet(false, true)) return
        val app = context.applicationContext
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_G)
                .build(),
        )
        CoroutineScope(Dispatchers.IO).launch {
            MobileAds.initialize(app)
            withContext(Dispatchers.Main) { preload(app) }
        }
    }

    private fun preload(context: Context) {
        preloadRewarded(context)
        if (!started.get() || loading || interstitial != null || Premium.state.value.active) return
        loading = true
        InterstitialAd.load(
            context,
            BuildConfig.ADMOB_INTERSTITIAL_ID,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitial = ad
                    loading = false
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.w(TAG, "Interstitial failed to load: ${error.message}")
                    loading = false
                }
            },
        )
    }

    private fun preloadRewarded(context: Context) {
        if (!started.get() || rewardedLoading || rewarded != null || Premium.state.value.active) return
        rewardedLoading = true
        RewardedAd.load(
            context,
            BuildConfig.ADMOB_REWARDED_ID,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewarded = ad
                    rewardedLoading = false
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.w(TAG, "Rewarded ad failed to load: ${error.message}")
                    rewardedLoading = false
                }
            },
        )
    }

    /**
     * Shows a rewarded ad that the user asked for. [onReward] runs only if they watch it to the end; [onUnavailable]
     * runs at once if no ad is ready (no consent yet, offline, no fill), so the UI can say so instead of waiting.
     */
    fun showRewarded(activity: Activity, onReward: () -> Unit, onUnavailable: () -> Unit) {
        val ad = rewarded
        if (ad == null) {
            preloadRewarded(activity.applicationContext)
            onUnavailable()
            return
        }
        rewarded = null
        var earned = false
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                preloadRewarded(activity.applicationContext)
                if (earned) onReward()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                Log.w(TAG, "Rewarded ad failed to show: ${error.message}")
                preloadRewarded(activity.applicationContext)
                onUnavailable()
            }
        }
        ad.show(activity) { earned = true }
    }

    /**
     * Call when the user opens a fact. Counts the visit and, if [AdPolicy] says it is time, shows the ad over the
     * fact that is opening. Never interrupts a recitation that is playing, and never shows to subscribers.
     */
    fun onFactOpened(activity: Activity) {
        if (Premium.state.value.active || Unlocks.adBreakActive()) return
        val prefs = Prefs(activity)
        val count = prefs.factsSinceAd + 1
        prefs.factsSinceAd = count
        val ad = interstitial
        if (ad == null) {
            preload(activity.applicationContext)
            return
        }
        val now = System.currentTimeMillis()
        if (AudioHub.state.value.status != AudioHub.Status.Idle || !AdPolicy.shouldShow(count, prefs.lastAdAt, now)) return

        interstitial = null
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                prefs.factsSinceAd = 0
                prefs.lastAdAt = System.currentTimeMillis()
            }

            override fun onAdDismissedFullScreenContent() = preload(activity.applicationContext)

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                Log.w(TAG, "Interstitial failed to show: ${error.message}")
                preload(activity.applicationContext)
            }
        }
        ad.show(activity)
    }
}
