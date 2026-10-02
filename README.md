# Quran Facts

An Android app (Kotlin, Jetpack Compose) that puts a verse of the Quran next to the science
that later described the same thing, with real photos and video to see it.

**Scope: facts only.** This app is about the discoveries and proofs found in the Quran. It is not a
Quran reader, a tajweed course, or a reading-habit tracker, so please don't add those here.

The **Timeline** tab puts every discovery on one line, starting from the revelation (610–632 CE), with
the number of years between them. Any fact can be shared as a picture (verse, translation, label).

Every fact carries an honesty label, so readers can tell a strong link from a debated one:

| Label | Meaning |
|---|---|
| **Scientific parallel** | The verse describes something specific that science has since measured or explained. |
| **Interpretive reading** | The link depends on how a word or verse is read; scholars and scientists disagree. |
| **Historical sign** | A statement about history that can be checked against records. |
| **Miracle (faith)** | A reported miraculous event. Science can neither prove nor disprove it. |

## How the content works

Nothing scientific or scriptural is typed into Kotlin. The app reads one generated file,
`app/src/main/assets/facts.json`, built from a curated source by a script:

```
tools/facts_source.py    <- you edit this: verses, science text, media choices
                            (later batches: facts_more.py, facts_third.py; the intro verse, 41:53)
tools/facts_evidence.py  <- and this: how it was proven, where it fits / where to be careful, sounds
                            (later batches: facts_evidence_more.py, facts_evidence_third.py)
tools/build_content.py   <- fetches, verifies, and writes facts.json
```

A fact is included only if (1) it is written in a verse and (2) people have actually discovered or
measured what the verse points to.

`build_content.py` does the boring, error-prone parts:

1. Fetches each cited ayah (Uthmani Arabic + 9 translations) from the Al Quran Cloud API.
2. Resolves every Wikimedia Commons photo/video: real URL, author, licence, and for video a
   phone-friendly <=480p VP9 transcode plus a poster frame.
3. Checks that every Arabic word listed in `words` really occurs in the cited verses.
4. Checks that source links resolve.

It exits non-zero on any error, so a broken fact cannot ship silently.

```bash
python3 tools/build_content.py                # full run (network)
python3 tools/build_content.py --offline      # use tools/.cache only
python3 tools/build_content.py --skip-links   # skip link checking
```

### Adding a fact

Add a `dict(...)` to `FACTS` in `tools/facts_source.py` (copy an existing one), then rerun the
script. Editorial rules, also enforced by unit tests where possible:

- Say what the verse says first, and what science found second. Never put words in the verse's mouth.
- Pick the label honestly. Every fact needs both `fits` and `gaps` (in `tools/facts_evidence.py`); `interpretive` and `miracle` facts need at least two caveats.
- If a claim is widely overstated online, say so under `gaps` (see `moon-split`, `two-seas`).
- Every fact needs a `proof` timeline: who did what, when, and what it showed, with the original evidence where it exists.
- A `discovery` places the fact on the app's Timeline at the first year in its text (for example
  `"1783 – 1927"` is 1783). If the text has no year, add `discoveryYear`. Gaps are counted from 632 CE.
- Media must be public domain or openly licensed (Wikimedia Commons).
- Verify numbers and citations against a primary source. (One citation in this project was
  caught as wrong during authoring because it was checked, not assumed.)

## Build and test

```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest      # data integrity + speed-of-light math
./gradlew assembleRelease        # R8-minified, ~3 MB
```

Requires JDK 17+ and the Android SDK (compileSdk 36). Dependency versions are pinned to a
set that builds with AGP 8.12.3; newer AndroidX (Compose 1.12, core 1.19) needs AGP 9.

## Audio

- **Verse recitation** (Mishary Alafasy) streams from the Islamic Network CDN behind Al Quran Cloud:
  `https://cdn.islamic.network/quran/audio/128/ar.alafasy/{global ayah number}.mp3`. The build script
  stores the URL per verse.
- **Science audio** (for example a sonification of the Big Bang's afterglow) comes from Wikimedia Commons
  and is listed in `sounds` in `tools/facts_evidence.py`.
- One shared player (`AudioHub`) means two clips can never overlap; it stops when the app leaves the
  foreground.

## Ads and the ad-free subscription

Free users see a full-screen ad now and then when they open a fact; subscribers see none. Everything else is free.

- **When ads show** (`monetization/AdPolicy.kt`): at most once every 4 facts opened, never twice within 3 minutes,
  never while a recitation is playing, and only after consent where the law requires it. Ads are limited to the
  "G" content rating.
- **Consent** (`monetization/Ads.kt`): Google's User Messaging Platform shows the GDPR form in the EEA, UK and
  Switzerland, and "Privacy choices" appears in More where users must be able to change it.
- **Subscription** (`monetization/Premium.kt`): Google Play Billing, one subscription product `premium`. Every base
  plan and offer you add to it (monthly, yearly, a free trial) appears on the paywall with Google Play's price.
  Purchases are acknowledged, and ownership is re-checked on every resume.

Debug builds always show Google's **test** ads. Two debug-only switches help testing:

```bash
adb shell am start -n com.example.quranfacts/.MainActivity --ez debugConsentEea true
adb shell am start -n com.example.quranfacts/.MainActivity --ez debugDemoPlans true
```

The first shows the consent form as if you were in Europe; the second fills the paywall with sample plans.

### Going live

1. Change `applicationId` from `com.example.quranfacts` first: Play rejects `com.example`, and the subscription is tied
   to the package name.
2. **AdMob**: create the app and an *Interstitial* ad unit, then add to `local.properties`:
   ```
   ADMOB_APP_ID=ca-app-pub-XXXXXXXXXXXXXXXX~YYYYYYYYYY
   ADMOB_INTERSTITIAL_ID=ca-app-pub-XXXXXXXXXXXXXXXX/ZZZZZZZZZZ
   ```
   In AdMob, create a GDPR message under *Privacy & messaging*, and consider blocking sensitive categories
   (alcohol, gambling, dating) under *Blocking controls*.
3. **Play Console**: upload a build, then create a subscription with product ID `premium` and its base plans and prices.
   Add licence testers to try purchases without being charged.
4. Publish a privacy policy, fill in the Data safety form (advertising ID, purchase history), and add an
   `app-ads.txt` file to your developer website.

## Testing on an emulator

If images and audio suddenly stop loading on a long-running emulator, check `ping upload.wikimedia.org`
inside it. An emulator keeps the DNS server it had when it booted, so switching networks on the host
(for example to a phone hotspot) leaves it unable to resolve names. Restart the emulator.

## Wikimedia notes (learned the hard way)

- Wikimedia's edge answers **HTTP 429** to requests carrying ExoPlayer's `Icy-MetaData` header or
  Java's default `Accept` header. The app streams video through the shared OkHttp client
  (`Http.client`) and strips the header. See `MediaSection.kt`.
- The first request for a large image's thumbnail can take many seconds while Wikimedia
  generates it. The UI shows a tinted placeholder until it arrives.

## Before a public release

- Change `applicationId` / `namespace` from `com.example.quranfacts`.
- Media is hotlinked from Wikimedia Commons. That is fine for development; for a public app
  with real traffic, mirror it on your own CDN (licences allow it; keep the attribution).
- Review redistribution terms for the Quran text (Tanzil, CC BY 3.0 with conditions) and each
  translation before publishing.
- Review the terms for the recitation audio (Islamic Network CDN) before publishing, or host your own.
- The science explanations are English only; verse translations already cover 9 languages.
