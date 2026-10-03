package com.example.quranfacts.ui

import androidx.activity.compose.LocalActivity
import androidx.annotation.StringRes
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Timeline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.quranfacts.AppViewModel
import com.example.quranfacts.DataState
import com.example.quranfacts.data.FactsDoc
import com.example.quranfacts.monetization.Ads
import com.example.quranfacts.ui.components.StarOrnament
import com.example.quranfacts.ui.detail.FactDetailScreen
import com.example.quranfacts.ui.explore.ExploreScreen
import com.example.quranfacts.ui.home.HomeScreen
import com.example.quranfacts.ui.more.MoreScreen
import com.example.quranfacts.ui.onboarding.LanguagePickerScreen
import com.example.quranfacts.ui.premium.PremiumScreen
import com.example.quranfacts.ui.saved.SavedScreen
import com.example.quranfacts.ui.timeline.TimelineScreen
import com.example.quranfacts.ui.theme.QuranFactsTheme
import kotlinx.serialization.Serializable
import androidx.compose.ui.res.stringResource
import com.example.quranfacts.R

@Serializable
data object HomeRoute

@Serializable
data class ExploreRoute(val category: String? = null)

@Serializable
data object TimelineRoute

@Serializable
data object SavedRoute

@Serializable
data object MoreRoute

@Serializable
data class FactRoute(val id: String)

@Serializable
data object PremiumRoute

private class Tab(@StringRes val label: Int, val icon: ImageVector, val route: Any)

private val Tabs = listOf(
    Tab(R.string.tab_home, Icons.Rounded.Home, HomeRoute),
    Tab(R.string.tab_explore, Icons.Rounded.Explore, ExploreRoute()),
    Tab(R.string.tab_timeline, Icons.Rounded.Timeline, TimelineRoute),
    Tab(R.string.tab_saved, Icons.Rounded.Bookmarks, SavedRoute),
    Tab(R.string.tab_more, Icons.Rounded.MoreHoriz, MoreRoute),
)

@Composable
fun AppRoot(vm: AppViewModel) {
    val theme by vm.theme.collectAsStateWithLifecycle()
    val state by vm.state.collectAsStateWithLifecycle()
    val language by vm.language.collectAsStateWithLifecycle()
    val languageConfirmed by vm.languageConfirmed.collectAsStateWithLifecycle()
    Localized(language) {
        QuranFactsTheme(theme) {
            Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                when (val s = state) {
                    DataState.Loading -> LoadingScreen()
                    is DataState.Failed -> ErrorScreen(s.message, onRetry = vm::load)
                    is DataState.Ready ->
                        if (languageConfirmed) MainScaffold(s.doc, vm)
                        else LanguagePickerScreen(s.doc.translations, language, vm::setLanguage, vm::confirmLanguage)
                }
            }
        }
    }
}

