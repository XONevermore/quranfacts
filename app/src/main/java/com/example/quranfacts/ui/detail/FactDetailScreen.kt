package com.example.quranfacts.ui.detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.quranfacts.data.Fact
import com.example.quranfacts.data.FactsDoc
import com.example.quranfacts.ui.components.ClaimBadge
import com.example.quranfacts.ui.components.FactCard
import com.example.quranfacts.ui.components.StarOrnament
import com.example.quranfacts.ui.components.TintChip
import com.example.quranfacts.ui.theme.Serif
import com.example.quranfacts.ui.theme.StatusBarIconsOverHero
import com.example.quranfacts.ui.theme.qf
import com.example.quranfacts.util.factShareText
import com.example.quranfacts.util.grouped
import com.example.quranfacts.util.openUrl
import com.example.quranfacts.util.toColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.pluralStringResource
import com.example.quranfacts.R
import com.example.quranfacts.monetization.Unlocks
import com.example.quranfacts.ui.components.UnlockGate
import com.example.quranfacts.data.REVELATION_END_YEAR

@Composable
fun FactDetailScreen(
    fact: Fact,
    doc: FactsDoc,
    language: String,
    arabicSize: Float,
    bookmarked: Boolean,
    onBack: () -> Unit,
    onToggleBookmark: () -> Unit,
    onLanguage: (String) -> Unit,
    onOpenFact: (String) -> Unit,
    onPremium: () -> Unit,
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val category = remember(doc, fact) { doc.categories.firstOrNull { it.id == fact.category } }
    val claim = remember(doc, fact) { doc.claimTypes.firstOrNull { it.id == fact.claimType } }
    val accent = category?.accent?.toColor() ?: MaterialTheme.qf.gold
    val related = remember(doc, fact) {
        doc.facts.filter { it.category == fact.category && it.id != fact.id }
            .ifEmpty { doc.facts.filter { it.id != fact.id }.take(4) }
    }
    val categories = remember(doc) { doc.categories.associateBy { it.id } }
    val claims = remember(doc) { doc.claimTypes.associateBy { it.id } }
    val background = MaterialTheme.colorScheme.background
    var sharing by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(28.dp),
        ) {
            // ---- Hero ---------------------------------------------------
            item {
                Box(Modifier.fillMaxWidth().height(340.dp)) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                translationY =
                                    if (listState.firstVisibleItemIndex == 0) listState.firstVisibleItemScrollOffset * 0.5f else 0f
                            },
                    ) {
                        Box(
                            Modifier.fillMaxSize().background(
                                Brush.linearGradient(listOf(accent.copy(alpha = 0.6f), MaterialTheme.colorScheme.surfaceContainerHigh)),
                            ),
                            contentAlignment = Alignment.Center,
                        ) { StarOrnament(72.dp, Color.White.copy(alpha = 0.12f), outlined = true) }
                        fact.heroImage?.let {
                            AsyncImage(
                                model = it.big,
                                contentDescription = it.caption,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    }
                    Box(
                        Modifier.fillMaxSize().background(
                            Brush.verticalGradient(
                                0f to Color.Black.copy(alpha = 0.45f),
                                0.35f to Color.Transparent,
                                0.75f to Color.Transparent,
                                1f to background,
                            ),
                        ),
                    )
                }
            }

            // ---- Title block --------------------------------------------
            item {
                Column(
                    Modifier.padding(horizontal = 20.dp).offset(y = (-36).dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        (category?.name ?: "").uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (MaterialTheme.qf.isDark) accent.brighten() else accent.darken(),
                    )
                    Text(fact.title, style = MaterialTheme.typography.displayMedium)
                    Text(
                        fact.hook,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        if (claim != null) ClaimBadge(claim)
                        fact.discovery?.let {
                            TintChip(stringResource(R.string.discovered_chip, it.whenText), color = MaterialTheme.qf.gold)
                        }
                    }
                }
            }

            // ---- What the Quran says ------------------------------------
            item {
                Column(Modifier.padding(horizontal = 20.dp).offset(y = (-36).dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        SectionTitle(stringResource(R.string.sec_quran), stringResource(R.string.sec_quran_sub))
                        LanguageMenu(doc, language, onLanguage)
                    }
                    fact.verses.forEach { v ->
                        VerseCard(v, language, doc.translations[language], arabicSize)
                    }
                }
            }

            // ---- Key words ----------------------------------------------
            if (fact.words.isNotEmpty()) {
                item {
                    Column(Modifier.offset(y = (-36).dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        SectionTitle(stringResource(R.string.sec_words), stringResource(R.string.sec_words_sub), Modifier.padding(horizontal = 20.dp))
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(fact.words) { w -> WordCard(w.ar, w.tr, w.en, w.root) }
                        }
                    }
                }
            }

            // ---- What science found -------------------------------------
            item {
                Column(Modifier.padding(horizontal = 20.dp).offset(y = (-36).dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    SectionTitle(stringResource(R.string.sec_other_side), fact.scienceHeading)
                    fact.science.forEach { p ->
                        Text(p, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            if (fact.stats.isNotEmpty()) {
                item {
                    LazyRow(
                        Modifier.offset(y = (-36).dp),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(fact.stats) { s -> StatTile(s.value, s.label) }
                    }
                }
            }

            fact.discovery?.let { d ->
                item {
                    Timeline(
                        whenText = d.whenText,
                        who = d.who,
                        yearsAfter = d.yearsAfterRevelation,
                        modifier = Modifier.padding(horizontal = 20.dp).offset(y = (-36).dp),
                    )
                }
            }

            if (fact.widget == "lightspeed") {
                item {
                    UnlockGate(
                        feature = Unlocks.Feature.SpeedLab,
                        title = stringResource(R.string.ls_title),
                        body = stringResource(R.string.locked_lab_body),
                        onPremium = onPremium,
                        modifier = Modifier.padding(horizontal = 20.dp).offset(y = (-36).dp),
                    ) {
                        LightSpeedLab(Modifier.padding(horizontal = 20.dp).offset(y = (-36).dp).then(Modifier))
                    }
                }
            }

            // ---- The evidence: how it was found out ---------------------
            if (fact.proof.isNotEmpty()) {
                item {
                    Column(
                        Modifier.padding(horizontal = 20.dp).offset(y = (-36).dp),
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                    ) {
                        SectionTitle(stringResource(R.string.sec_evidence), fact.proofHeading)
                        ProofTimeline(fact.proof, accent)
                    }
                }
            }

            // ---- See it -------------------------------------------------
            if (fact.media.isNotEmpty()) {
                item {
                    Column(Modifier.offset(y = (-36).dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        SectionTitle(
                            stringResource(R.string.sec_media),
                            mediaTitle(fact),
                            Modifier.padding(horizontal = 20.dp),
                        )
                        MediaCarousel(fact.media, accent)
                    }
                }
            }

            // ---- How well does it fit? ----------------------------------
            if (fact.fit.fits.isNotEmpty() || fact.fit.gaps.isNotEmpty()) {
                item {
                    Column(
                        Modifier.padding(horizontal = 20.dp).offset(y = (-36).dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        SectionTitle(stringResource(R.string.sec_side), stringResource(R.string.sec_side_sub))
                        FitPanel(fact.fit)
                    }
                }
            }

            // ---- Sources ------------------------------------------------
            if (fact.sources.isNotEmpty()) {
                item {
                    Column(Modifier.padding(horizontal = 20.dp).offset(y = (-36).dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        SectionTitle(stringResource(R.string.sec_sources), stringResource(R.string.sec_sources_sub))
                        Spacer(Modifier.height(6.dp))
                        fact.sources.forEach { s ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { context.openUrl(s.url) }
                                    .padding(vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.OpenInNew, null,
                                    Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary,
                                )
                                Text(s.title, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            // ---- Related ------------------------------------------------
            if (related.isNotEmpty()) {
                item {
                    Column(Modifier.offset(y = (-36).dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        SectionTitle(stringResource(R.string.sec_keep_going), stringResource(R.string.more_in, category?.name ?: stringResource(R.string.this_theme)), Modifier.padding(horizontal = 20.dp))
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(related, key = { it.id }) { f ->
                                FactCard(
                                    f, categories[f.category], claims[f.claimType],
                                    onClick = { onOpenFact(f.id) },
                                    modifier = Modifier.width(220.dp),
                                    height = 280.dp,
                                    showHook = false,
                                )
                            }
                        }
                    }
                }
            }
        }

        // ---- Floating actions -----------------------------------------
        val barAlpha by androidx.compose.runtime.remember {
            androidx.compose.runtime.derivedStateOf {
                if (listState.firstVisibleItemIndex > 0) 1f
                else (listState.firstVisibleItemScrollOffset / 500f).coerceIn(0f, 1f)
            }
        }
        StatusBarIconsOverHero(barAlpha < 0.5f)
        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 64.dp)
                .graphicsLayer { alpha = barAlpha }
                .background(Brush.verticalGradient(0.82f to background, 1f to Color.Transparent)),
        )
        Row(
            Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            RoundButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(R.string.cd_back), tint = Color.White) }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                RoundButton(onClick = { sharing = true }) { Icon(Icons.Rounded.Share, stringResource(R.string.cd_share), tint = Color.White) }
                RoundButton(onClick = onToggleBookmark) {
                    Icon(
                        if (bookmarked) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
                        stringResource(if (bookmarked) R.string.bookmark_remove else R.string.bookmark_add),
                        tint = if (bookmarked) Color(0xFFE2B865) else Color.White,
                    )
                }
            }
        }
    }

    if (sharing) {
        ShareFactDialog(
            fact = fact,
            claim = claim,
            language = language,
            shareText = context.factShareText(fact, language, claim?.label.orEmpty()),
            onDismiss = { sharing = false },
            onPremium = onPremium,
        )
    }
}

@Composable
private fun RoundButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.clip(CircleShape).background(Color.Black.copy(alpha = 0.45f)),
    ) { content() }
}

@Composable
private fun SectionTitle(kicker: String, title: String, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            kicker.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(title, style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
private fun LanguageMenu(doc: FactsDoc, language: String, onLanguage: (String) -> Unit) {
    var open by remember { mutableStateOf(false) }
    Box {
        Row(
            Modifier
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .clickable { open = true }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Icon(Icons.Rounded.Language, null, Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(language.uppercase(), style = MaterialTheme.typography.labelMedium)
            Icon(Icons.Rounded.ExpandMore, null, Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
            doc.translations.forEach { (code, info) ->
                DropdownMenuItem(
                    text = { Text(info.language) },
                    onClick = { onLanguage(code); open = false },
                    trailingIcon = {
                        if (code == language) StarOrnament(10.dp, MaterialTheme.colorScheme.primary)
                    },
                )
            }
        }
    }
}

@Composable
private fun WordCard(arabic: String, transliteration: String, meaning: String, root: String) {
    Card(
        Modifier.width(196.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            androidx.compose.runtime.CompositionLocalProvider(
                androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Rtl,
            ) {
                Text(
                    arabic,
                    style = MaterialTheme.typography.headlineMedium.copy(fontFamily = null, fontWeight = FontWeight.Normal),
                    color = MaterialTheme.qf.arabicInk,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Text(
                transliteration,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(meaning, style = MaterialTheme.typography.bodyMedium)
            if (root.isNotBlank()) {
                Text(
                    stringResource(R.string.word_root, root),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun StatTile(value: String, label: String) {
    Column(
        Modifier
            .widthIn(min = 140.dp, max = 200.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            value,
            style = MaterialTheme.typography.headlineSmall.copy(fontFamily = Serif, fontWeight = FontWeight.Bold),
            color = MaterialTheme.qf.gold,
        )
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

/** Revelation on one side, discovery on the other. */
@Composable
private fun Timeline(whenText: String, who: String, yearsAfter: Int?, modifier: Modifier = Modifier) {
    val gold = MaterialTheme.qf.gold
    val green = MaterialTheme.qf.parallel
    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Row(
            Modifier.padding(start = 18.dp, end = 18.dp, top = 18.dp, bottom = if (yearsAfter != null) 10.dp else 18.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(stringResource(R.string.revealed_approx, REVELATION_END_YEAR), style = MaterialTheme.typography.titleMedium.copy(fontFamily = Serif), color = gold)
                Text(stringResource(R.string.revealed_title), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Canvas(Modifier.width(56.dp).height(12.dp)) {
                val y = size.height / 2
                drawLine(
                    Brush.horizontalGradient(listOf(gold, green)),
                    Offset(0f, y), Offset(size.width - 4.dp.toPx(), y), strokeWidth = 2.dp.toPx(),
                )
                val head = Path().apply {
                    moveTo(size.width - 8.dp.toPx(), y - 5.dp.toPx())
                    lineTo(size.width, y)
                    lineTo(size.width - 8.dp.toPx(), y + 5.dp.toPx())
                }
                drawPath(head, green, style = Stroke(width = 2.dp.toPx()))
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp), horizontalAlignment = Alignment.End) {
                Text(
                    whenText,
                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = Serif),
                    color = green,
                    textAlign = TextAlign.End,
                )
                Text(
                    who,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.End,
                )
            }
        }
        if (yearsAfter != null) {
            Text(
                pluralStringResource(R.plurals.years_after, yearsAfter, yearsAfter.grouped()),
                style = MaterialTheme.typography.labelLarge,
                color = green,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 18.dp, bottom = 16.dp)
                    .clip(RoundedCornerShape(50))
                    .background(green.copy(alpha = 0.10f))
                    .padding(vertical = 6.dp),
            )
        }
    }
}

private fun Color.brighten(): Color =
    Color(red + (1f - red) * 0.3f, green + (1f - green) * 0.3f, blue + (1f - blue) * 0.3f)

private fun Color.darken(): Color = Color(red * 0.6f, green * 0.6f, blue * 0.6f)

@Composable
private fun mediaTitle(fact: Fact): String {
    val video = fact.media.any { it.isVideo }
    val audio = fact.media.any { it.isAudio }
    return stringResource(
        when {
            video && audio -> R.string.media_photos_video_audio
            video -> R.string.media_photos_video
            audio -> R.string.media_photos_audio
            else -> R.string.media_photos
        },
    )
}
