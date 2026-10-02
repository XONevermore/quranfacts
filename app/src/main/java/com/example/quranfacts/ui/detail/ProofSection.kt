package com.example.quranfacts.ui.detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.quranfacts.data.Fit
import com.example.quranfacts.data.Media
import com.example.quranfacts.data.ProofStep
import com.example.quranfacts.ui.theme.Serif
import com.example.quranfacts.ui.theme.qf
import androidx.compose.ui.res.stringResource
import com.example.quranfacts.R

/**
 * A vertical timeline of how something was found out: each step says who did what, when,
 * and what it showed, with the original evidence where it exists.
 */
@Composable
fun ProofTimeline(steps: List<ProofStep>, accent: Color, modifier: Modifier = Modifier) {
    var viewing by remember { mutableStateOf<Media?>(null) }
    val gold = MaterialTheme.qf.gold
    val rail = MaterialTheme.colorScheme.outline

    Column(modifier) {
        steps.forEachIndexed { index, step ->
            val last = index == steps.lastIndex
            Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                // The rail: a dot on the year, then a line running down to the next step.
                Column(Modifier.width(14.dp).fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(Modifier.padding(top = 5.dp).size(12.dp).clip(CircleShape).background(gold))
                    if (!last) {
                        Canvas(Modifier.weight(1f).width(2.dp)) {
                            drawLine(rail, Offset(size.width / 2, 0f), Offset(size.width / 2, size.height), strokeWidth = 2.dp.toPx())
                        }
                    }
                }
                Column(
                    Modifier.weight(1f).padding(bottom = if (last) 0.dp else 26.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        step.year,
                        style = MaterialTheme.typography.titleMedium.copy(fontFamily = Serif, fontWeight = FontWeight.Bold),
                        color = gold,
                    )
                    Text(step.title, style = MaterialTheme.typography.titleSmall)
                    Text(
                        step.text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    step.media?.let { m ->
                        Column(Modifier.padding(top = 6.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            MediaTile(m, accent, isCurrent = true, onOpenImage = { viewing = it })
                            MediaCredit(m)
                        }
                    }
                }
            }
        }
    }
    viewing?.let { ImageViewer(it) { viewing = null } }
}

/** The honest scoreboard: where the verse's words match the finding, and where to be careful. */
@Composable
fun FitPanel(fit: Fit, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        if (fit.fits.isNotEmpty()) {
            FitCard(
                title = stringResource(R.string.fit_title),
                items = fit.fits,
                color = MaterialTheme.qf.parallel,
                icon = { Icon(Icons.Rounded.CheckCircle, null, Modifier.size(20.dp), tint = MaterialTheme.qf.parallel) },
            )
        }
        if (fit.gaps.isNotEmpty()) {
            FitCard(
                title = stringResource(R.string.gap_title),
                items = fit.gaps,
                color = MaterialTheme.qf.historical,
                icon = { Icon(Icons.Rounded.WarningAmber, null, Modifier.size(20.dp), tint = MaterialTheme.qf.historical) },
            )
        }
    }
}

@Composable
private fun FitCard(title: String, items: List<String>, color: Color, icon: @Composable () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(color.copy(alpha = 0.10f))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            icon()
            Text(title, style = MaterialTheme.typography.titleMedium, color = color)
        }
        items.forEach { text ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(Modifier.padding(top = 9.dp).size(5.dp).clip(CircleShape).background(color))
                Text(text, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
            }
        }
    }
}
