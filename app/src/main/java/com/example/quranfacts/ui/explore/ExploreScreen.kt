package com.example.quranfacts.ui.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.quranfacts.data.Fact
import com.example.quranfacts.data.FactsDoc
import com.example.quranfacts.ui.components.FactCard
import com.example.quranfacts.ui.components.FilterPill
import com.example.quranfacts.ui.components.StarOrnament
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.pluralStringResource
import com.example.quranfacts.R

/**
 * How well a fact matches the query, or 0 for no match. A hit in the title beats a hit in
 * the hook, which beats verse references, translations and Arabic words, which beat the science text.
 * Verses match in English and in the reader's chosen translation, so an Uzbek or Russian word works too.
 */
private fun Fact.score(query: String, doc: FactsDoc, language: String): Int {
    val terms = query.trim().lowercase().split(' ').filter { it.isNotBlank() }
    if (terms.isEmpty()) return 1
    val title = title.lowercase()
    val hook = hook.lowercase()
    val refs = buildString {
        verses.forEach {
            append(it.ref).append(' ').append(it.surahEn).append(' ').append(it.tr["en"].orEmpty()).append(' ')
            if (language != "en") append(it.tr[language].orEmpty()).append(' ')
        }
        words.forEach { append(it.tr).append(' ').append(it.en).append(' ') }
        doc.claimTypes.firstOrNull { it.id == claimType }?.let { append(it.label) }
    }.lowercase()
    val evidence = proof.joinToString(" ") { "${it.year} ${it.title} ${it.text}" }.lowercase()
    val body = science.joinToString(" ").lowercase()
    var total = 0
    for (t in terms) {
        total += when {
            t in title -> 8
            t in hook -> 4
            t in refs -> 2
            t in body -> 1
            t in evidence -> 1
            else -> return 0
        }
    }
    return total
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    doc: FactsDoc,
    initialCategory: String?,
    language: String,
    onFact: (String) -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable(initialCategory) { mutableStateOf(initialCategory) }
    var claim by rememberSaveable { mutableStateOf<String?>(null) }
    val focus: FocusManager = LocalFocusManager.current

    val categories = remember(doc) { doc.categories.associateBy { it.id } }
    val claims = remember(doc) { doc.claimTypes.associateBy { it.id } }
    val results = remember(doc, query, category, claim, language) {
        doc.facts
            .filter { (category == null || it.category == category) && (claim == null || it.claimType == claim) }
            .map { it to it.score(query, doc, language) }
            .filter { it.second > 0 }
            .let { scored -> if (query.isBlank()) scored else scored.sortedByDescending { it.second } }
            .map { it.first }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 168.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 28.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(Modifier.statusBarsPadding().padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(stringResource(R.string.explore_title), style = MaterialTheme.typography.displayMedium)
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text(stringResource(R.string.search_hint), maxLines = 1) },
                    leadingIcon = { Icon(Icons.Rounded.Search, null) },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton({ query = "" }) { Icon(Icons.Rounded.Close, stringResource(R.string.cd_clear)) }
                        }
                    },
                    shape = RoundedCornerShape(50),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = androidx.compose.foundation.text.KeyboardActions(onSearch = { focus.clearFocus() }),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterPill(stringResource(R.string.filter_all_themes), selected = category == null) { category = null }
                    }
                    items(doc.categories, key = { it.id }) { c ->
                        FilterPill(c.name, selected = category == c.id) { category = if (category == c.id) null else c.id }
                    }
                }
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterPill(stringResource(R.string.filter_any_strength), selected = claim == null) { claim = null }
                    }
                    items(doc.claimTypes, key = { it.id }) { c ->
                        FilterPill(c.short, selected = claim == c.id) { claim = if (claim == c.id) null else c.id }
                    }
                }
                Text(
                    pluralStringResource(R.plurals.facts_count, results.size, results.size),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        if (results.isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    Modifier.fillMaxWidth().padding(vertical = 56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    StarOrnament(40.dp, MaterialTheme.colorScheme.outline, outlined = true)
                    Text(stringResource(R.string.no_match_title), style = MaterialTheme.typography.titleMedium)
                    Text(
                        stringResource(R.string.no_match_body),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
        items(results, key = { it.id }) { f ->
            FactCard(
                fact = f,
                category = categories[f.category],
                claim = claims[f.claimType],
                onClick = { onFact(f.id) },
                modifier = Modifier.fillMaxWidth(),
                height = 250.dp,
                showHook = false,
            )
        }
    }
}
