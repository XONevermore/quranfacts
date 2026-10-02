package com.example.quranfacts.ui.timeline

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.quranfacts.data.Fact
import com.example.quranfacts.data.FactsDoc
import com.example.quranfacts.data.REVELATION_END_YEAR
import com.example.quranfacts.ui.components.ClaimBadge
import com.example.quranfacts.ui.components.CoverImage
import com.example.quranfacts.ui.components.FilterPill
import com.example.quranfacts.ui.components.StarOrnament
import com.example.quranfacts.ui.theme.Serif
import com.example.quranfacts.ui.theme.qf
import com.example.quranfacts.util.grouped
import com.example.quranfacts.util.toColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.pluralStringResource
import com.example.quranfacts.R

private val RailWidth = 30.dp

/**
 * The whole app on one line: the Quran is complete by 632 CE, and each discovery its verses point to
 * sits where it was made, with the number of years in between.
 */
@Composable
fun TimelineScreen(doc: FactsDoc, onFact: (String) -> Unit) {
    var claim by rememberSaveable { mutableStateOf<String?>(null) }
    val claims = remember(doc) { doc.claimTypes.associateBy { it.id } }
    val categories = remember(doc) { doc.categories.associateBy { it.id } }
    val dated = remember(doc, claim) {
        doc.facts
            .filter { it.discovery?.year != null && (claim == null || it.claimType == claim) }
            .sortedBy { it.discovery!!.year }
    }
    val undated = remember(doc, claim) {
        doc.facts.filter { it.discovery?.year == null && (claim == null || it.claimType == claim) }
    }
    val gold = MaterialTheme.qf.gold
    val green = MaterialTheme.qf.parallel

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 32.dp),
    ) {
        item {
            Column(Modifier.statusBarsPadding().padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(stringResource(R.string.timeline_title), style = MaterialTheme.typography.displayMedium)
                Text(
                    stringResource(R.string.timeline_sub, REVELATION_END_YEAR),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        item {
            LazyRow(Modifier.padding(top = 14.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item { FilterPill(stringResource(R.string.filter_any_strength), selected = claim == null) { claim = null } }
                items(doc.claimTypes, key = { it.id }) { c ->
                    FilterPill(c.short, selected = claim == c.id) { claim = if (claim == c.id) null else c.id }
                }
            }
        }
        if (dated.isNotEmpty()) {
            item { Summary(dated, Modifier.padding(top = 18.dp, bottom = 10.dp)) }
        }

        // ---- 610–632: the revelation -----------------------------------------
        item {
            RailRow(top = null, bottom = gold, dotColor = gold, dotSize = 16.dp) {
                Column(Modifier.padding(vertical = 14.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        stringResource(R.string.revealed_span, REVELATION_END_YEAR),
                        style = MaterialTheme.typography.headlineSmall.copy(fontFamily = Serif, fontWeight = FontWeight.Bold),
                        color = gold,
                    )
                    Text(stringResource(R.string.revealed_title), style = MaterialTheme.typography.titleMedium)
                    Text(
                        stringResource(R.string.revealed_body),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        if (dated.isNotEmpty()) {
            val firstGap = dated.first().discovery!!.yearsAfterRevelation!!
            item {
                RailRow(top = gold, bottom = lerp(gold, green, 0.1f), dashed = true, dotColor = null) {
                    Text(
                        pluralStringResource(R.plurals.years_pass, firstGap, firstGap.grouped()),
                        style = MaterialTheme.typography.bodyMedium.copy(fontFamily = Serif),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 22.dp),
                    )
                }
            }
        }

        // ---- The discoveries, century by century ------------------------------
        var lastCentury = -1
        dated.forEachIndexed { i, fact ->
            val year = fact.discovery!!.year!!
            val century = year / 100
            val t = if (dated.size == 1) 1f else i / (dated.size - 1f)
            val color = lerp(lerp(gold, green, 0.25f), green, t)
            if (century != lastCentury) {
                lastCentury = century
                item(key = "c$century") {
                    RailRow(top = color, bottom = color, dotColor = null) {
                        Text(
                            stringResource(R.string.century_label, century),
                            style = MaterialTheme.typography.labelLarge,
                            color = color,
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .clip(RoundedCornerShape(50))
                                .background(color.copy(alpha = 0.12f))
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                        )
                    }
                }
            }
            item(key = fact.id) {
                val accent = categories[fact.category]?.accent?.toColor() ?: gold
                RailRow(
                    top = color,
                    bottom = if (i == dated.lastIndex) null else color,
                    dotColor = MaterialTheme.qf.claim(fact.claimType),
                ) {
                    DiscoveryCard(fact, accent, claims[fact.claimType], color, onClick = { onFact(fact.id) })
                }
            }
        }

        if (undated.isNotEmpty()) {
            item { NotOnTheLine(undated, onFact, Modifier.padding(top = 28.dp)) }
        }
    }
}

/** Three numbers that say what the line shows. */
@Composable
private fun Summary(dated: List<Fact>, modifier: Modifier = Modifier) {
    val gaps = dated.mapNotNull { it.discovery?.yearsAfterRevelation }
    Row(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(vertical = 16.dp, horizontal = 8.dp),
    ) {
        SummaryStat(dated.size.toString(), stringResource(R.string.summary_discoveries), Modifier.weight(1f))
        SummaryStat(gaps.min().grouped(), stringResource(R.string.summary_first), Modifier.weight(1f))
        SummaryStat(gaps.average().toInt().grouped(), stringResource(R.string.summary_avg), Modifier.weight(1f))
    }
}

@Composable
private fun SummaryStat(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            value,
            style = MaterialTheme.typography.headlineSmall.copy(fontFamily = Serif, fontWeight = FontWeight.Bold),
            color = MaterialTheme.qf.gold,
        )
        Text(
            label,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 2,
        )
    }
}

/**
 * One row of the timeline: a rail on the left (line above and below, optional dot) and content on the right.
 * [top] / [bottom] are the line colours above and below the dot, or null for no line.
 */
@Composable
private fun RailRow(
    top: Color?,
    bottom: Color?,
    dotColor: Color?,
    dotSize: Dp = 11.dp,
    dashed: Boolean = false,
    content: @Composable () -> Unit,
) {
    val ring = MaterialTheme.colorScheme.background
    Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Canvas(Modifier.width(RailWidth).fillMaxHeight()) {
            val x = size.width / 2
            val mid = size.height / 2
            val stroke = 2.dp.toPx()
            val effect = if (dashed) PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 6.dp.toPx())) else null
            top?.let { drawLine(it, Offset(x, 0f), Offset(x, mid), stroke, pathEffect = effect) }
            bottom?.let { drawLine(it, Offset(x, mid), Offset(x, size.height), stroke, pathEffect = effect) }
            if (dotColor != null) {
                val r = dotSize.toPx() / 2
                drawCircle(ring, r + 3.dp.toPx(), Offset(x, mid))
                drawCircle(dotColor, r, Offset(x, mid))
            }
        }
        Box(Modifier.weight(1f).padding(start = 10.dp), contentAlignment = Alignment.CenterStart) { content() }
    }
}

@Composable
private fun DiscoveryCard(
    fact: Fact,
    accent: Color,
    claim: com.example.quranfacts.data.ClaimType?,
    color: Color,
    onClick: () -> Unit,
) {
    val d = fact.discovery!!
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    d.whenText,
                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = Serif, fontWeight = FontWeight.Bold),
                    color = color,
                )
                d.yearsAfterRevelation?.let {
                    Text(
                        stringResource(R.string.yrs_later_short, it.grouped()),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Text(fact.title, style = MaterialTheme.typography.titleSmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(
                d.who,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            if (claim != null) ClaimBadge(claim, compact = true, explainOnClick = false, modifier = Modifier.padding(top = 2.dp))
        }
        CoverImage(
            url = fact.heroImage?.thumb,
            accent = accent,
            modifier = Modifier.size(64.dp).clip(RoundedCornerShape(14.dp)),
            contentDescription = null,
        )
    }
}

/** Facts with no single date of discovery: prophecies, a miracle, and things that were always observable. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun NotOnTheLine(facts: List<Fact>, onFact: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StarOrnament(12.dp, MaterialTheme.colorScheme.primary)
            Text(stringResource(R.string.not_on_line), style = MaterialTheme.typography.titleLarge)
        }
        Text(
            stringResource(R.string.not_on_line_body),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            facts.forEach { f ->
                val c = MaterialTheme.qf.claim(f.claimType)
                Row(
                    Modifier
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .clickable { onFact(f.id) }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Box(Modifier.size(7.dp).clip(RoundedCornerShape(50)).background(c))
                    Text(f.title, style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
