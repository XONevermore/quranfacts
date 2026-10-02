package com.example.quranfacts.ui.components

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quranfacts.R
import com.example.quranfacts.monetization.Ads
import com.example.quranfacts.monetization.Premium
import com.example.quranfacts.monetization.Unlocks
import com.example.quranfacts.ui.theme.qf

/** True while [feature] is open: the user is a subscriber, or watched a rewarded ad less than a day ago. */
@Composable
fun isUnlocked(feature: Unlocks.Feature): Boolean {
    val until by Unlocks.until.collectAsStateWithLifecycle()
    val premium by Premium.state.collectAsStateWithLifecycle()
    return premium.active || (until[feature] ?: 0L) > System.currentTimeMillis()
}

/**
 * Returns a function that offers a rewarded ad for [feature]. The ad is shown only when it is called (always from a
 * tap), [onUnlocked] runs after the user watched it, and a short message says so if no ad is available.
 */
@Composable
fun rememberUnlocker(feature: Unlocks.Feature): (onUnlocked: () -> Unit) -> Unit {
    val activity = LocalActivity.current
    val context = LocalContext.current
    return remember(activity, context, feature) {
        { onUnlocked ->
            val unavailable = { Toast.makeText(context, context.getString(R.string.toast_ad_unavailable), Toast.LENGTH_LONG).show() }
            if (activity == null) {
                unavailable()
            } else {
                Ads.showRewarded(
                    activity,
                    onReward = {
                        Unlocks.grant(feature)
                        val key = if (feature == Unlocks.Feature.AdBreak) R.string.toast_ad_break_on else R.string.toast_unlocked
                        Toast.makeText(context, context.getString(key), Toast.LENGTH_SHORT).show()
                        onUnlocked()
                    },
                    onUnavailable = unavailable,
                )
            }
        }
    }
}

/** Shows [content] when [feature] is open; otherwise a quiet card offering "watch one ad" or Premium. */
@Composable
fun UnlockGate(
    feature: Unlocks.Feature,
    title: String,
    body: String,
    onPremium: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    if (isUnlocked(feature)) {
        content()
        return
    }
    val unlock = rememberUnlocker(feature)
    val gold = MaterialTheme.qf.gold
    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = BorderStroke(1.dp, gold.copy(alpha = 0.28f)),
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Icon(Icons.Rounded.Lock, null, Modifier.size(20.dp), tint = gold)
                Text(title, style = MaterialTheme.typography.titleLarge)
            }
            Text(body, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Button(onClick = { unlock {} }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Rounded.PlayCircle, null, Modifier.size(20.dp))
                Text(stringResource(R.string.unlock_button), Modifier.padding(start = 8.dp))
            }
            TextButton(onClick = onPremium) { Text(stringResource(R.string.unlock_premium)) }
        }
    }
}
