@file:OptIn(UnstableApi::class)

package com.example.quranfacts.audio

import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.quranfacts.Http

/**
 * Media source factory shared by video and audio.
 *
 * ExoPlayer adds an "Icy-MetaData" header to every progressive load. Wikimedia's edge answers
 * that with HTTP 429, so it is removed before the request goes out. Everything goes through the
 * shared OkHttp client so images, video and audio all present the same identity.
 */
fun mediaSourceFactory(): DefaultMediaSourceFactory {
    val http = OkHttpDataSource.Factory(Http.client)
    val withoutIcy = ResolvingDataSource.Factory(http) { spec ->
        spec.withRequestHeaders(spec.httpRequestHeaders.filterKeys { !it.equals("Icy-MetaData", ignoreCase = true) })
    }
    return DefaultMediaSourceFactory(withoutIcy)
}
