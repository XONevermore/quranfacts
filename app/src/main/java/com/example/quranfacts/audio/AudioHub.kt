@file:OptIn(UnstableApi::class)

package com.example.quranfacts.audio

import android.content.Context
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * One audio player for the whole app (verse recitations and science clips), so two things can
 * never talk over each other. Call from the main thread.
 */
object AudioHub {
    enum class Status { Idle, Loading, Playing }

    data class State(val url: String? = null, val status: Status = Status.Idle, val failed: Boolean = false)

    private var player: ExoPlayer? = null
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    /** Starts [url], or stops it if it is already the current item. */
    fun toggle(context: Context, url: String) {
        if (_state.value.url == url && !_state.value.failed) {
            stop()
            return
        }
        val p = ensurePlayer(context)
        p.setMediaItem(MediaItem.fromUri(url))
        p.prepare()
        p.playWhenReady = true
        _state.value = State(url, Status.Loading)
    }

    fun stop() {
        player?.let {
            it.stop()
            it.clearMediaItems()
        }
        _state.value = State()
    }

    fun release() {
        player?.release()
        player = null
        _state.value = State()
    }

    private fun ensurePlayer(context: Context): ExoPlayer =
        player ?: ExoPlayer.Builder(context.applicationContext)
            .setMediaSourceFactory(mediaSourceFactory())
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_SPEECH)
                    .build(),
                /* handleAudioFocus = */ true,
            )
            .setHandleAudioBecomingNoisy(true)
            .build()
            .also { p ->
                p.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        val url = _state.value.url ?: return
                        when (playbackState) {
                            Player.STATE_BUFFERING -> _state.value = State(url, Status.Loading)
                            Player.STATE_READY -> _state.value = State(url, Status.Playing)
                            Player.STATE_ENDED -> _state.value = State()
                        }
                    }

                    override fun onPlayerError(error: PlaybackException) {
                        Log.w("QF-audio", "Playback failed for ${_state.value.url}", error)
                        _state.value = State(_state.value.url, Status.Idle, failed = true)
                    }
                })
                player = p
            }
}
