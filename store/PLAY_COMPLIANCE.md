# Google Play: policy and privacy answers

Everything the Play Console asks for that is not in the listing text. Answers match the app as shipped (version 1.1) and
`docs/privacy.html`. If the app changes (new SDK, new permission), update both.

## 1. Privacy policy
- Hosted from `docs/privacy.html`. Turn on GitHub Pages (repo Settings > Pages > Deploy from branch > `main` > `/docs`); the URL is
  `https://xonevermore.github.io/quranfacts/privacy.html`. It must open without a login.
- The same URL goes in Play Console > Grow > Store presence > Store settings > Privacy policy, and is built into the app
  (`PRIVACY_POLICY_URL` in `util/Actions.kt`, button in More). Change both together if you host it elsewhere.
- The contact email in the policy is the one on the Google account; change it in `docs/privacy.html` if you use another.

## 2. App content
| Question | Answer |
|---|---|
| Target audience | 13+ (choose 13-15, 16-17, 18+; do **not** choose any age under 13). Not designed for children, so the Families policy does not apply |
| Ads | Yes, the app contains ads |
| News app / COVID / government / financial / health | No |
| Content rating (IARC) | Reference/educational. No violence, sexual content, gambling, user-generated content or location sharing. Expected: Everyone / PEGI 3 |
| Data safety | Section 3 |
| Advertising ID | Yes, used for advertising (AdMob). Declare it; the app's manifest gets `AD_ID` from the Ads SDK |

## 3. Data safety form
Data collected / shared: **collected = yes (by Google SDKs), encrypted in transit = yes, deletion request = no data held by us.**

| Data type | Collected | Shared | Purpose | Optional |
|---|---|---|---|---|
| Device or other IDs (advertising ID) | Yes | Yes (Google AdMob) | Advertising or marketing; Fraud prevention | Yes, can be declined in the consent form / reset in Android settings |
| Approximate location (derived from IP by the ad SDK) | Yes | Yes (Google AdMob) | Advertising; Fraud prevention | no |
| App interactions (ad interactions) | Yes | Yes (Google AdMob) | Advertising; Analytics | no |
| Diagnostics (crash logs / performance of the ad SDK) | Yes | Yes (Google AdMob) | Analytics | no |
| Purchase history | Yes (subscription status via Google Play Billing) | No | App functionality | no |

Not collected: name, email, address, phone, precise location, contacts, photos/files, audio, messages, health/fitness,
financial info (payments are handled by Google Play), web browsing, search history, calendar. Select "Data is processed
ephemerally: No", "Users can request deletion: no data is collected by us" (the policy explains how to clear ad data).
Mark Google AdMob as the third party; Google's SDK data is declared under "shared".

## 4. Ads and monetisation rules the app follows
- Interstitials only at natural breaks (opening a fact), at most one per 4 facts and never within 3 minutes of the last one;
  never during recitation playback; never at app launch or on exit (`monetization/AdPolicy.kt`).
- Rewarded ads are always user-initiated and say what the reward is (24 h unlock of an optional extra, or 2 h without full-screen ads).
- Ad content rating capped at G; UMP consent form for EEA/UK/CH and US states, with "Privacy choices" in More.
- AdMob: add `app-ads.txt` to the developer website listed in Play Console. In AdMob > Blocking controls block sensitive categories
  (gambling, dating, alcohol, politics, religion) so that no ads conflict with the content.
- Subscription: price, renewal and cancellation terms are shown on the paywall before purchase, with a link to manage in Google Play.

## 5. Store listing rules
- Titles are at most 30 characters, descriptions have no keyword lists, no price/ranking claims, no emoji/ALL CAPS in titles.
- Quran verses always come from published named translators (shown in More); the app is a learning aid, not tafsir or a fatwa (disclaimer in More).
- Screenshots in `store/` are English. Add localised screenshots per language when ready; the app itself is fully localised.

## 6. Before pressing "Send for review"
1. Enable GitHub Pages and open the privacy URL in a browser.
2. Create the AdMob Rewarded unit and put all three AdMob IDs in `local.properties` (release builds otherwise show test ads, which Play rejects in production).
3. Sign with the upload key (`keystore/keystore.properties`), build `./gradlew :app:testDebugUnitTest :app:bundleRelease`.
4. Create the `premium` subscription (base plan + price) in Play Console and activate it.
5. Fill in sections 2 and 3 above, upload listings from `store/listings/`, release notes, then submit.
