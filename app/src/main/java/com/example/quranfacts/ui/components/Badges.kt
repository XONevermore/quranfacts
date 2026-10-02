package com.example.quranfacts.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.quranfacts.data.ClaimType
import com.example.quranfacts.ui.theme.qf
import androidx.compose.ui.res.stringResource
import com.example.quranfacts.R

/** Bright, fixed colours for badges that sit on photos (which always carry a dark scrim). */
private fun onImageClaimColor(id: String): Color = when (id) {
    "parallel" -> Color(0xFF5BE0BC)
    "interpretive" -> Color(0xFFA9BCFF)
    "historical" -> Color(0xFFFFB877)
    "miracle" -> Color(0xFFD9B5FF)
    else -> Color(0xFFE2B865)
}

/**
 * The claim-type pill. This is the heart of the app's honesty: every fact says how
 * strong its verse-to-discovery link is. Tapping it explains the label.
 */
@Composable
fun ClaimBadge(
    claim: ClaimType,
    modifier: Modifier = Modifier,
    onImage: Boolean = false,
    explainOnClick: Boolean = true,
    compact: Boolean = false,
) {
    var showInfo by remember { mutableStateOf(false) }
    val color = if (onImage) onImageClaimColor(claim.id) else MaterialTheme.qf.claim(claim.id)
    val background = if (onImage) Color.Black.copy(alpha = 0.55f) else color.copy(alpha = 0.14f)
    Row(
        modifier
            .clip(RoundedCornerShape(50))
            .background(background)
            .then(if (explainOnClick) Modifier.clickable { showInfo = true } else Modifier)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(Modifier.size(7.dp).clip(CircleShape).background(color))
        Text(
            (if (compact) claim.short else claim.label).uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            maxLines = 1,
        )
    }
    if (showInfo) {
        AlertDialog(
            onDismissRequest = { showInfo = false },
            confirmButton = { TextButton({ showInfo = false }) { Text(stringResource(R.string.got_it)) } },
            title = { Text(claim.label, style = MaterialTheme.typography.titleLarge) },
            text = { Text(claim.description, style = MaterialTheme.typography.bodyLarge) },
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        )
    }
}

@Composable
fun ClaimLegend(claims: List<ClaimType>, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        claims.forEach { c ->
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                ClaimBadge(c, explainOnClick = false)
                Text(
                    c.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

/** A quiet tinted label, e.g. "Discovered 1929". */
@Composable
fun TintChip(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Text(
        text,
        style = MaterialTheme.typography.labelMedium,
        color = color,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 5.dp),
    )
}

/** A round filter pill in the gold theme, used for the theme and claim-strength filters. */
@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun FilterPill(label: String, selected: Boolean, onClick: () -> Unit) {
    androidx.compose.material3.FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label, style = MaterialTheme.typography.labelLarge) },
        shape = RoundedCornerShape(50),
        colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
    )
}

/** Segmented-button colours that follow the gold theme instead of Material's default green. */
@Composable
fun qfSegmentedColors() = androidx.compose.material3.SegmentedButtonDefaults.colors(
    activeContainerColor = MaterialTheme.colorScheme.primaryContainer,
    activeContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    activeBorderColor = MaterialTheme.colorScheme.primary,
    inactiveContainerColor = Color.Transparent,
    inactiveContentColor = MaterialTheme.colorScheme.onSurface,
    inactiveBorderColor = MaterialTheme.colorScheme.outline,
)
