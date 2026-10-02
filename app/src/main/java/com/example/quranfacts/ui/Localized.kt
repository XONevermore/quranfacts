package com.example.quranfacts.ui

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.unit.LayoutDirection
import com.example.quranfacts.data.I18n

/**
 * Runs [content] in the app's chosen language instead of the phone's. Every `stringResource` below reads
 * res/values-xx/strings.xml for [language], and the layout direction follows it (Urdu is right-to-left),
 * so switching language in the app needs no activity restart.
 */
@Composable
fun Localized(language: String, content: @Composable () -> Unit) {
    val base = LocalContext.current
    val localized = remember(base, language) {
        val locale = I18n.locale(language)
        val config = Configuration(base.resources.configuration).apply {
            setLocale(locale)
            setLayoutDirection(locale)
        }
        val resources = base.createConfigurationContext(config).resources
        // A wrapper (not the configuration context itself) so code that unwraps to the Activity still can.
        object : ContextWrapper(base) {
            override fun getResources(): Resources = resources
        } as Context
    }
    CompositionLocalProvider(
        LocalContext provides localized,
        LocalConfiguration provides localized.resources.configuration,
        LocalResources provides localized.resources,
        LocalLayoutDirection provides if (I18n.isRtl(language)) LayoutDirection.Rtl else LayoutDirection.Ltr,
        content = content,
    )
}
