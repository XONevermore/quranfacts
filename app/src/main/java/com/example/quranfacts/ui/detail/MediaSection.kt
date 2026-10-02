@file:OptIn(UnstableApi::class)

package com.example.quranfacts.ui.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage
import com.example.quranfacts.audio.AudioHub
import com.example.quranfacts.audio.mediaSourceFactory
import com.example.quranfacts.data.Media
import com.example.quranfacts.ui.components.CoverImage
import com.example.quranfacts.ui.components.StarOrnament
import com.example.quranfacts.util.openUrl
import androidx.compose.ui.res.stringResource
import com.example.quranfacts.R

private fun Media.creditLine(): String {
    val who = credit.ifBlank { "Wikimedia Commons" }
    return listOf(who, license, "Wikimedia Commons").filter { it.isNotBlank() }.distinct().joinToString(" · ")
}

private fun formatDuration(seconds: Int): String = "%d:%02d".format(seconds / 60, seconds % 60)

/** A photo, a video or an audio clip in a rounded 16:10 frame. */
@Composable
fun MediaTile(
    media: Media,
    accent: Color,
    isCurrent: Boolean,
    onOpenImage: (Media) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .fillMaxWidth()
            .aspectRatio(16f / 10f)
            .clip(RoundedCornerShape(22.dp)),
    ) {
        when {
            media.isVideo -> VideoTile(media, accent, isCurrent)
            media.isAudio -> AudioTile(media, accent, isCurrent)
            else -> CoverImage(
                url = media.big,
                accent = accent,
                modifier = Modifier.fillMaxSize().clickable { onOpenImage(media) },
                contentDescription = media.caption,
            )
        }
    }
}

/** Caption plus a tappable credit line that opens the file's page on Wikimedia Commons. */
@Composable
fun MediaCredit(media: Media, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(media.caption, style = MaterialTheme.typography.bodyMedium)
        Text(
            media.creditLine(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { context.openUrl(media.page) },
        )
    }
}

@Composable
fun MediaCarousel(media: List<Media>, accent: Color, modifier: Modifier = Modifier) {
    val pager = rememberPagerState { media.size }
    var viewing by remember { mutableStateOf<Media?>(null) }

    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HorizontalPager(
            state = pager,
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSpacing = 12.dp,
        ) { page ->
            MediaTile(media[page], accent, isCurrent = pager.currentPage == page, onOpenImage = { viewing = it })
        }
        MediaCredit(media[pager.currentPage.coerceIn(0, media.lastIndex)], Modifier.padding(horizontal = 20.dp))
        if (media.size > 1) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                media.indices.forEach { i ->
                    val active = i == pager.currentPage
                    Box(
                        Modifier
                            .padding(horizontal = 3.dp)
                            .size(if (active) 8.dp else 6.dp)
                            .clip(CircleShape)
                            .background(
                                if (active) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outline,
                            ),
                    )
                }
            }
        }
    }
    viewing?.let { ImageViewer(it) { viewing = null } }
}

@Composable
private fun VideoTile(media: Media, accent: Color, isCurrent: Boolean) {
    var playing by remember(media.url) { mutableStateOf(false) }
    LaunchedEffect(isCurrent) { if (!isCurrent) playing = false }

    if (playing) {
        VideoPlayer(media)
    } else {
        Box(Modifier.fillMaxSize().clickable { playing = true }) {
            CoverImage(url = media.big, accent = accent, modifier = Modifier.fillMaxSize(), contentDescription = media.caption)
            Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.25f)))
            Box(
                Modifier
                    .align(Alignment.Center)
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Rounded.PlayArrow, stringResource(R.string.cd_play_video), tint = Color.White, modifier = Modifier.size(38.dp))
            }
            if (media.duration > 0) {
                Text(
                    formatDuration(media.duration),
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black.copy(alpha = 0.6f))
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                )
            }
        }
    }
}

