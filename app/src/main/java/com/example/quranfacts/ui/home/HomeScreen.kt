package com.example.quranfacts.ui.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.quranfacts.data.Category
import com.example.quranfacts.data.FactsDoc
import com.example.quranfacts.data.REVELATION_END_YEAR
import com.example.quranfacts.ui.detail.VerseCard
import com.example.quranfacts.ui.components.ClaimBadge
import com.example.quranfacts.ui.components.FactCard
import com.example.quranfacts.ui.components.SectionHeader
import com.example.quranfacts.ui.components.StarOrnament
import com.example.quranfacts.ui.components.Starfield
import com.example.quranfacts.ui.theme.Gold
import com.example.quranfacts.ui.theme.Midnight
import com.example.quranfacts.ui.theme.Serif
import com.example.quranfacts.ui.theme.qf
import com.example.quranfacts.ui.theme.StatusBarIconsOverHero
import com.example.quranfacts.util.grouped
import com.example.quranfacts.util.pickOfTheDay
import com.example.quranfacts.util.toColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.pluralStringResource
import com.example.quranfacts.R

private val Featured = listOf(
    "big-bang", "expanding-universe", "embryo-stages", "sun-moving", "mountains-pegs", "water-cycle",
)

@Composable
fun HomeScreen(
    doc: FactsDoc,
    language: String,
    arabicSize: Float,
    onFact: (String) -> Unit,
    onCategory: (String?) -> Unit,
    onMethod: () -> Unit,
    onTimeline: () -> Unit,
) {
    val categories = remember(doc) { doc.categories.associateBy { it.id } }
    val claims = remember(doc) { doc.claimTypes.associateBy { it.id } }
    val today = remember(doc) { pickOfTheDay(doc.facts) }
    val featured = remember(doc) { Featured.mapNotNull { id -> doc.facts.firstOrNull { it.id == id } } }
    val withVideo = remember(doc) { doc.facts.filter { it.hasVideo } }
    // History first (the oldest manuscripts lead), then parallels; the featured six are already shown above.
    val strongest = remember(doc) {
        doc.facts
            .filter { (it.claimType == "parallel" || it.claimType == "historical") && it.id !in Featured }
            .sortedBy { if (it.claimType == "historical") 0 else 1 }
    }

    val listState = rememberLazyListState()
    val heroVisible by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset < 300 }
    }
    StatusBarIconsOverHero(heroVisible)

    LazyColumn(
        Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp),
    ) {
        item {
            Hero(
                factCount = doc.facts.size,
                onStart = { onCategory(null) },
                onSurprise = { onFact(doc.facts.random().id) },
            )
        }
        if (today != null) {
            item {
                Column(
                    Modifier.padding(horizontal = 20.dp).offset(y = (-24).dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        stringResource(R.string.fact_of_day),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    FactCard(
                        fact = today,
                        category = categories[today.category],
                        claim = claims[today.claimType],
                        onClick = { onFact(today.id) },
                        modifier = Modifier.fillMaxWidth(),
                        height = 280.dp,
                    )
                }
            }
        }
        doc.intro?.let { verse ->
            item {
                Column(
                    Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(stringResource(R.string.the_promise), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    VerseCard(verse, language, doc.translations[language], arabicSize)
                }
            }
        }
        item {
            TimelineTeaser(doc, onTimeline, Modifier.padding(horizontal = 20.dp))
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                SectionHeader(stringResource(R.string.explore_by_theme), subtitle = stringResource(R.string.explore_by_theme_sub))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(doc.categories, key = { it.id }) { c ->
                        CategoryCard(
                            c,
                            count = doc.facts.count { it.category == c.id },
                            onClick = { onCategory(c.id) },
                        )
                    }
                }
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                SectionHeader(stringResource(R.string.start_here), subtitle = stringResource(R.string.start_here_sub))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    items(featured, key = { it.id }) { f ->
                        FactCard(
                            f, categories[f.category], claims[f.claimType],
                            onClick = { onFact(f.id) },
                            modifier = Modifier.width(264.dp),
                            height = 340.dp,
                        )
                    }
                }
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                SectionHeader(stringResource(R.string.strongest), subtitle = stringResource(R.string.strongest_sub))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    items(strongest, key = { it.id }) { f ->
                        FactCard(
                            f, categories[f.category], claims[f.claimType],
                            onClick = { onFact(f.id) },
                            modifier = Modifier.width(220.dp),
                            height = 280.dp,
                            showHook = false,
                        )
                    }
                }
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                SectionHeader(stringResource(R.string.watch_see), subtitle = stringResource(R.string.watch_see_sub))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    items(withVideo, key = { it.id }) { f ->
                        FactCard(
                            f, categories[f.category], claims[f.claimType],
                            onClick = { onFact(f.id) },
                            modifier = Modifier.width(240.dp),
                            height = 180.dp,
                            showHook = false,
                        )
                    }
                }
            }
        }
        item {
            HonestyCard(doc, onMethod, Modifier.padding(horizontal = 20.dp))
        }
    }
}

