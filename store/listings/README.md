# Play Store listings (ASO)

One folder per language: `title.txt` (max 30), `short.txt` (max 80), `full.txt` (max 4000). Paste them into
Play Console > Grow > Store presence > Store listings > Manage translations (add each language, then paste).

| Folder | Play Console language |
|---|---|
| en | English (United States) |
| ru | Russian |
| uz | Uzbek (written in Latin script on purpose, see below) |
| tr | Turkish |
| fr | French |
| id | Indonesian |
| ur | Urdu |
| bn | Bengali |
| de | German |

How the listings help people find the app
- The title carries the two phrases people actually search ("Quran" + "science"/"proofs") in the local word and script.
- The first lines of the full description repeat the search phrases naturally; the TOPICS paragraph lists the topics
  people look for (scientific miracles of the Quran, Big Bang, embryo, mountains, two seas...). Don't stuff more keywords:
  Google demotes keyword lists.
- The launcher name is localised too (`app_name` in every `values-xx/strings.xml`), so the app is found by name on the phone.
- Screenshots in `store/` should be re-shot per language once the translated app is final (caption text is part of ASO).

Uzbek: the app shows Uzbek verses in Cyrillic (the Sodik translation is Cyrillic), but most Uzbek searches on Google Play are
typed in Latin, so the Play listing is Latin. If you prefer Cyrillic, translate the same text; the in-app strings are Cyrillic.

Ads
- Rewarded ads need their own AdMob ad unit (format "Rewarded"). Put its ID in local.properties as `ADMOB_REWARDED_ID`
  next to `ADMOB_APP_ID` and `ADMOB_INTERSTITIAL_ID`. Until then release builds show Google's test rewarded ad.