/** An audio clip: no picture, so it gets a starfield-toned card with a big play button. */
@Composable
private fun AudioTile(media: Media, accent: Color, isCurrent: Boolean) {
    val context = LocalContext.current
    val audio by AudioHub.state.collectAsStateWithLifecycle()
    val mine = audio.url == media.url
    LaunchedEffect(isCurrent) { if (!isCurrent && mine) AudioHub.stop() }

    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(accent.copy(alpha = 0.55f), Color(0xFF0F1734))))
            .clickable { AudioHub.toggle(context, media.url) },
        contentAlignment = Alignment.Center,
    ) {
        StarOrnament(120.dp, Color.White.copy(alpha = 0.08f), outlined = true)
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                Modifier.size(68.dp).clip(CircleShape).background(Color.Black.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center,
            ) {
                when {
                    mine && audio.status == AudioHub.Status.Loading ->
                        CircularProgressIndicator(Modifier.size(30.dp), color = Color.White, strokeWidth = 3.dp)
                    mine && audio.status == AudioHub.Status.Playing ->
                        Icon(Icons.Rounded.Pause, stringResource(R.string.cd_pause), tint = Color.White, modifier = Modifier.size(38.dp))
                    else -> Icon(Icons.Rounded.PlayArrow, stringResource(R.string.cd_play_audio), tint = Color.White, modifier = Modifier.size(38.dp))
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Rounded.GraphicEq, null, tint = Color.White.copy(alpha = 0.85f), modifier = Modifier.size(18.dp))
                Text(
                    if (mine && audio.failed) stringResource(R.string.audio_clip_failed) else stringResource(R.string.audio_label) + if (media.duration > 0) " · ${formatDuration(media.duration)}" else "",
                    color = Color.White.copy(alpha = 0.85f),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

/** Inline ExoPlayer. It is only created after a tap, so browsing the app never streams video. */
@Composable
private fun VideoPlayer(media: Media) {
    val context = LocalContext.current
    var failed by remember(media.url) { mutableStateOf(false) }
    val player = remember(media.url) {
        // Stop any recitation first so the two never overlap.
        AudioHub.stop()
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(mediaSourceFactory())
            .build()
            .apply {
                setMediaItem(MediaItem.fromUri(media.url))
                prepare()
                playWhenReady = true
            }
    }
    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                (error.cause as? HttpDataSource.InvalidResponseCodeException)?.let {
                    Log.w("QF-video", "HTTP ${it.responseCode} for ${media.url}")
                }
                failed = true
            }
        }
        player.addListener(listener)
        onDispose {
            player.removeListener(listener)
            player.release()
        }
    }
    if (failed) {
        Box(Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(stringResource(R.string.video_unplayable), color = Color.White, style = MaterialTheme.typography.bodyMedium)
                TextButton({ context.openUrl(media.page) }) { Text(stringResource(R.string.open_commons)) }
            }
        }
    } else {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    this.player = player
                    useController = true
                    setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                }
            },
            modifier = Modifier.fillMaxSize().background(Color.Black),
        )
    }
}

/** Full-screen photo with pinch-to-zoom, drag and double-tap. */
@Composable
fun ImageViewer(media: Media, onClose: () -> Unit) {
    val context = LocalContext.current
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false),
    ) {
        Box(Modifier.fillMaxSize().background(Color.Black)) {
            AsyncImage(
                model = media.big,
                contentDescription = media.caption,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onDoubleTap = {
                            if (scale > 1f) {
                                scale = 1f; offset = Offset.Zero
                            } else scale = 2.5f
                        })
                    }
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = (scale * zoom).coerceIn(1f, 6f)
                            offset = if (scale > 1f) offset + pan else Offset.Zero
                        }
                    }
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    },
            )
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f)),
            ) {
                Icon(Icons.Rounded.Close, stringResource(R.string.cd_close), tint = Color.White)
            }
            Column(
                Modifier
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .navigationBarsPadding()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(media.caption, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                Text(
                    media.creditLine(),
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { context.openUrl(media.page) },
                )
            }
        }
    }
}