@Composable
private fun Hero(factCount: Int, onStart: () -> Unit, onSurprise: () -> Unit) {
    val spin by rememberInfiniteTransition(label = "spin").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(120_000, easing = LinearEasing), RepeatMode.Restart),
        label = "spin",
    )
    val background = MaterialTheme.colorScheme.background
    Box(Modifier.fillMaxWidth().height(400.dp)) {
        Box(
            Modifier.fillMaxSize().background(
                Brush.verticalGradient(listOf(Color(0xFF1B2A57), Color(0xFF0F1734), Midnight)),
            ),
        )
        Box(
            Modifier.fillMaxSize().background(
                Brush.radialGradient(
                    listOf(Gold.copy(alpha = 0.20f), Color.Transparent),
                    center = Offset(900f, 180f),
                    radius = 700f,
                ),
            ),
        )
        Starfield(Modifier.fillMaxSize(), count = 130)
        StarOrnament(
            300.dp, Gold.copy(alpha = 0.10f), outlined = true,
            modifier = Modifier.align(Alignment.TopEnd).offset(x = 90.dp, y = 30.dp).rotate(spin),
        )
        // Fade into the page background so the light theme has no hard edge.
        Box(
            Modifier.fillMaxSize().background(
                Brush.verticalGradient(0.72f to Color.Transparent, 1f to background),
            ),
        )
        Column(
            Modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 24.dp, vertical = 20.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StarOrnament(20.dp, Gold)
                Text(
                    stringResource(R.string.app_name).uppercase(),
                    style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 3.sp),
                    color = Gold,
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                stringResource(R.string.hero_title),
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
            )
            Spacer(Modifier.height(10.dp))
            Text(
                pluralStringResource(R.plurals.hero_body, factCount, factCount),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.78f),
            )
            Spacer(Modifier.height(18.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onStart,
                    colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Color(0xFF1A1405)),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 22.dp, vertical = 12.dp),
                ) {
                    Text(stringResource(R.string.start_exploring), style = MaterialTheme.typography.titleSmall)
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Rounded.ArrowForward, null, Modifier.size(18.dp))
                }
                FilledTonalIconButton(
                    onClick = onSurprise,
                    modifier = Modifier.size(46.dp),
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = Color.White.copy(alpha = 0.14f),
                        contentColor = Color.White,
                    ),
                ) { Icon(Icons.Rounded.Shuffle, stringResource(R.string.cd_surprise)) }
            }
            Spacer(Modifier.height(18.dp))
        }
    }
}

@Composable
private fun CategoryCard(category: Category, count: Int, onClick: () -> Unit) {
    val accent = category.accent.toColor()
    Box(
        Modifier
            .width(210.dp)
            .height(128.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(Brush.linearGradient(listOf(accent.copy(alpha = 0.55f), accent.copy(alpha = 0.14f))))
            .clickable(onClick = onClick),
    ) {
        StarOrnament(
            110.dp, Color.White.copy(alpha = 0.14f), outlined = true,
            modifier = Modifier.align(Alignment.TopEnd).offset(x = 30.dp, y = (-24).dp),
        )
        Column(Modifier.align(Alignment.BottomStart).padding(16.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(category.name, style = MaterialTheme.typography.titleLarge, color = Color.White)
            Text(
                pluralStringResource(R.plurals.category_count, count, count, category.tagline),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.85f),
                maxLines = 2,
            )
        }
    }
}

/** A doorway to the timeline: how long after the Quran each discovery came. */
@Composable
private fun TimelineTeaser(doc: FactsDoc, onOpen: () -> Unit, modifier: Modifier = Modifier) {
    val gaps = remember(doc) { doc.facts.mapNotNull { it.discovery?.yearsAfterRevelation } }
    if (gaps.isEmpty()) return
    val gold = MaterialTheme.qf.gold
    val green = MaterialTheme.qf.parallel
    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(listOf(gold.copy(alpha = 0.20f), green.copy(alpha = 0.14f))),
            )
            .clickable(onClick = onOpen)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(stringResource(R.string.teaser_label), style = MaterialTheme.typography.labelSmall, color = gold)
        Text(stringResource(R.string.teaser_title, REVELATION_END_YEAR), style = MaterialTheme.typography.titleLarge)
        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            Column {
                Text(
                    "${gaps.size}",
                    style = MaterialTheme.typography.headlineMedium.copy(fontFamily = Serif, fontWeight = FontWeight.Bold),
                    color = gold,
                )
                Text(stringResource(R.string.summary_discoveries), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column {
                Text(
                    "${gaps.min().grouped()}+",
                    style = MaterialTheme.typography.headlineMedium.copy(fontFamily = Serif, fontWeight = FontWeight.Bold),
                    color = green,
                )
                Text(stringResource(R.string.teaser_earliest), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.see_in_order), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.width(6.dp))
            Icon(Icons.AutoMirrored.Rounded.ArrowForward, null, Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun HonestyCard(doc: FactsDoc, onMethod: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StarOrnament(16.dp, MaterialTheme.colorScheme.primary)
                Text(stringResource(R.string.claim_card_title), style = MaterialTheme.typography.titleLarge)
            }
            Text(
                stringResource(R.string.claim_card_body),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                doc.claimTypes.take(2).forEach { ClaimBadge(it) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                doc.claimTypes.drop(2).forEach { ClaimBadge(it) }
            }
            TextButton(onClick = onMethod, contentPadding = PaddingValues(0.dp)) {
                Text(stringResource(R.string.how_we_work), color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(6.dp))
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowForward, null,
                    Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
