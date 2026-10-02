@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.quranfacts.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.quranfacts.ui.components.StarOrnament
import com.example.quranfacts.ui.components.qfSegmentedColors
import com.example.quranfacts.ui.theme.Serif
import com.example.quranfacts.ui.theme.qf
import java.util.Locale
import kotlin.math.abs
import androidx.compose.ui.res.stringResource
import com.example.quranfacts.R

private fun Double.n0(): String = String.format(Locale.US, "%,.0f", this)
private fun Double.n2(): String = String.format(Locale.US, "%,.2f", this)
private fun Double.n4(): String = String.format(Locale.US, "%.4f", this)
private fun Double.pct(): String = String.format(Locale.US, "%+.4f%%", this)

/**
 * "Try it yourself": the calculation behind the speed-of-light reading of 32:5,
 * with every assumption exposed as a switch. The point is transparency: you can see
 * exactly which choices carry the result.
 */
@Composable
fun LightSpeedLab(modifier: Modifier = Modifier) {
    var years by remember { mutableIntStateOf(1_000) }
    var sidereal by remember { mutableStateOf(true) }
    var correction by remember { mutableStateOf(true) }

    val r = LightSpeedMath.compute(years, sidereal, correction)
    val months = r.months
    val orbitKm = r.orbitKm
    val alphaDeg = r.alphaDeg
    val cosAlpha = r.cosAlpha
    val pathKm = r.pathKm
    val distanceKm = r.distanceKm
    val daySeconds = r.daySeconds
    val speed = r.speedKmS
    val diffPct = r.diffPercent

    val closeMatch = abs(diffPct) < 0.01
    val resultColor = if (closeMatch) MaterialTheme.qf.parallel else MaterialTheme.qf.historical

    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = BorderStroke(1.dp, MaterialTheme.qf.gold.copy(alpha = 0.28f)),
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StarOrnament(16.dp, MaterialTheme.qf.gold)
                Column {
                    Text(stringResource(R.string.ls_title), style = MaterialTheme.typography.titleLarge)
                    Text(
                        stringResource(R.string.ls_sub),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Choice(stringResource(R.string.ls_q_verse)) {
                Segments(
                    options = listOf(stringResource(R.string.ls_verse_a), stringResource(R.string.ls_verse_b)),
                    selected = if (years == 1_000) 0 else 1,
                    onSelect = { years = if (it == 0) 1_000 else 50_000 },
                )
            }
            Choice(stringResource(R.string.ls_q_day)) {
                Segments(
                    options = listOf(stringResource(R.string.ls_sidereal), stringResource(R.string.ls_solar)),
                    selected = if (sidereal) 0 else 1,
                    onSelect = { sidereal = it == 0 },
                )
            }
            Choice(stringResource(R.string.ls_q_earth)) {
                Segments(
                    options = listOf(stringResource(R.string.yes), stringResource(R.string.no)),
                    selected = if (correction) 0 else 1,
                    onSelect = { correction = it == 0 },
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Step(1, stringResource(R.string.ls_step1, years.toDouble().n0(), months.toDouble().n0()))
                Step(2, stringResource(R.string.ls_step2, LightSpeedMath.MOON_ORBIT_RADIUS_KM.n0(), orbitKm.n0()))
                Step(
                    3,
                    if (correction) {
                        stringResource(R.string.ls_step3_corrected, alphaDeg.n2(), cosAlpha.n4(), pathKm.n0())
                    } else {
                        stringResource(R.string.ls_step3_plain, pathKm.n0())
                    },
                )
                Step(4, stringResource(R.string.ls_step4, months.toDouble().n0(), pathKm.n0(), distanceKm.n0()))
                Step(5, stringResource(if (sidereal) R.string.ls_step5_sidereal else R.string.ls_step5_solar, daySeconds.n2()))
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(resultColor.copy(alpha = 0.10f))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(stringResource(R.string.ls_result), style = MaterialTheme.typography.labelSmall, color = resultColor)
                Text(
                    stringResource(R.string.ls_speed, speed.n0()),
                    style = MaterialTheme.typography.headlineMedium.copy(fontFamily = Serif, fontWeight = FontWeight.Bold),
                    color = resultColor,
                )
                Text(
                    stringResource(R.string.ls_true, LightSpeedMath.LIGHT_KM_S.n0(), diffPct.pct()),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Gauge(ratio = (speed / LightSpeedMath.LIGHT_KM_S).toFloat(), color = resultColor)
            }

            Text(
                verdict(years, sidereal, correction, speed, diffPct),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun verdict(years: Int, sidereal: Boolean, correction: Boolean, speed: Double, diffPct: Double): String = when {
    years != 1_000 -> stringResource(R.string.ls_verdict_70, (speed / LightSpeedMath.LIGHT_KM_S).n0())
    !correction -> stringResource(R.string.ls_verdict_nocorr, abs(diffPct).n2())
    !sidereal -> stringResource(R.string.ls_verdict_solar, abs(diffPct).n2())
    else -> stringResource(R.string.ls_verdict_author)
}

@Composable
private fun Choice(label: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        content()
    }
}

@Composable
private fun Segments(options: List<String>, selected: Int, onSelect: (Int) -> Unit) {
    SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                selected = index == selected,
                onClick = { onSelect(index) },
                shape = SegmentedButtonDefaults.itemShape(index, options.size),
                colors = qfSegmentedColors(),
                label = { Text(label, style = MaterialTheme.typography.labelMedium, maxLines = 1) },
            )
        }
    }
}

@Composable
private fun Step(number: Int, text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(
            Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(number.toString(), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        }
        Text(text, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
    }
}

/** A track from 0 to 2× the speed of light with a tick at the true value. */
@Composable
private fun Gauge(ratio: Float, color: Color) {
    val track = MaterialTheme.colorScheme.outline
    val tick = MaterialTheme.colorScheme.onSurface
    Canvas(Modifier.fillMaxWidth().height(14.dp)) {
        val h = size.height
        val radius = CornerRadius(h / 2, h / 2)
        drawRoundRect(track.copy(alpha = 0.5f), Offset(0f, h * 0.25f), Size(size.width, h * 0.5f), radius)
        val fill = (ratio / 2f).coerceIn(0f, 1f) * size.width
        drawRoundRect(color, Offset(0f, h * 0.25f), Size(fill.coerceAtLeast(h * 0.5f), h * 0.5f), radius)
        drawLine(tick, Offset(size.width / 2, 0f), Offset(size.width / 2, h), strokeWidth = 2.dp.toPx())
    }
}
