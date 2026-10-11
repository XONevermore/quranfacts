package com.example.quranfacts.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.quranfacts.R
import com.example.quranfacts.data.Pattern
import com.example.quranfacts.ui.theme.qf

/**
 * The counts of verses, words and letters that circulate with a fact, each recounted against the full text
 * (tools/quran_count.py) and marked: checks out, depends on how you count, or does not hold.
 */
@Composable
fun PatternsPanel(patterns: List<Pattern>, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text(
            stringResource(R.string.patterns_intro),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        patterns.forEach { PatternCard(it) }
    }
}

@Composable
private fun PatternCard(pattern: Pattern) {
    val (color, label) = when (pattern.verdict) {
        Pattern.HOLDS -> MaterialTheme.qf.parallel to stringResource(R.string.verdict_holds)
        Pattern.FAILS -> MaterialTheme.colorScheme.error to stringResource(R.string.verdict_fails)
        else -> MaterialTheme.qf.historical to stringResource(R.string.verdict_depends)
    }
    val icon = when (pattern.verdict) {
        Pattern.HOLDS -> Icons.Rounded.CheckCircle
        Pattern.FAILS -> Icons.Rounded.Cancel
        else -> Icons.Rounded.WarningAmber
    }
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            Modifier
                .clip(RoundedCornerShape(50))
                .background(color.copy(alpha = 0.12f))
                .padding(start = 8.dp, end = 12.dp, top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Icon(icon, null, Modifier.size(16.dp), tint = color)
            Text(label, style = MaterialTheme.typography.labelLarge, color = color)
        }
        Text(pattern.claim, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold))
        Text(
            pattern.check,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
