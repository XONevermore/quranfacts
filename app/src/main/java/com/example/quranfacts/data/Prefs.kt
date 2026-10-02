package com.example.quranfacts.data

import android.content.Context
import androidx.core.content.edit

enum class ThemeMode { System, Light, Dark;
    companion object {
        fun from(s: String?) = entries.firstOrNull { it.name == s } ?: Dark
    }
}

/** Tiny SharedPreferences wrapper; the ViewModel mirrors these into StateFlows. */
class Prefs(context: Context) {
    private val sp = context.getSharedPreferences("quran_facts", Context.MODE_PRIVATE)

    var language: String
        get() = sp.getString(K_LANG, null) ?: I18n.deviceLanguage()
        set(v) = sp.edit { putString(K_LANG, v) }

    var arabicSize: Float
        get() = sp.getFloat(K_ARABIC_SIZE, 30f)
        set(v) = sp.edit { putFloat(K_ARABIC_SIZE, v) }

    var theme: ThemeMode
        get() = ThemeMode.from(sp.getString(K_THEME, null))
        set(v) = sp.edit { putString(K_THEME, v.name) }

    var bookmarks: Set<String>
        get() = sp.getStringSet(K_BOOKMARKS, emptySet())?.toSet() ?: emptySet()
        set(v) = sp.edit { putStringSet(K_BOOKMARKS, v) }

    /** Last subscription state Google Play confirmed, so a subscriber starting offline never sees an ad. */
    var premium: Boolean
        get() = sp.getBoolean(K_PREMIUM, false)
        set(v) = sp.edit { putBoolean(K_PREMIUM, v) }

    /** Facts opened since the last full-screen ad (see AdPolicy). */
    var factsSinceAd: Int
        get() = sp.getInt(K_FACTS_SINCE_AD, 0)
        set(v) = sp.edit { putInt(K_FACTS_SINCE_AD, v) }

    /** When the last full-screen ad was shown (epoch millis), or 0 if never. */
    var lastAdAt: Long
        get() = sp.getLong(K_LAST_AD_AT, 0L)
        set(v) = sp.edit { putLong(K_LAST_AD_AT, v) }

    /** When a rewarded-ad unlock ends (epoch millis), or 0 if it was never unlocked. See monetization/Unlocks.kt. */
    fun unlockUntil(key: String): Long = sp.getLong("unlock_$key", 0L)

    fun setUnlockUntil(key: String, until: Long) = sp.edit { putLong("unlock_$key", until) }

    private companion object {
        const val K_LANG = "language"
        const val K_ARABIC_SIZE = "arabic_size"
        const val K_THEME = "theme"
        const val K_BOOKMARKS = "bookmarks"
        const val K_PREMIUM = "premium"
        const val K_FACTS_SINCE_AD = "facts_since_ad"
        const val K_LAST_AD_AT = "last_ad_at"
    }
}
