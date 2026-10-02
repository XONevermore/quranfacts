package com.example.quranfacts.monetization

import android.content.Context
import com.example.quranfacts.data.Prefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Optional extras that a free user can open by choosing to watch one short rewarded ad, instead of buying Premium.
 * Never forced: an ad is only ever shown after the user taps the unlock button. One ad opens an extra for
 * [UNLOCK_MS], and a separate "ad break" switches off the occasional full-screen ads for [AD_BREAK_MS].
 * Premium subscribers have everything open all the time.
 */
object Unlocks {
    enum class Feature(val key: String) {
        /** The interactive speed-of-light calculator on the "Speed of Light" fact. */
        SpeedLab("speed_lab"),

        /** Sharing a fact as a picture (sharing as text is always free). */
        ShareImage("share_image"),

        /** No full-screen ads for a while. */
        AdBreak("ad_break"),
    }

    const val UNLOCK_MS = 24 * 3_600_000L
    const val AD_BREAK_MS = 2 * 3_600_000L

    private var prefs: Prefs? = null
    private val _until = MutableStateFlow<Map<Feature, Long>>(emptyMap())

    /** When each unlock ends; observe it so screens update when a reward arrives. */
    val until: StateFlow<Map<Feature, Long>> = _until.asStateFlow()

    fun init(context: Context) {
        if (prefs != null) return
        val p = Prefs(context.applicationContext)
        prefs = p
        _until.value = Feature.entries.associateWith { p.unlockUntil(it.key) }
    }

    /** True for subscribers and while a reward is still running. [AdBreak] is not "open" for subscribers: it is moot. */
    fun isOpen(feature: Feature, now: Long = System.currentTimeMillis()): Boolean =
        Premium.state.value.active || (_until.value[feature] ?: 0L) > now

    fun adBreakActive(now: Long = System.currentTimeMillis()): Boolean = (_until.value[Feature.AdBreak] ?: 0L) > now

    fun grant(feature: Feature, now: Long = System.currentTimeMillis()) {
        val ends = now + if (feature == Feature.AdBreak) AD_BREAK_MS else UNLOCK_MS
        prefs?.setUnlockUntil(feature.key, ends)
        _until.value = _until.value + (feature to ends)
    }
}
