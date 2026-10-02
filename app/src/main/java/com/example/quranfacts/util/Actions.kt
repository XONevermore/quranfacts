package com.example.quranfacts.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.FileProvider
import com.example.quranfacts.BuildConfig
import com.example.quranfacts.R
import com.example.quranfacts.data.Fact
import com.example.quranfacts.data.Verse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Locale
import java.util.TimeZone

/** Hosted from docs/privacy.html (GitHub Pages). Must be the same address entered in Play Console > App content > Privacy policy. */
const val PRIVACY_POLICY_URL = "https://xonevermore.github.io/quranfacts/privacy.html"

fun Context.openUrl(url: String) {
    runCatching {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }.onFailure { Toast.makeText(this, getString(R.string.toast_no_app), Toast.LENGTH_SHORT).show() }
}

fun Context.shareText(text: String) {
    val send = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    startActivity(Intent.createChooser(send, null).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}

/**
 * Shares a picture (plus a caption) through the system share sheet. The file goes to cache/share, which
 * the FileProvider in the manifest exposes read-only to the app the user picks.
 */
suspend fun Context.shareImage(bitmap: Bitmap, fileName: String, caption: String) {
    val file = withContext(Dispatchers.IO) {
        File(cacheDir, "share").apply { mkdirs() }.resolve(fileName).also { f ->
            f.outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        }
    }
    val uri = FileProvider.getUriForFile(this, "$packageName.share", file)
    val send = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        putExtra(Intent.EXTRA_TEXT, caption)
        clipData = ClipData.newRawUri(null, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(Intent.createChooser(send, null).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}

fun Context.copyText(label: String, text: String) {
    val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    cm.setPrimaryClip(ClipData.newPlainText(label, text))
    Toast.makeText(this, getString(R.string.toast_copied), Toast.LENGTH_SHORT).show()
}

fun Context.verseShareText(verse: Verse, language: String): String {
    val tr = verse.tr[language] ?: verse.tr["en"].orEmpty()
    return "${verse.ar}\n\n“$tr”\n— ${getString(R.string.quran_word)} ${verse.surah}:${verse.ayah} (${verse.surahEn})\n${verse.quranComUrl}"
}

/** The installed app's package, for the Google Play link in shared text. */
private const val PACKAGE = BuildConfig.APPLICATION_ID

fun Context.factShareText(fact: Fact, language: String, claimLabel: String): String {
    val v = fact.verses.first()
    val tr = v.tr[language] ?: v.tr["en"].orEmpty()
    return buildString {
        append("${fact.title}\n")
        append("${fact.hook}\n\n")
        append("“$tr”\n— ${getString(R.string.quran_word)} ${v.surah}:${v.ayah} (${v.surahEn})\n\n")
        append("[$claimLabel] ${getString(R.string.share_more)}\n")
        append("https://play.google.com/store/apps/details?id=$PACKAGE")
    }
}

/** A stable "fact of the day": the same fact all day, a new one tomorrow. */
fun <T> pickOfTheDay(items: List<T>): T? {
    if (items.isEmpty()) return null
    // java.time needs Android 8 (API 26) and this app supports 7, so count local days by hand.
    val now = System.currentTimeMillis()
    val day = ((now + TimeZone.getDefault().getOffset(now)) / 86_400_000L).toInt()
    return items[day % items.size]
}

fun String.toColor(): Color = Color(android.graphics.Color.parseColor(this))

/** 1151 -> "1,151". Digits stay Western and comma-grouped in every language, so numbers look the same everywhere. */
fun Int.grouped(): String = String.format(Locale.US, "%,d", this)
