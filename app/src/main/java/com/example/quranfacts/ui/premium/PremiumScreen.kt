package com.example.quranfacts.ui.premium

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.LockOpen
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quranfacts.monetization.Premium
import com.example.quranfacts.monetization.periodName
import com.example.quranfacts.monetization.periodWithCount
import com.example.quranfacts.monetization.planTitle
import com.example.quranfacts.ui.components.StarOrnament
import com.example.quranfacts.ui.components.TintChip
import com.example.quranfacts.ui.theme.Serif
import com.example.quranfacts.ui.theme.qf
import com.example.quranfacts.util.openUrl
import androidx.compose.ui.res.stringResource
import com.example.quranfacts.R

/** The ad-free subscription: what it gives, the plans Google Play offers, and the renewal terms. */
@Composable
fun PremiumScreen(onBack: () -> Unit) {
    val state by Premium.state.collectAsStateWithLifecycle()
    val activity = LocalActivity.current
    val context = LocalContext.current
    val gold = MaterialTheme.qf.gold
    // Preselect the best value (the plan with the biggest saving), otherwise the first.
    val defaultPlan = state.plans.maxByOrNull { it.savingPercent ?: 0 }?.basePlanId
    var chosenId by rememberSaveable { mutableStateOf<String?>(null) }
    val chosen = state.plans.firstOrNull { it.basePlanId == (chosenId ?: defaultPlan) }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                bottom = 24.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
            ),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            item {
                Box(Modifier.statusBarsPadding().padding(top = 4.dp)) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(R.string.cd_back))
                    }
                }
            }
            item {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Box(
                        Modifier
                            .size(88.dp)
                            .clip(CircleShape)
                            .background(Brush.radialGradient(listOf(gold.copy(alpha = 0.35f), Color.Transparent))),
                        contentAlignment = Alignment.Center,
                    ) { StarOrnament(52.dp, gold) }
                    Text(stringResource(R.string.premium_brand), style = MaterialTheme.typography.labelMedium, color = gold)
                    Text(stringResource(R.string.premium_headline), style = MaterialTheme.typography.displayMedium, textAlign = TextAlign.Center)
                    Text(
                        stringResource(R.string.premium_intro),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Benefit(Icons.Rounded.Block, stringResource(R.string.benefit_noads_t), stringResource(R.string.benefit_noads_b))
                    Benefit(Icons.Rounded.Favorite, stringResource(R.string.benefit_support_t), stringResource(R.string.benefit_support_b))
                    Benefit(Icons.Rounded.LockOpen, stringResource(R.string.benefit_free_t), stringResource(R.string.benefit_free_b))
                }
            }

            when {
                state.active -> item {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(MaterialTheme.qf.parallel.copy(alpha = 0.12f))
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Icon(Icons.Rounded.CheckCircle, null, Modifier.size(40.dp), tint = MaterialTheme.qf.parallel)
                        Text(stringResource(R.string.subscribed_title), style = MaterialTheme.typography.titleLarge)
                        Text(
                            stringResource(R.string.subscribed_body),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )
                        OutlinedButton(onClick = { context.openUrl(Premium.manageUrl(context)) }) {
                            Text(stringResource(R.string.manage_play))
                        }
                    }
                }

                state.status == Premium.Status.Connecting -> item {
                    Row(
                        Modifier.fillMaxWidth().padding(vertical = 24.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                        Spacer(Modifier.width(12.dp))
                        Text(stringResource(R.string.connecting_play), style = MaterialTheme.typography.bodyMedium)
                    }
                }

                state.status == Premium.Status.Unavailable || state.plans.isEmpty() -> item {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(stringResource(R.string.unavailable_title), style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
                        Text(
                            stringResource(R.string.unavailable_body),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )
                        OutlinedButton(onClick = Premium::retry) { Text(stringResource(R.string.try_again)) }
                    }
                }

                else -> {
                    state.plans.forEach { plan ->
                        item(key = plan.basePlanId) {
                            PlanCard(plan, selected = plan.basePlanId == chosen?.basePlanId) { chosenId = plan.basePlanId }
                        }
                    }
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { if (activity != null && chosen != null) Premium.purchase(activity, chosen) },
                                enabled = chosen != null && !state.pending,
                                modifier = Modifier.fillMaxWidth().height(54.dp),
                                shape = RoundedCornerShape(50),
                            ) {
                                Text(
                                    stringResource(if (chosen?.freeTrial != null) R.string.start_trial else R.string.subscribe),
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                            chosen?.let { p ->
                                val then = stringResource(R.string.price_every, p.price, periodName(p.period))
                                Text(
                                    if (p.freeTrial != null) {
                                        stringResource(R.string.terms_trial, periodWithCount(p.freeTrial), then)
                                    } else {
                                        stringResource(R.string.terms_plain, then)
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }

            if (state.pending) {
                item {
                    Text(
                        stringResource(R.string.payment_pending),
                        style = MaterialTheme.typography.bodyMedium,
                        color = gold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
            state.message?.let { msg ->
                item {
                    Text(
                        stringResource(msg),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().clickable { Premium.clearMessage() },
                    )
                }
            }

            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (!state.active) {
                        TextButton(onClick = Premium::retry) { Text(stringResource(R.string.restore_purchase)) }
                    }
                    Text(
                        stringResource(R.string.renewal_terms),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
        // Keep scrolled text from running under the clock and battery icons.
        val page = MaterialTheme.colorScheme.background
        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 10.dp)
                .background(Brush.verticalGradient(0.7f to page.copy(alpha = 0.92f), 1f to Color.Transparent)),
        )
    }
}

@Composable
private fun Benefit(icon: ImageVector, title: String, text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        Box(
            Modifier.size(38.dp).clip(CircleShape).background(MaterialTheme.qf.gold.copy(alpha = 0.14f)),
            contentAlignment = Alignment.Center,
        ) { Icon(icon, null, Modifier.size(20.dp), tint = MaterialTheme.qf.gold) }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall)
            Text(text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun PlanCard(plan: Premium.Plan, selected: Boolean, onSelect: () -> Unit) {
    val gold = MaterialTheme.qf.gold
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) gold.copy(alpha = 0.10f) else MaterialTheme.colorScheme.surfaceContainer)
            .border(
                BorderStroke(if (selected) 2.dp else 1.dp, if (selected) gold else MaterialTheme.colorScheme.outline),
                RoundedCornerShape(20.dp),
            )
            .clickable(onClick = onSelect)
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            Modifier
                .size(22.dp)
                .clip(CircleShape)
                .border(2.dp, if (selected) gold else MaterialTheme.colorScheme.outline, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            if (selected) Box(Modifier.size(11.dp).clip(CircleShape).background(gold))
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(planTitle(plan.period), style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                plan.freeTrial?.let { TintChip(stringResource(R.string.chip_free, periodWithCount(it)), color = MaterialTheme.qf.parallel) }
                plan.savingPercent?.let { TintChip(stringResource(R.string.chip_save, it), color = gold) }
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                plan.price,
                style = MaterialTheme.typography.titleLarge.copy(fontFamily = Serif, fontWeight = FontWeight.Bold),
            )
            Text(
                stringResource(R.string.per_period, periodName(plan.period)),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
