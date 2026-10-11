package com.example.quranfacts.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quranfacts.R
import com.example.quranfacts.ui.components.StarOrnament
import com.example.quranfacts.ui.theme.Serif
import com.example.quranfacts.ui.theme.qf
import java.util.Locale

private fun Double.n1(): String = String.format(Locale.US, "%.1f", this)
private fun Double.n2(): String = String.format(Locale.US, "%.2f", this)
private fun Double.pct(): String = String.format(Locale.US, "%.1f%%", kotlin.math.abs(this))

/**
 * "Do the maths": the popular six-days calculation (13.8 billion ÷ 6, then the earth's two days of 41:9),
 * with the one placement choice it depends on exposed as a switch.
 */
@Composable
fun SixDaysLab(modifier: Modifier = Modifier) {
    var firstDay by remember { mutableIntStateOf(5) }
    val r = SixDaysMath.compute(firstDay)
    val last = firstDay == SixDaysMath.DAYS - 1
    val resultColor = if (last) MaterialTheme.qf.parallel else MaterialTheme.qf.historical

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
                    Text(stringResource(R.string.sd_title), style = MaterialTheme.typography.titleLarge)
                    Text(
                        stringResource(R.string.sd_sub),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Step(1, stringResource(R.string.sd_step1, SixDaysMath.UNIVERSE_BN.n1()))
                Step(2, stringResource(R.string.sd_step2, SixDaysMath.UNIVERSE_BN.n1(), r.perDayBn.n1()))
                Step(3, stringResource(R.string.sd_step3, r.perDayBn.n1(), r.earthSpanBn.n1()))
            }

            Choice(stringResource(R.string.sd_q_place)) {
                Segments(
                    options = listOf(stringResource(R.string.sd_first), stringResource(R.string.sd_last)),
                    selected = if (last) 1 else 0,
                    onSelect = { firstDay = if (it == 0) 1 else SixDaysMath.DAYS - 1 },
                )
            }

            DayLine(firstDay = firstDay, earthOnLine = r.earthOnLine.toFloat(), color = resultColor)

            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(resultColor.copy(alpha = 0.10f))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(stringResource(R.string.sd_result), style = MaterialTheme.typography.labelSmall, color = resultColor)
                Text(
                    if (last) stringResource(R.string.sd_span_last, r.startAgoBn.n1())
                    else stringResource(R.string.sd_span_first, r.endAgoBn.n1()),
                    style = MaterialTheme.typography.headlineSmall.copy(fontFamily = Serif, fontWeight = FontWeight.Bold),
                    color = resultColor,
                )
                Text(
                    if (last) stringResource(R.string.sd_true_last, SixDaysMath.EARTH_BN.n2(), r.diffPercent.pct())
                    else stringResource(R.string.sd_true_first, SixDaysMath.EARTH_BN.n2(), (r.endAgoBn - SixDaysMath.EARTH_BN).n1()),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Text(
                if (last) stringResource(R.string.sd_verdict_last, r.diffPercent.pct()) else stringResource(R.string.sd_verdict_first),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

/** Six equal days from the beginning to today, the earth's two days filled, and a tick where the Earth formed. */
@Composable
private fun DayLine(firstDay: Int, earthOnLine: Float, color: Color) {
    val track = MaterialTheme.colorScheme.outline
    val earth = Color(0xFF6FB6FF)
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Canvas(Modifier.fillMaxWidth().height(30.dp)) {
            val w = size.width
            val h = size.height
            val gap = 3.dp.toPx()
            val seg = (w - gap * (SixDaysMath.DAYS - 1)) / SixDaysMath.DAYS
            val barTop = h * 0.3f
            val barH = h * 0.4f
            for (d in 1..SixDaysMath.DAYS) {
                val x = (d - 1) * (seg + gap)
                val on = d == firstDay || d == firstDay + 1
                drawRoundRect(
                    if (on) color else track.copy(alpha = 0.45f),
                    Offset(x, barTop), Size(seg, barH), CornerRadius(barH / 2, barH / 2),
                )
            }
            val ex = earthOnLine * w
            drawLine(earth, Offset(ex, 0f), Offset(ex, h), strokeWidth = 3.dp.toPx())
            drawCircle(earth, radius = 5.dp.toPx(), center = Offset(ex, h / 2))
        }
        Row(Modifier.fillMaxWidth()) {
            for (d in 1..SixDaysMath.DAYS) {
                Text(
                    d.toString(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (d == firstDay || d == firstDay + 1) color else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.sd_axis_start, SixDaysMath.UNIVERSE_BN.n1()), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(stringResource(R.string.sd_axis_end), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Text(
            stringResource(R.string.sd_earth_tick, SixDaysMath.EARTH_BN.n2()),
            style = MaterialTheme.typography.labelSmall,
            color = earth,
        )
    }
}
