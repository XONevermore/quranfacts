package com.example.quranfacts

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import com.example.quranfacts.monetization.Premium
import com.example.quranfacts.monetization.Unlocks
import okhttp3.OkHttpClient
import okio.Path.Companion.toOkioPath

object Http {
    /** Wikimedia asks clients to identify themselves; anonymous "okhttp/x" gets throttled first. */
    const val USER_AGENT = "QuranScience/1.0 (Android; educational app)"

    /** One client for images and video, so both present the same identity to Wikimedia. */
    val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .header("User-Agent", USER_AGENT)
                        .header("Accept", "*/*")
                        .build(),
                )
            }
            .build()
    }
}

class QuranFactsApp : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        // Connect to Google Play Billing early, so a subscriber's ad-free status is known before any ad could show.
        Premium.init(this)
        Unlocks.init(this)
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader =
        ImageLoader.Builder(context)
            .components { add(OkHttpNetworkFetcherFactory(callFactory = { Http.client })) }
            .memoryCache { MemoryCache.Builder().maxSizePercent(context, 0.25).build() }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("images").toOkioPath())
                    .maxSizeBytes(300L * 1024 * 1024)
                    .build()
            }
            .crossfade(true)
            .build()
}
