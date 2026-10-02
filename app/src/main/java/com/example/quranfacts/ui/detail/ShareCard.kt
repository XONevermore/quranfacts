package com.example.quranfacts.ui.detail

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.automirrored.rounded.ShortText
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.allowHardware
import com.example.quranfacts.data.ClaimType
import com.example.quranfacts.data.Fact
import com.example.quranfacts.ui.components.ClaimBadge
import com.example.quranfacts.ui.components.StarOrnament
import com.example.quranfacts.ui.components.Starfield
import com.example.quranfacts.ui.theme.Gold
import com.example.quranfacts.ui.theme.Midnight
import com.example.quranfacts.ui.theme.Serif
import com.example.quranfacts.util.grouped
import com.example.quranfacts.util.shareImage
import com.example.quranfacts.util.shareText
import kotlinx.coroutines.launch
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.pluralStringResource
import com.example.quranfacts.R
import com.example.quranfacts.monetization.Unlocks
import com.example.quranfacts.ui.components.isUnlocked
import com.example.quranfacts.ui.components.rememberUnlocker

/**
 * Preview of a fact as a picture (verse, translation, honesty label, discovery), with buttons to share it
 * as an image or as plain text. The card is drawn into a graphics layer so what you see is what is sent.
 */
@Composable
fun ShareFactDialog(
    fact: Fact,
    claim: ClaimType?,
    language: String,
    shareText: String,
    onDismiss: () -> Unit,
    onPremium: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val layer = rememberGraphicsLayer()
    var busy by remember { mutableStateOf(false) }
    val imageUnlocked = isUnlocked(Unlocks.Feature.ShareImage)
    val unlockImage = rememberUnlocker(Unlocks.Feature.ShareImage)

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Column(
            Modifier
                .padding(20.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(stringResource(R.string.share_title), style = MaterialTheme.typography.titleLarge)
            Box(
                Modifier
                    .width(300.dp)
                    .aspectRatio(4f / 5f)
                    .clip(RoundedCornerShape(18.dp))
                    .drawWithContent {
                        layer.record { this@drawWithContent.drawContent() }
                        drawLayer(layer)
                    },
            ) {
                ShareCard(fact, claim, language)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(onClick = { context.shareText(shareText); onDismiss() }) {
                    Icon(Icons.AutoMirrored.Rounded.ShortText, null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.share_text))
                }
                Button(
                    enabled = !busy,
                    onClick = {
                        val send = {
                            busy = true
                            scope.launch {
                            val bitmap = layer.toImageBitmap().asAndroidBitmap().let {
                                // Hardware bitmaps cannot be compressed; copy into ordinary memory first.
                                if (Build.VERSION.SDK_INT >= 26 && it.config == Bitmap.Config.HARDWARE) {
                                    it.copy(Bitmap.Config.ARGB_8888, false)
                                } else {
                                    it
                                }
                            }
                            context.shareImage(bitmap, "quran-fact-${fact.id}.png", shareText)
                            busy = false
                            onDismiss()
                            }
                        }
                        if (imageUnlocked) send() else unlockImage { send() }
                    },
                ) {
                    Icon(Icons.Rounded.Image, null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(if (imageUnlocked) R.string.share_image else R.string.share_image_locked))
                }
            }
            if (!imageUnlocked) {
                Text(
                    stringResource(R.string.share_image_hint),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
                TextButton(onClick = { onDismiss(); onPremium() }) { Text(stringResource(R.string.unlock_premium)) }
            }
        }
    }
}

/** The picture itself. Always dark, whatever the app theme, so it looks the same wherever it is posted. */
@Composable
private fun ShareCard(fact: Fact, claim: ClaimType?, language: String) {
    val context = LocalContext.current
    val verse = fact.verses.first()
    val translation = verse.tr[language] ?: verse.tr["en"].orEmpty()
    val ink = Color(0xFFF6E6BC)

    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1B2A57), Color(0xFF0F1734), Midnight))),
    ) {
        Starfield(Modifier.fillMaxSize(), count = 60)
        fact.heroImage?.let { hero ->
            Box(Modifier.fillMaxWidth().aspectRatio(16f / 9f)) {
                AsyncImage(
                    // Software bitmaps, so the picture can be drawn into the shareable image on every Android version.
                    model = ImageRequest.Builder(context).data(hero.url).allowHardware(false).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    // Fade the photo itself out at the bottom so it melts into the card, with no hard edge.
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                        .drawWithContent {
                            drawContent()
                            drawRect(
                                Brush.verticalGradient(0.5f to Color.Black, 1f to Color.Transparent),
                                blendMode = BlendMode.DstIn,
                            )
                        },
                )
                Box(
                    Modifier.fillMaxSize().background(
                        Brush.verticalGradient(
                            0f to Color.Black.copy(alpha = 0.40f),
                            0.30f to Color.Black.copy(alpha = 0.10f),
                            0.62f to Color(0xFF14204A).copy(alpha = 0.70f),
                            1f to Color.Transparent,
                        ),
                    ),
                )
            }
        }
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.Black.copy(alpha = 0.55f))
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                StarOrnament(11.dp, Gold)
                Text(stringResource(R.string.app_name).uppercase(), style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 2.sp), color = Gold)
            }
            Box(Modifier.weight(0.55f))
            Text(
                fact.title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = Serif,
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(Color.Black.copy(alpha = 0.7f), blurRadius = 12f),
                ),
                color = Color.White,
                maxLines = 2,
            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                BasicText(
                    verse.ar,
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    style = TextStyle(color = ink, lineHeight = 1.7.em, textDirection = TextDirection.Rtl, textAlign = TextAlign.Start),
                    autoSize = TextAutoSize.StepBased(minFontSize = 10.sp, maxFontSize = 24.sp, stepSize = 1.sp),
                )
            }
            BasicText(
                stringResource(R.string.share_card_ref, translation, verse.ref),
                modifier = Modifier.fillMaxWidth().weight(0.8f),
                style = TextStyle(
                    color = Color.White.copy(alpha = 0.88f),
                    lineHeight = 1.35.em,
                    textDirection = if (language == "ur") TextDirection.Rtl else TextDirection.Ltr,
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 8.sp, maxFontSize = 15.sp, stepSize = 0.5.sp),
            )
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                if (claim != null) ClaimBadge(claim, onImage = true, explainOnClick = false, compact = true)
                fact.discovery?.let { d ->
                    val later = d.yearsAfterRevelation?.let { "\n" + pluralStringResource(R.plurals.years_later, it, it.grouped()) }.orEmpty()
                    Text(
                        stringResource(R.string.share_found, d.whenText) + later,
                        style = MaterialTheme.typography.labelSmall,
                        color = Gold,
                        textAlign = TextAlign.End,
                        maxLines = 2,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}
