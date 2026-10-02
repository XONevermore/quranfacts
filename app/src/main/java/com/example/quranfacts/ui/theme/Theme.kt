package com.example.quranfacts.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.quranfacts.data.ThemeMode

private val DarkColors = darkColorScheme(
    primary = Gold,
    onPrimary = Color(0xFF1A1405),
    primaryContainer = Color(0xFF3A2F14),
    onPrimaryContainer = GoldSoft,
    secondary = Emerald,
    onSecondary = Color(0xFF00201A),
    secondaryContainer = Color(0xFF12352E),
    onSecondaryContainer = Color(0xFFB8F0E2),
    tertiary = Color(0xFF8FA8FF),
    onTertiary = Color(0xFF0B1330),
    background = Midnight,
    onBackground = Ink,
    surface = Night,
    onSurface = Ink,
    surfaceVariant = PanelHigh,
    onSurfaceVariant = InkDim,
    outline = Stroke,
    outlineVariant = Color(0xFF1F2946),
    surfaceContainerLowest = Midnight,
    surfaceContainerLow = Night,
    surfaceContainer = Panel,
    surfaceContainerHigh = PanelHigh,
    surfaceContainerHighest = PanelHighest,
    error = Color(0xFFFF8A80),
)

private val LightColors = lightColorScheme(
    primary = GoldDeep,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFF6E7BC),
    onPrimaryContainer = Color(0xFF3A2A00),
    secondary = Color(0xFF0E7D67),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCDEFE5),
    onSecondaryContainer = Color(0xFF00201A),
    tertiary = Color(0xFF3A55C8),
    onTertiary = Color.White,
    background = Parchment,
    onBackground = InkLight,
    surface = Parchment,
    onSurface = InkLight,
    surfaceVariant = ParchmentHigh,
    onSurfaceVariant = InkLightDim,
    outline = ParchmentStroke,
    outlineVariant = Color(0xFFE8DFCB),
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color(0xFFFEFBF4),
    surfaceContainer = ParchmentPanel,
    surfaceContainerHigh = ParchmentHigh,
    surfaceContainerHighest = ParchmentHighest,
    error = Color(0xFFB3261E),
)

/** Colours that Material's scheme has no slot for. */
@Immutable
class QfExtras(
    val isDark: Boolean,
    val gold: Color,
    val arabicInk: Color,
    val parallel: Color,
    val interpretive: Color,
    val historical: Color,
    val miracle: Color,
    val heroScrim: Color,
) {
    fun claim(id: String): Color = when (id) {
        "parallel" -> parallel
        "interpretive" -> interpretive
        "historical" -> historical
        "miracle" -> miracle
        else -> gold
    }
}

private val DarkExtras = QfExtras(
    isDark = true,
    gold = Gold,
    arabicInk = Color(0xFFF6E6BC),
    parallel = Color(0xFF3FC9A6),
    interpretive = Color(0xFF8FA8FF),
    historical = Color(0xFFE9A25E),
    miracle = Color(0xFFC79BFF),
    heroScrim = Midnight,
)

private val LightExtras = QfExtras(
    isDark = false,
    gold = GoldDeep,
    arabicInk = Color(0xFF3A2A00),
    parallel = Color(0xFF0E7D67),
    interpretive = Color(0xFF3A55C8),
    historical = Color(0xFFA85F12),
    miracle = Color(0xFF7A3FBF),
    heroScrim = Parchment,
)

private val LocalQf = staticCompositionLocalOf { DarkExtras }

val MaterialTheme.qf: QfExtras
    @Composable
    @ReadOnlyComposable
    get() = LocalQf.current

@Composable
fun QuranFactsTheme(
    mode: ThemeMode = ThemeMode.Dark,
    content: @Composable () -> Unit,
) {
    val dark = when (mode) {
        ThemeMode.System -> isSystemInDarkTheme()
        ThemeMode.Light -> false
        ThemeMode.Dark -> true
    }
    val colors = if (dark) DarkColors else LightColors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = !dark
            controller.isAppearanceLightNavigationBars = !dark
        }
    }
    CompositionLocalProvider(LocalQf provides if (dark) DarkExtras else LightExtras) {
        MaterialTheme(colorScheme = colors, typography = QfTypography, content = content)
    }
}

/**
 * In the light theme, status-bar icons are dark, which vanish over a dark hero image.
 * While a hero is on screen, flip them to light; restore when it scrolls away or the
 * screen is left.
 */
@Composable
fun StatusBarIconsOverHero(overHero: Boolean) {
    val view = LocalView.current
    val dark = MaterialTheme.qf.isDark
    if (view.isInEditMode) return
    DisposableEffect(overHero, dark) {
        val controller = WindowCompat.getInsetsController((view.context as Activity).window, view)
        controller.isAppearanceLightStatusBars = if (dark) false else !overHero
        onDispose { controller.isAppearanceLightStatusBars = !dark }
    }
}
