@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.quranfacts.ui.more

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quranfacts.data.FactsDoc
import com.example.quranfacts.data.ThemeMode
import com.example.quranfacts.monetization.Ads
import com.example.quranfacts.monetization.Premium
import com.example.quranfacts.monetization.Unlocks
import com.example.quranfacts.ui.components.rememberUnlocker
import com.example.quranfacts.ui.components.ClaimLegend
import com.example.quranfacts.ui.components.qfSegmentedColors
import com.example.quranfacts.ui.components.StarOrnament
import com.example.quranfacts.ui.theme.qf
import androidx.compose.ui.res.stringResource
import com.example.quranfacts.R
import com.example.quranfacts.util.PRIVACY_POLICY_URL
import com.example.quranfacts.util.openUrl

@Composable
fun MoreScreen(
    doc: FactsDoc,
    language: String,
    arabicSize: Float,
    theme: ThemeMode,
    onLanguage: (String) -> Unit,
    onArabicSize: (Float) -> Unit,
    onArabicSizeDone: () -> Unit,
    onTheme: (ThemeMode) -> Unit,
    onPremium: () -> Unit,
) {
    val sample = doc.facts.first().verses.first()
    val premium by Premium.state.collectAsStateWithLifecycle()
    val privacyChoices by Ads.privacyOptionsRequired.collectAsStateWithLifecycle()
    val activity = LocalActivity.current
    val context = androidx.compose.ui.platform.LocalContext.current

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Column(Modifier.statusBarsPadding().padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(stringResource(R.string.more_title), style = MaterialTheme.typography.displayMedium)
                Text(
                    stringResource(R.string.more_sub),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        item {
            PremiumCard(active = premium.active, onClick = onPremium)
        }

        if (!premium.active) {
            item { AdBreakCard() }
        }

        item {
            Panel(stringResource(R.string.panel_language)) {
                doc.translations.forEach { (code, info) ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { onLanguage(code) }
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(selected = code == language, onClick = { onLanguage(code) })
                        Column(Modifier.weight(1f)) {
                            Text(info.language, style = MaterialTheme.typography.titleSmall)
                            Text(
                                if (info.publisher.isBlank()) info.translator else "${info.translator} · ${info.publisher}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
                Text(
                    stringResource(R.string.language_note),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 6.dp),
                )
            }
        }

        item {
            Panel(stringResource(R.string.verse_sources_title), icon = true) {
                Text(
                    stringResource(R.string.verse_sources_body),
                    style = MaterialTheme.typography.bodyMedium,
                )
                val notTrusted = doc.translations.filterValues { !it.trusted }.values.map { it.language }
                if (notTrusted.isNotEmpty()) {
                    Text(
                        stringResource(R.string.verse_sources_english, notTrusted.joinToString(", ")),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        item {
            Panel(stringResource(R.string.panel_arabic)) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Text(
                        sample.ar,
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
                Slider(
                    value = arabicSize,
                    onValueChange = onArabicSize,
                    onValueChangeFinished = onArabicSizeDone,
                    valueRange = 22f..44f,
                    colors = androidx.compose.material3.SliderDefaults.colors(
                        inactiveTrackColor = MaterialTheme.colorScheme.outline,
                    ),
                )
            }
        }

        item {
            Panel(stringResource(R.string.panel_appearance)) {
                val modes = ThemeMode.entries
                SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
                    modes.forEachIndexed { i, mode ->
                        SegmentedButton(
                            selected = mode == theme,
                            onClick = { onTheme(mode) },
                            shape = SegmentedButtonDefaults.itemShape(i, modes.size),
                            colors = qfSegmentedColors(),
                            label = {
                                Text(
                                    stringResource(
                                        when (mode) {
                                            ThemeMode.System -> R.string.theme_system
                                            ThemeMode.Light -> R.string.theme_light
                                            ThemeMode.Dark -> R.string.theme_dark
                                        },
                                    ),
                                )
                            },
                        )
                    }
                }
            }
        }

        item {
            Panel(stringResource(R.string.panel_label_claims), icon = true) {
                Text(
                    stringResource(R.string.label_claims_body),
                    style = MaterialTheme.typography.bodyMedium,
                )
                ClaimLegend(doc.claimTypes)
            }
        }

        item {
            Panel(stringResource(R.string.how_we_work), icon = true) {
                Bullet(stringResource(R.string.work_1))
                Bullet(stringResource(R.string.work_2))
                Bullet(stringResource(R.string.work_3))
                Bullet(stringResource(R.string.work_4))
                Bullet(stringResource(R.string.work_5))
                Bullet(stringResource(R.string.work_6))
                Bullet(stringResource(R.string.work_7))
                Bullet(stringResource(R.string.work_8))
                Bullet(stringResource(R.string.work_9))
            }
        }

        if (privacyChoices && !premium.active) {
            item {
                Panel(stringResource(R.string.panel_privacy)) {
                    Text(
                        stringResource(R.string.privacy_body),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    OutlinedButton(onClick = { activity?.let(Ads::showPrivacyOptions) }) { Text(stringResource(R.string.privacy_button)) }
                }
            }
        }

        item {
            Panel(stringResource(R.string.privacy_policy_title)) {
                Text(
                    stringResource(R.string.privacy_policy_body),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                OutlinedButton(onClick = { context.openUrl(PRIVACY_POLICY_URL) }) { Text(stringResource(R.string.privacy_policy_button)) }
            }
        }

        item {
            Panel(stringResource(R.string.panel_credits)) {
                Bullet(stringResource(R.string.credit_1))
                Bullet(stringResource(R.string.credit_2))
                Bullet(stringResource(R.string.credit_3))
                Bullet(stringResource(R.string.credit_4))
            }
        }

        item {
            Text(
                stringResource(R.string.disclaimer),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 4.dp),
            )
        }
    }
}

/** Top of More: an invitation to go ad-free, or a thank-you for subscribers. */
@Composable
private fun PremiumCard(active: Boolean, onClick: () -> Unit) {
    val gold = MaterialTheme.qf.gold
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(listOf(gold.copy(alpha = 0.24f), gold.copy(alpha = 0.06f))))
            .clickable(onClick = onClick)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        StarOrnament(36.dp, gold)
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(stringResource(if (active) R.string.premium_on else R.string.premium_go), style = MaterialTheme.typography.titleLarge)
            Text(
                stringResource(if (active) R.string.premium_on_body else R.string.premium_go_body),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Icon(Icons.AutoMirrored.Rounded.ArrowForward, null, tint = gold)
    }
}

@Composable
private fun Panel(title: String, icon: Boolean = false, content: @Composable () -> Unit) {
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                if (icon) StarOrnament(14.dp, MaterialTheme.colorScheme.primary)
                Text(title, style = MaterialTheme.typography.titleLarge)
            }
            content()
        }
    }
}

@Composable
private fun Bullet(text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        StarOrnament(8.dp, MaterialTheme.colorScheme.primary, Modifier.padding(top = 7.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
    }
}

/** A friendly alternative to Premium: watch one short video, get no full-screen ads for a couple of hours. */
@Composable
private fun AdBreakCard() {
    val until by Unlocks.until.collectAsStateWithLifecycle()
    val context = androidx.compose.ui.platform.LocalContext.current
    val unlock = rememberUnlocker(Unlocks.Feature.AdBreak)
    val ends = until[Unlocks.Feature.AdBreak] ?: 0L
    val active = ends > System.currentTimeMillis()
    Panel(stringResource(R.string.ad_break_title)) {
        Text(
            if (active) {
                stringResource(R.string.ad_break_active, android.text.format.DateFormat.getTimeFormat(context).format(java.util.Date(ends)))
            } else {
                stringResource(R.string.ad_break_body)
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        if (!active) {
            OutlinedButton(onClick = { unlock {} }) { Text(stringResource(R.string.ad_break_button)) }
        }
    }
}
