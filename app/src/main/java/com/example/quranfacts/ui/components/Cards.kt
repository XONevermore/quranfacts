package com.example.quranfacts.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.quranfacts.data.Category
import com.example.quranfacts.data.ClaimType
import com.example.quranfacts.data.Fact
import com.example.quranfacts.ui.theme.qf
import com.example.quranfacts.util.toColor
import androidx.compose.ui.res.stringResource
import com.example.quranfacts.R

/**
 * An image that never looks broken: a category-tinted gradient with a faint star sits
 * underneath, so offline / slow / failed loads still look intentional.
 */
@Composable
fun CoverImage(
    url: String?,
    accent: Color,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    Box(
        modifier.background(
            Brush.linearGradient(
                listOf(accent.copy(alpha = 0.55f), MaterialTheme.colorScheme.surfaceContainerHigh),
            ),
        ),
        contentAlignment = Alignment.Center,
    ) {
        StarOrnament(56.dp, Color.White.copy(alpha = 0.10f), outlined = true)
        if (url != null) {
            AsyncImage(
                model = url,
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
fun PlayBadge(modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.55f)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(Icons.Rounded.Videocam, contentDescription = stringResource(R.string.cd_has_video), tint = Color.White, modifier = Modifier.size(17.dp))
    }
}

/** The main fact card: a photo, a scrim, and text that is always white for legibility. */
@Composable
fun FactCard(
    fact: Fact,
    category: Category?,
    claim: ClaimType?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 300.dp,
    showHook: Boolean = true,
) {
    val accent = category?.accent?.toColor() ?: MaterialTheme.qf.gold
    Box(
        modifier
            .height(height)
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick),
    ) {
        CoverImage(
            url = fact.heroImage?.thumb,
            accent = accent,
            modifier = Modifier.fillMaxSize(),
            contentDescription = fact.title,
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.35f to Color.Transparent,
                        1f to Color.Black.copy(alpha = 0.88f),
                    ),
                ),
        )
        Row(
            Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            if (claim != null) ClaimBadge(claim, onImage = true, explainOnClick = false, compact = true)
            if (fact.hasVideo) PlayBadge()
        }
        Column(
            Modifier.align(Alignment.BottomStart).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                (category?.name ?: "").uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = lighten(accent),
            )
            Text(
                fact.title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            if (showHook) {
                Text(
                    fact.hook,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.82f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

private fun lighten(c: Color): Color =
    Color(
        red = c.red + (1f - c.red) * 0.35f,
        green = c.green + (1f - c.green) * 0.35f,
        blue = c.blue + (1f - c.blue) * 0.35f,
    )
