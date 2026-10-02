package com.example.quranfacts

import com.example.quranfacts.data.FactsDoc
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * Guards the generated facts.json (see tools/build_content.py) so a bad regeneration or a
 * hand edit cannot silently ship a broken or dishonest fact.
 */
class FactsDataTest {
    private val doc: FactsDoc = Json { ignoreUnknownKeys = true }.decodeFromString(
        FactsDoc.serializer(),
        File("src/main/assets/facts.json").readText(Charsets.UTF_8),
    )

    @Test
    fun factIdsAreUnique() {
        val ids = doc.facts.map { it.id }
        assertEquals(ids.distinct().size, ids.size)
    }

    @Test
    fun everyFactReferencesKnownCategoryAndClaimType() {
        val categories = doc.categories.map { it.id }.toSet()
        val claims = doc.claimTypes.map { it.id }.toSet()
        doc.facts.forEach {
            assertTrue("${it.id}: unknown category ${it.category}", it.category in categories)
            assertTrue("${it.id}: unknown claim type ${it.claimType}", it.claimType in claims)
        }
    }

    @Test
    fun everyFactHasContentAndAHeroImage() {
        doc.facts.forEach {
            assertTrue("${it.id}: no verses", it.verses.isNotEmpty())
            assertTrue("${it.id}: no science text", it.science.isNotEmpty())
            assertTrue("${it.id}: no hero image", it.heroImage != null)
        }
    }

    @Test
    fun everyVerseHasArabicAndEveryTranslation() {
        val languages = doc.translations.keys
        doc.facts.flatMap { it.verses }.forEach { v ->
            assertTrue("${v.ref}: empty Arabic", v.ar.isNotBlank())
            languages.forEach { lang ->
                assertFalse("${v.ref}: missing $lang translation", v.tr[lang].isNullOrBlank())
            }
        }
    }

    @Test
    fun citedVersesDoNotStartWithTheBismillah() {
        // Some APIs glue the Bismillah onto ayah 1 of each surah (except Al-Fatiha, where it is the verse).
        // Strip vowel marks and the tatweel, and unify alef wasla, before comparing.
        fun plain(s: String) = s.filterNot { it.code in 0x064B..0x065F || it.code == 0x0670 || it.code == 0x0640 }
            .replace('ٱ', 'ا')
        val bismillah = plain("بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ")
        doc.facts.flatMap { it.verses }.filter { it.surah != 1 }.forEach {
            assertFalse("${it.ref}: starts with the Bismillah", plain(it.ar).startsWith(bismillah))
        }
    }

    @Test
    fun weakerClaimsSpellOutAtLeastTwoCaveats() {
        doc.facts.filter { it.claimType == "interpretive" || it.claimType == "miracle" }.forEach {
            assertTrue("${it.id}: ${it.claimType} claim needs at least 2 items under 'gaps'", it.fit.gaps.size >= 2)
        }
    }

    @Test
    fun everyFactExplainsHowItWasFoundOut() {
        doc.facts.forEach { f ->
            assertTrue("${f.id}: needs at least 2 proof steps", f.proof.size >= 2)
            f.proof.forEach {
                assertTrue("${f.id}: blank proof step", it.year.isNotBlank() && it.title.isNotBlank() && it.text.isNotBlank())
            }
        }
    }

    @Test
    fun everyFactShowsBothWhereItFitsAndWhereToBeCareful() {
        doc.facts.forEach {
            assertTrue("${it.id}: nothing under 'fits'", it.fit.fits.isNotEmpty())
            assertTrue("${it.id}: nothing under 'gaps' (an honest match always has some)", it.fit.gaps.isNotEmpty())
        }
    }

    @Test
    fun everyVerseHasARecitation() {
        doc.facts.flatMap { it.verses }.forEach {
            val url = it.audio
            assertTrue("${it.ref}: no recitation", url != null)
            assertTrue("${it.ref}: not an mp3 over https: $url", url!!.startsWith("https://") && url.endsWith(".mp3"))
        }
    }

    @Test
    fun evidenceMediaIsHttpsAndLicensed() {
        doc.facts.flatMap { f -> f.proof.mapNotNull { it.media } }.forEach { m ->
            assertTrue("not https: ${m.url}", m.url.startsWith("https://"))
            assertFalse("tracking parameter in ${m.url}", m.url.contains("utm_"))
            assertTrue("${m.page}: missing licence", m.license.isNotBlank())
            assertTrue("${m.page}: missing caption", m.caption.isNotBlank())
        }
    }

    @Test
    fun audioClipsHaveASourceAndALicence() {
        doc.facts.flatMap { it.media }.filter { it.isAudio }.forEach {
            assertTrue("not https: ${it.url}", it.url.startsWith("https://"))
            assertTrue("${it.page}: missing licence", it.license.isNotBlank())
        }
    }

    @Test
    fun mediaUrlsAreHttpsAndCarryNoTracking() {
        doc.facts.flatMap { it.media }.forEach { m ->
            listOf(m.url, m.thumb, m.big).forEach {
                assertTrue("not https: $it", it.startsWith("https://"))
                assertFalse("tracking parameter in $it", it.contains("utm_"))
            }
            assertTrue("${m.page}: missing licence", m.license.isNotBlank())
        }
    }

    @Test
    fun videosPointAtPhoneFriendlyTranscodes() {
        doc.facts.flatMap { it.media }.filter { it.isVideo }.forEach {
            assertTrue("${it.url}: not a ≤480p VP9 transcode", it.url.contains("/transcoded/") || it.url.endsWith(".webm"))
        }
    }

    @Test
    fun everyDiscoveryHasAYearAfterTheRevelation() {
        val thisYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        doc.facts.mapNotNull { f -> f.discovery?.let { f.id to it } }.forEach { (id, d) ->
            val year = d.year
            assertTrue("$id: discovery '${d.whenText}' has no year for the timeline", year != null)
            assertTrue("$id: discovery year $year is not after 632 CE", year!! > 632 && year <= thisYear)
            Regex("\\d{4}").find(d.whenText)?.let {
                assertEquals("$id: the timeline should place '${d.whenText}' at its start", it.value.toInt(), year)
            }
        }
    }

    @Test
    fun theHomeScreenVerseIsFortyOneFiftyThree() {
        val intro = doc.intro
        assertTrue("facts.json has no intro verse", intro != null)
        assertEquals("41:53", intro!!.ref)
        doc.translations.keys.forEach { assertFalse("intro missing $it translation", intro.tr[it].isNullOrBlank()) }
    }

    @Test
    fun theSpeedOfLightFactShowsTheLab() {
        assertEquals("lightspeed", doc.facts.first { it.id == "speed-of-light" }.widget)
    }
}
