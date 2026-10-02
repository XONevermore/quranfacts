package com.example.quranfacts.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Share
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quranfacts.audio.AudioHub
import com.example.quranfacts.data.TranslationInfo
import com.example.quranfacts.data.Verse
import com.example.quranfacts.ui.components.OrnamentDivider
import com.example.quranfacts.ui.components.StarOrnament
import com.example.quranfacts.ui.theme.qf
import com.example.quranfacts.util.copyText
import com.example.quranfacts.util.openUrl
import com.example.quranfacts.util.shareText
import com.example.quranfacts.util.verseShareText
import androidx.compose.ui.res.stringResource
import com.example.quranfacts.R

@Composable
fun VerseCard(
    verse: Verse,
    language: String,
    translation: TranslationInfo?,
    arabicSize: Float,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val gold = MaterialTheme.qf.gold
    val text = verse.tr[language] ?: verse.tr["en"].orEmpty()
    val rtlTranslation = language == "ur"

    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = BorderStroke(1.dp, gold.copy(alpha = 0.28f)),
    ) {
        Column(Modifier.padding(start = 20.dp, end = 8.dp, top = 8.dp, bottom = 20.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StarOrnament(12.dp, gold)
                    Text(
                        "${verse.surahEn.uppercase()} · ${verse.ref}",
                        style = MaterialTheme.typography.labelMedium,
                        color = gold,
                    )
                }
                Row {
                    IconButton({ context.copyText("${context.getString(R.string.quran_word)} ${verse.ref}", context.verseShareText(verse, language)) }) {
                        Icon(Icons.Rounded.ContentCopy, stringResource(R.string.cd_copy_verse), Modifier.size(19.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton({ context.shareText(context.verseShareText(verse, language)) }) {
                        Icon(Icons.Rounded.Share, stringResource(R.string.cd_share_verse), Modifier.size(19.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton({ context.openUrl(verse.quranComUrl) }) {
                        Icon(Icons.AutoMirrored.Rounded.OpenInNew, stringResource(R.string.cd_open_quran), Modifier.size(19.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            Column(Modifier.padding(end = 12.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Text(
                        verse.ar,
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            fontSize = arabicSize.sp,
                            lineHeight = (arabicSize * 1.95f).sp,
                            textDirection = TextDirection.Rtl,
                            textAlign = TextAlign.Start,
                        ),
                        color = MaterialTheme.qf.arabicInk,
                    )
                }
                OrnamentDivider(gold.copy(alpha = 0.8f))
                CompositionLocalProvider(
                    LocalLayoutDirection provides if (rtlTranslation) LayoutDirection.Rtl else LayoutDirection.Ltr,
                ) {
                    Text(
                        text,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                if (translation != null) {
                    Text(
                        translation.translator,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                verse.audio?.let { ListenButton(it) }
            }
        }
    }
}

/** Plays or pauses the recitation of this verse (Mishary Alafasy). */
@Composable
private fun ListenButton(url: String) {
    val context = LocalContext.current
    val audio by AudioHub.state.collectAsStateWithLifecycle()
    val mine = audio.url == url
    val loading = mine && audio.status == AudioHub.Status.Loading
    val playing = mine && audio.status == AudioHub.Status.Playing
    val gold = MaterialTheme.qf.gold

    Row(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(gold.copy(alpha = 0.14f))
            .clickable { AudioHub.toggle(context, url) }
            .padding(start = 8.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        androidx.compose.foundation.layout.Box(
            Modifier.size(32.dp).clip(androidx.compose.foundation.shape.CircleShape).background(gold),
            contentAlignment = Alignment.Center,
        ) {
            when {
                loading -> CircularProgressIndicator(Modifier.size(18.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
                playing -> Icon(Icons.Rounded.Pause, stringResource(R.string.cd_pause_recitation), Modifier.size(20.dp), tint = MaterialTheme.colorScheme.onPrimary)
                else -> Icon(Icons.Rounded.PlayArrow, stringResource(R.string.cd_listen_recitation), Modifier.size(22.dp), tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
        Column {
            Text(
                stringResource(
                    if (mine && audio.failed) R.string.audio_failed else if (playing) R.string.audio_playing else R.string.audio_listen,
                ),
                style = MaterialTheme.typography.labelLarge,
                color = gold,
            )
            Text(
                stringResource(R.string.recited_by),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
