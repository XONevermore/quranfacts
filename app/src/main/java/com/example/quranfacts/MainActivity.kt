package com.example.quranfacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranfacts.audio.AudioHub
import com.example.quranfacts.monetization.Ads
import com.example.quranfacts.monetization.Premium
import com.example.quranfacts.ui.AppRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: AppViewModel = viewModel()
            AppRoot(vm)
        }
        // Free users: ask for ad consent where the law requires it, then start ads. Subscribers skip this.
        Ads.gatherConsent(this)
        if (BuildConfig.DEBUG && intent.getBooleanExtra("debugDemoPlans", false)) Premium.showDemoPlans()
    }

    override fun onResume() {
        super.onResume()
        // A subscription can start, renew or end outside the app (in Google Play), so check on every return.
        Premium.refreshAsync()
    }

    override fun onStop() {
        super.onStop()
        AudioHub.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) AudioHub.release()
    }
}
