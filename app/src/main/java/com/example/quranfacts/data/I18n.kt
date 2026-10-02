package com.example.quranfacts.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import java.util.Locale

/**
 * One language for the whole app: the choice that picks the verse translation also picks the UI language
 * (res/values-xx/strings.xml) and the fact text. facts.json is English; other languages are flat
 * `key -> text` overlays in assets/i18n/<lang>.json, built by tools/i18n.py and applied here.
 */
object I18n {
    /** Languages with a UI, fact text and verse translation. */
    val LANGUAGES = listOf("en", "uz", "ru", "tr", "fr", "id", "ur", "bn", "de")

    private val RTL = setOf("ur")
    private val json = Json { ignoreUnknownKeys = true }

    fun isRtl(language: String) = language in RTL

    /** The phone's language if the app has it, otherwise English. (Android still reports Indonesian as "in".) */
    fun deviceLanguage(): String {
        val lang = Locale.getDefault().language.let { if (it == "in") "id" else it }
        return if (lang in LANGUAGES) lang else "en"
    }

    fun locale(language: String): Locale = Locale.forLanguageTag(language)

    /** The fact-text overlay for [language]; empty for English, which is the base file. */
    suspend fun overlay(context: Context, language: String): Map<String, String> {
        if (language == "en") return emptyMap()
        return withContext(Dispatchers.IO) {
            runCatching {
                context.assets.open("i18n/$language.json").bufferedReader(Charsets.UTF_8).use {
                    json.decodeFromString(MapSerializer(String.serializer(), String.serializer()), it.readText())
                }
            }.getOrDefault(emptyMap())
        }
    }

    /** [doc] with every string that has a translation replaced; anything missing stays English. */
    fun apply(doc: FactsDoc, o: Map<String, String>): FactsDoc {
        if (o.isEmpty()) return doc
        fun t(key: String, fallback: String) = o[key] ?: fallback
        return doc.copy(
            categories = doc.categories.map {
                it.copy(name = t("cat.${it.id}.name", it.name), tagline = t("cat.${it.id}.tagline", it.tagline))
            },
            claimTypes = doc.claimTypes.map {
                it.copy(
                    label = t("claim.${it.id}.label", it.label),
                    short = t("claim.${it.id}.short", it.short),
                    description = t("claim.${it.id}.description", it.description),
                )
            },
            facts = doc.facts.map { f ->
                val p = f.id
                f.copy(
                    title = t("$p.title", f.title),
                    hook = t("$p.hook", f.hook),
                    scienceHeading = t("$p.sciH", f.scienceHeading),
                    proofHeading = t("$p.proofH", f.proofHeading),
                    words = f.words.mapIndexed { n, w -> w.copy(en = t("$p.word.$n", w.en)) },
                    science = f.science.mapIndexed { n, s -> t("$p.science.$n", s) },
                    stats = f.stats.mapIndexed { n, s ->
                        s.copy(value = t("$p.stats.$n.value", s.value), label = t("$p.stats.$n.label", s.label))
                    },
                    discovery = f.discovery?.copy(
                        whenText = t("$p.disc.when", f.discovery.whenText),
                        who = t("$p.disc.who", f.discovery.who),
                    ),
                    proof = f.proof.mapIndexed { n, s ->
                        s.copy(
                            title = t("$p.proof.$n.title", s.title),
                            text = t("$p.proof.$n.text", s.text),
                            media = s.media?.let { m -> m.copy(caption = t("$p.proof.$n.cap", m.caption)) },
                        )
                    },
                    fit = Fit(
                        fits = f.fit.fits.mapIndexed { n, s -> t("$p.fit.$n", s) },
                        gaps = f.fit.gaps.mapIndexed { n, s -> t("$p.gap.$n", s) },
                    ),
                    media = f.media.mapIndexed { n, m -> m.copy(caption = t("$p.media.$n.cap", m.caption)) },
                )
            },
        )
    }
}
