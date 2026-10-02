# Handoff: localisation, rewarded ads, ASO (work in progress, 2026-10-02)

## What is done
- **One language for everything.** The language picked in More drives the UI (res/values-xx/strings.xml, generated from
  `tools/ui_strings/<lang>.json` by `python3 tools/gen_strings.py`), the fact text (overlays `assets/i18n/<lang>.json`, built by
  `python3 tools/i18n.py build`) and the verse translation. Languages: en uz(Cyrillic) ru tr fr id ur(RTL) bn de.
  App code: `data/I18n.kt`, `ui/Localized.kt`, `AppViewModel.state` (applies the overlay).
- **Fact text translations** live in `tools/i18n/<lang>/NN.json` (keys = `tools/i18n/en/NN.json`). Store new ones with
  `python3 tools/i18n_put.py` (stdin sections `## <lang> <fact-id>`, one line per key in en order).
  Done for all 8 languages: chunks 00-03 (all) and chunk 04 except `locust-swarm`. **Still to translate:** `locust-swarm`
  (chunk 04) and every fact in chunks 05, 06, 07, 08 (28 facts). Untranslated keys fall back to English in the app.
  Check with `python3 tools/i18n.py check` (digits must match), then `python3 tools/i18n.py build`.
- **Quran wording rule (user, standing):** never our own translation of scripture. Verse quotations inside fact text
  (`tools/i18n/quran_quotes.json`, English “…” segments) are replaced by the matching words of the *trusted verse translation*
  of that language (`quran_quotes_trusted.json`, verified verbatim by `tools/i18n_quotes_put.py`). Workflow after translating a
  fact: write the translation with quotation marks in the same positions as English, then `python3 tools/i18n_quotes.py fix`;
  multi-quote sentences (mosquito-example.fit.1, bird-flight.hook, saba-dam.hook, smaller-than-atom.hook, sea-fire.gap.0,
  and any with 2+ quotes) need a manual grammar pass. `python3 tools/i18n_quotes.py check` must report 0 remaining.
  If a language had no trusted translation, verses stay English and More explains why (`TranslationInfo.trusted`).
- **Rewarded ads** (unobtrusive, user-initiated only): `monetization/Unlocks.kt`, `Ads.showRewarded`, `ui/components/UnlockGate.kt`.
  Unlocks (24 h per rewarded ad): speed-of-light lab, image sharing. "Ad break" card in More: 2 h without interstitials.
  Premium opens everything. Needs an AdMob **Rewarded** ad unit: `ADMOB_REWARDED_ID` in local.properties (test ID until then).
- **ASO:** localized launcher name (`app_name` per language), store listings for 9 languages in `store/listings/<lang>/`
  (+ README). Uzbek listing is Latin on purpose.
- Verse translation sources are shown in More (translator, plus publisher for Diyanet and Kemenag).

## Before releasing
- Bump `versionCode` in app/build.gradle.kts (production is versionCode 1).
- `./gradlew :app:testDebugUnitTest :app:bundleRelease`; do NOT install on the user's phone without asking.
- Build the overlays (`python3 tools/i18n.py build`) and regenerate strings (`python3 tools/gen_strings.py`) before building.
