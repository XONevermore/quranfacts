package com.example.quranfacts.ui.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quranfacts.data.FactsDoc
import com.example.quranfacts.ui.components.FactCard
import com.example.quranfacts.ui.components.StarOrnament
import androidx.compose.ui.res.stringResource
import com.example.quranfacts.R

@Composable
fun SavedScreen(
    doc: FactsDoc,
    bookmarks: Set<String>,
    onFact: (String) -> Unit,
) {
    val categories = remember(doc) { doc.categories.associateBy { it.id } }
    val claims = remember(doc) { doc.claimTypes.associateBy { it.id } }
    val saved = remember(doc, bookmarks) { doc.facts.filter { it.id in bookmarks } }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 168.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 28.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(Modifier.statusBarsPadding().padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(stringResource(R.string.saved_title), style = MaterialTheme.typography.displayMedium)
                Text(
                    stringResource(R.string.saved_sub),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        if (saved.isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    Modifier.fillMaxWidth().padding(vertical = 72.dp, horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    StarOrnament(56.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.6f), outlined = true)
                    Text(stringResource(R.string.saved_empty_title), style = MaterialTheme.typography.titleLarge)
                    Text(
                        stringResource(R.string.saved_empty_body),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
        items(saved, key = { it.id }) { f ->
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