@Composable
private fun MainScaffold(doc: FactsDoc, vm: AppViewModel) {
    val nav = rememberNavController()
    val entry by nav.currentBackStackEntryAsState()
    val destination = entry?.destination
    val language by vm.language.collectAsStateWithLifecycle()
    val arabicSize by vm.arabicSize.collectAsStateWithLifecycle()
    val theme by vm.theme.collectAsStateWithLifecycle()
    val bookmarks by vm.bookmarks.collectAsStateWithLifecycle()

    val onTab = destination?.hierarchy?.any {
        Tabs.any { tab -> it.hasRoute(tab.route::class) }
    } == true

    // Every way into a fact goes through here, so free users see an occasional ad (see AdPolicy).
    val activity = LocalActivity.current
    fun openFact(id: String) {
        nav.navigate(FactRoute(id))
        activity?.let(Ads::onFactOpened)
    }

    fun goTab(route: Any) = nav.navigate(route) {
        popUpTo(nav.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = route !is ExploreRoute
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            if (onTab) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.97f)) {
                    Tabs.forEach { tab ->
                        val selected = destination?.hierarchy?.any { it.hasRoute(tab.route::class) } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = { goTab(tab.route) },
                            icon = { Icon(tab.icon, stringResource(tab.label)) },
                            label = { Text(stringResource(tab.label), maxLines = 1, softWrap = false, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                        )
                    }
                }
            }
        },
    ) { padding ->
        Box(Modifier.padding(bottom = padding.calculateBottomPadding())) {
        NavHost(
            navController = nav,
            startDestination = HomeRoute,
            modifier = Modifier.fillMaxSize(),
            enterTransition = { fadeIn(tween(220)) },
            exitTransition = { fadeOut(tween(160)) },
            popEnterTransition = { fadeIn(tween(220)) },
            popExitTransition = { fadeOut(tween(160)) },
        ) {
            composable<HomeRoute> {
                HomeScreen(
                    doc = doc,
                    language = language,
                    arabicSize = arabicSize,
                    onFact = ::openFact,
                    onCategory = { goTab(ExploreRoute(it)) },
                    onMethod = { goTab(MoreRoute) },
                    onTimeline = { goTab(TimelineRoute) },
                )
            }
            composable<ExploreRoute> { e ->
                val route = e.toRoute<ExploreRoute>()
                ExploreScreen(doc, route.category, language, ::openFact)
            }
            composable<TimelineRoute> {
                TimelineScreen(doc, ::openFact)
            }
            composable<SavedRoute> {
                SavedScreen(doc, bookmarks, ::openFact)
            }
            composable<MoreRoute> {
                MoreScreen(
                    doc = doc,
                    language = language,
                    arabicSize = arabicSize,
                    theme = theme,
                    onLanguage = vm::setLanguage,
                    onArabicSize = vm::setArabicSize,
                    onArabicSizeDone = vm::commitArabicSize,
                    onTheme = vm::setTheme,
                    onPremium = { nav.navigate(PremiumRoute) },
                )
            }
            composable<PremiumRoute> {
                PremiumScreen(onBack = { nav.popBackStack() })
            }
            composable<FactRoute>(
                enterTransition = { slideInVertically(tween(320)) { it / 10 } + fadeIn(tween(320)) },
                popExitTransition = { slideOutVertically(tween(260)) { it / 10 } + fadeOut(tween(260)) },
            ) { e ->
                val id = e.toRoute<FactRoute>().id
                val fact = doc.facts.firstOrNull { it.id == id }
                if (fact != null) {
                    FactDetailScreen(
                        fact = fact,
                        doc = doc,
                        language = language,
                        arabicSize = arabicSize,
                        bookmarked = id in bookmarks,
                        onBack = { nav.popBackStack() },
                        onToggleBookmark = { vm.toggleBookmark(id) },
                        onLanguage = vm::setLanguage,
                        onOpenFact = ::openFact,
                        onPremium = { nav.navigate(PremiumRoute) },
                    )
                }
            }
        }
        // Keep scrolled content from colliding with the clock and battery icons. Home's
        // hero is designed to run full-bleed, so it is left alone.
        val onHome = destination?.hierarchy?.any { it.hasRoute(HomeRoute::class) } == true
        if (onTab && !onHome) {
            val page = MaterialTheme.colorScheme.background
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 10.dp)
                    .background(Brush.verticalGradient(0.7f to page.copy(alpha = 0.92f), 1f to Color.Transparent)),
            )
        }
        }
    }
}

@Composable
private fun LoadingScreen() {
    val pulse by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 0.35f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "pulse",
    )
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        StarOrnament(64.dp, MaterialTheme.colorScheme.primary, Modifier.alpha(pulse))
    }
}

@Composable
private fun ErrorScreen(message: String?, onRetry: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(R.string.load_failed), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        if (!message.isNullOrBlank()) {
            Text(message, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
        }
        Button(onRetry) { Text(stringResource(R.string.try_again)) }
    }
}
