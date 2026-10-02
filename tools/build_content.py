#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Builds app/src/main/assets/facts.json from tools/facts_source.py.

What it does (so nobody has to type a verse or a URL by hand):
  1. Fetches every cited ayah from the Al Quran Cloud API: Uthmani Arabic text plus
     translations (cached in tools/.cache so re-runs are fast and offline-friendly).
  2. Resolves each Wikimedia Commons file: real URL, size, license, author, and for
     videos a phone-friendly (<=480p VP9) transcode + poster frame.
  3. Verifies that each Arabic word in `words` really occurs in the cited verses.
  4. Checks that source links resolve.
Exit code is non-zero if anything is wrong, so a broken fact never ships silently.

Usage:  python3 tools/build_content.py [--offline] [--skip-links]
"""
import html
import json
import os
import re
import sys
import time
import urllib.error
import urllib.parse
import urllib.request

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from facts_evidence import EVIDENCE  # noqa: E402
from facts_source import CATEGORIES, CLAIM_TYPES, FACTS, INTRO_VERSE  # noqa: E402

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
CACHE_DIR = os.path.join(ROOT, "tools", ".cache")
OUT = os.path.join(ROOT, "app", "src", "main", "assets", "facts.json")
UA = "QuranFactsApp/0.1 (personal project; contact sardorurdushev75@gmail.com)"

ARABIC_EDITION = "quran-uthmani"
# Recitation: Mishary Rashid Alafasy, served by the Islamic Network CDN behind Al Quran Cloud.
AUDIO_URL = "https://cdn.islamic.network/quran/audio/128/ar.alafasy/{number}.mp3"
TRANSLATIONS = {
    # app language code: (edition id, display name, native language name)
    "en": ("en.sahih", "Saheeh International", "English"),
    "uz": ("uz.sodik", "Muhammad Sodik Muhammad Yusuf", "Ўзбекча"),
    "ru": ("ru.kuliev", "Elmir Kuliev", "Русский"),
    "tr": ("tr.diyanet", "Diyanet İşleri", "Türkçe"),
    "fr": ("fr.hamidullah", "Muhammad Hamidullah", "Français"),
    "id": ("id.indonesian", "Kementerian Agama Republik Indonesia", "Bahasa Indonesia"),
    "ur": ("ur.jalandhry", "Fateh Muhammad Jalandhry", "اردو"),
    "bn": ("bn.bengali", "Muhiuddin Khan", "বাংলা"),
    "de": ("de.bubenheim", "Bubenheim & Elyas", "Deutsch"),
}

# Only official publishers are named; every other translation is credited to its translator alone.
PUBLISHERS = {
    "tr": "Diyanet İşleri Başkanlığı (Presidency of Religious Affairs)",
    "id": "Indonesian Ministry of Religious Affairs (Kemenag)",
}

errors, warnings = [], []
OFFLINE = "--offline" in sys.argv
SKIP_LINKS = "--skip-links" in sys.argv


def http_get(url, retries=4, timeout=40):
    last = None
    for i in range(retries):
        try:
            req = urllib.request.Request(url, headers={"User-Agent": UA})
            with urllib.request.urlopen(req, timeout=timeout) as r:
                return r.status, r.read()
        except urllib.error.HTTPError as e:
            if e.code in (429, 500, 502, 503, 504):
                last = e
                time.sleep(2 * (i + 1))
                continue
            return e.code, b""
        except Exception as e:  # timeouts, resets
            last = e
            time.sleep(2 * (i + 1))
    raise RuntimeError(f"GET failed: {url}: {last}")


def http_json(url):
    status, body = http_get(url)
    if status != 200:
        raise RuntimeError(f"HTTP {status} for {url}")
    return json.loads(body)


# ----------------------------------------------------------------------------
# Quran text
# ----------------------------------------------------------------------------
def expand_ref(ref):
    surah, rest = ref.split(":")
    if "-" in rest:
        a, b = rest.split("-")
        return [(int(surah), n) for n in range(int(a), int(b) + 1)]
    return [(int(surah), int(rest))]


BISMILLAH_NORM = ["بسم", "الله", "الرحمن", "الرحيم"]


def strip_bismillah(surah, ayah, text):
    """Al Quran Cloud glues the Bismillah onto the first ayah of every surah except 1 and 9. Remove it,
    together with stray byte-order marks (the reader shows the Bismillah as a header instead)."""
    text = text.replace("\ufeff", "").strip()
    if ayah == 1 and surah not in (1, 9):
        toks = text.split()
        if [norm(t) for t in toks[:4]] == BISMILLAH_NORM:
            text = " ".join(toks[4:])
    return text


def fetch_ayah(surah, ayah):
    os.makedirs(CACHE_DIR, exist_ok=True)
    path = os.path.join(CACHE_DIR, f"ayah_{surah}_{ayah}.json")
    if os.path.exists(path):
        cached = json.load(open(path, encoding="utf-8"))
        cached["ar"] = strip_bismillah(surah, ayah, cached["ar"])
        if "number" in cached or OFFLINE:
            return cached
    if OFFLINE:
        raise RuntimeError(f"offline and {surah}:{ayah} not cached")
    editions = ",".join([ARABIC_EDITION] + [v[0] for v in TRANSLATIONS.values()])
    data = http_json(f"https://api.alquran.cloud/v1/ayah/{surah}:{ayah}/editions/{editions}")["data"]
    by_id = {e["edition"]["identifier"]: e for e in data}
    ar = by_id[ARABIC_EDITION]
    rec = {
        "number": ar["number"],
        "surah": surah,
        "ayah": ayah,
        "surahEn": ar["surah"]["englishName"],
        "surahAr": ar["surah"]["name"],
        "surahMeaning": ar["surah"]["englishNameTranslation"],
        "ar": strip_bismillah(surah, ayah, ar["text"]),
        "tr": {code: by_id[ed[0]]["text"] for code, ed in TRANSLATIONS.items()},
    }
    json.dump(rec, open(path, "w", encoding="utf-8"), ensure_ascii=False)
    time.sleep(0.25)
    return rec


_DIACRITICS = re.compile("[ؐ-ًؚ-ٰٟۖ-ۭ࣓-ࣿـ]")


def norm(s):
    s = s.replace("ٱ", "ا")
    s = _DIACRITICS.sub("", s)
    for a, b in (("أ", "ا"), ("إ", "ا"), ("آ", "ا"), ("ى", "ي"), ("ئ", "ي"), ("ؤ", "و"), ("ة", "ه"), ("ء", "")):
        s = s.replace(a, b)
    return s


# ----------------------------------------------------------------------------
# Wikimedia Commons
# ----------------------------------------------------------------------------
COMMONS = "https://commons.wikimedia.org/w/api.php"


def commons(params):
    return http_json(COMMONS + "?" + urllib.parse.urlencode({"action": "query", **params, "format": "json", "formatversion": "2"}))


def strip_utm(url):
    """The API tacks tracking parameters onto every URL; they mean nothing to the app."""
    return re.sub(r"[?&]utm_[^#]*$", "", url) if url else url


def strip_html(s):
    s = html.unescape(re.sub(r"<[^>]+>", "", s or ""))
    return re.sub(r"\s+", " ", s).strip()


# Wikimedia only serves thumbnails at these widths. Direct downloads of original files are throttled far
# harder than thumbnails (HTTP 429), so even small images must be requested as a (slightly smaller) thumbnail.
STANDARD_WIDTHS = [120, 250, 330, 500, 960, 1280, 1920, 3840]


def small_original_thumb(title, orig_width):
    """A thumbnail URL for an image narrower than the requested width, or None if it is too small."""
    smaller = [w for w in STANDARD_WIDTHS if w < orig_width]
    if not smaller:
        return None
    data = commons({"titles": title, "prop": "imageinfo", "iiprop": "url", "iiurlwidth": smaller[-1]})
    pages = data["query"]["pages"]
    ii = (pages[0].get("imageinfo") or [{}])[0] if pages else {}
    thumb = strip_utm(ii.get("thumburl") or "")
    return thumb if thumb and "/thumb/" in thumb else None


def resolve_media(files):
    """files: list of 'File:xxx' -> dict title -> metadata"""
    out = {}
    os.makedirs(CACHE_DIR, exist_ok=True)
    cache_path = os.path.join(CACHE_DIR, "commons.json")
    cache = json.load(open(cache_path, encoding="utf-8")) if os.path.exists(cache_path) else {}
    todo = [f for f in files if f not in cache or "mediatype" not in cache[f]]
    if todo and OFFLINE:
        raise RuntimeError(f"offline and media not cached: {todo}")
    for i in range(0, len(todo), 20):
        batch = todo[i:i + 20]
        common = dict(titles="|".join(batch), prop="imageinfo|videoinfo",
                      iiprop="url|size|mime|mediatype|extmetadata", iiextmetadatafilter="LicenseShortName|Artist|Credit",
                      viprop="duration|derivatives|size|url")
        big = commons({**common, "iiurlwidth": 1280})
        small = commons({**common, "iiurlwidth": 500})
        small_by = {p["title"]: p for p in small["query"]["pages"]}
        norm_map = {n["from"]: n["to"] for n in big["query"].get("normalized", [])}
        for p in big["query"]["pages"]:
            title = p["title"]
            if p.get("missing") or "imageinfo" not in p:
                errors.append(f"media missing on Commons: {title}")
                continue
            ii = p["imageinfo"][0]
            sp = small_by.get(title, {})
            sii = (sp.get("imageinfo") or [{}])[0]
            md = ii.get("extmetadata", {})
            entry = {
                "title": title,
                "mime": ii["mime"],
                "mediatype": ii.get("mediatype", "BITMAP"),
                "w": ii.get("thumbwidth") or ii.get("width"),
                "h": ii.get("thumbheight") or ii.get("height"),
                "origW": ii.get("width"), "origH": ii.get("height"),
                "url": strip_utm(ii.get("thumburl") or ii["url"]),
                "thumb": strip_utm(sii.get("thumburl") or ii.get("thumburl") or ii["url"]),
                "page": ii["descriptionurl"],
                "license": strip_html(md.get("LicenseShortName", {}).get("value", "")),
                "credit": strip_html(md.get("Artist", {}).get("value", "")) or strip_html(md.get("Credit", {}).get("value", "")),
                "size": ii.get("size"),
            }
            if entry["mediatype"] in ("BITMAP", "DRAWING") and "/thumb/" not in entry["url"]:
                fixed = small_original_thumb(title, ii.get("width") or 0)
                if fixed:
                    entry["url"] = fixed
                    if "/thumb/" not in entry["thumb"]:
                        entry["thumb"] = fixed
                    time.sleep(0.3)
            if entry["mediatype"] == "VIDEO":
                vi = (p.get("videoinfo") or [{}])[0]
                entry["duration"] = round(vi.get("duration") or 0)
                entry["videoUrl"] = pick_video(vi, title)
            elif entry["mediatype"] == "AUDIO":
                vi = (p.get("videoinfo") or [{}])[0]
                entry["duration"] = round(vi.get("duration") or 0)
                entry["audioUrl"] = strip_utm(ii["url"])
            cache[title] = entry
            for frm, to in norm_map.items():
                if to == title:
                    cache[frm] = entry
        time.sleep(0.5)
    json.dump(cache, open(cache_path, "w", encoding="utf-8"), ensure_ascii=False)
    for f in files:
        if f in cache:
            out[f] = cache[f]
    return out


def pick_video(vi, title):
    ders = vi.get("derivatives") or []
    webm = [d for d in ders if d["type"].startswith("video/webm") and "vp9" in d["type"] and d.get("height")]
    webm.sort(key=lambda d: d["height"])
    good = [d for d in webm if d["height"] <= 480]
    if good:
        return strip_utm(good[-1]["src"])
    if webm:
        return strip_utm(webm[0]["src"])  # smallest available
    if vi.get("size", 1 << 30) <= 40 * (1 << 20):
        return strip_utm(vi.get("url"))
    errors.append(f"no phone-friendly transcode for {title}")
    return None


# Commons sometimes stores the "author" in an odd form (a transliterated name, a username).
CREDIT_OVERRIDES = {
    "File:Hubble's law original 1929.png": "Edwin Hubble, PNAS 1929",
}


def clean_credit(c):
    c = re.sub(r"\(talk\)", "", c)
    c = re.sub(r"^\s*(User:|Author:|Creator:)\s*", "", c)
    parts = [p.strip() for p in re.split(r"[;\n]", c) if p.strip()]
    out = ""
    for p in parts:
        nxt = (out + "; " + p) if out else p
        if len(nxt) > 70:
            out = out + " et al." if out else p[:67].rsplit(" ", 1)[0] + "…"
            break
        out = nxt
    return out.strip(" ,;")


# ----------------------------------------------------------------------------
# Links
# ----------------------------------------------------------------------------
SOFT_HOSTS = ("sunnah.com", "islamawareness.net", "answering-islam.org", "esa.int")  # bot-checks / flaky


def check_link(url):
    try:
        status, _ = http_get(url, retries=2, timeout=25)
    except Exception as e:
        return "soft", str(e)
    if status < 400:
        return "ok", status
    if status in (401, 403, 429) or any(h in url for h in SOFT_HOSTS):
        return "soft", status
    return "bad", status


# ----------------------------------------------------------------------------
def make_item(media, file, caption):
    """One Media JSON object from a resolved Commons file, or None if it cannot be played."""
    m = media.get(file)
    if not m:
        return None
    kind = {"VIDEO": "video", "AUDIO": "audio"}.get(m.get("mediatype"), "image")
    if kind == "video" and not m.get("videoUrl"):
        return None
    url = m["videoUrl"] if kind == "video" else m["audioUrl"] if kind == "audio" else m["url"]
    return {
        "type": kind,
        "url": url,
        "thumb": m["thumb"],
        "big": m["url"],
        "w": m["w"], "h": m["h"],
        "duration": m.get("duration", 0) if kind != "image" else 0,
        "caption": caption,
        "credit": CREDIT_OVERRIDES.get(file) or clean_credit(m["credit"]),
        "license": m["license"],
        "page": m["page"],
    }


def discovery_year(fact):
    """The year the app's timeline places a discovery at: an explicit `discoveryYear`, else the first year in `when`."""
    if fact.get("discoveryYear"):
        return fact["discoveryYear"]
    m = re.search(r"\d{4}", fact["discovery"][0])
    return int(m.group()) if m else None


def main():
    verse_cache = {}
    media_files = []

    def verse(surah, ayah):
        if (surah, ayah) not in verse_cache:
            verse_cache[(surah, ayah)] = fetch_ayah(surah, ayah)
        v = dict(verse_cache[(surah, ayah)])
        v["audio"] = AUDIO_URL.format(number=v.pop("number"))
        return v

    def want(file):
        if file and file not in media_files:
            media_files.append(file)

    fact_ids = {f["id"] for f in FACTS}
    for missing in sorted(fact_ids - set(EVIDENCE)):
        errors.append(f"[{missing}] has no entry in facts_evidence.py")
    for extra in sorted(set(EVIDENCE) - fact_ids):
        errors.append(f"[{extra}] is in facts_evidence.py but not in facts_source.py")
    for f in FACTS:
        for m in f["media"]:
            want(m[0])
        ev = EVIDENCE.get(f["id"], {})
        for step in ev.get("proof", []):
            want(step[3])
        for snd in ev.get("sounds", []):
            want(snd[0])

    print(f"Resolving {len(media_files)} Commons files…")
    media = resolve_media(media_files)

    out_facts = []
    for f in FACTS:
        ev = EVIDENCE.get(f["id"], {})
        verses = [verse(surah, ayah) for ref in f["verses"] for surah, ayah in expand_ref(ref)]
        arabic_all = norm(" ".join(v["ar"] for v in verses))
        words = []
        for ar, tr, en, root in f.get("words", []):
            if norm(ar) not in arabic_all:
                errors.append(f"[{f['id']}] word not found in cited verses: {ar} ({tr})")
            words.append({"ar": ar, "tr": tr, "en": en, "root": root})

        items = [i for i in (make_item(media, file, caption) for file, caption in f["media"]) if i]
        items += [i for i in (make_item(media, file, caption) for file, caption in ev.get("sounds", [])) if i]
        if not any(i["type"] == "image" for i in items):
            errors.append(f"[{f['id']}] has no image (needed for hero)")

        proof = []
        for year, title, text, file, caption in ev.get("proof", []):
            step = {"year": year, "title": title, "text": text, "media": None}
            if file:
                step["media"] = make_item(media, file, caption or title)
                if step["media"] is None:
                    errors.append(f"[{f['id']}] proof media could not be resolved: {file}")
            proof.append(step)
        fit = ev.get("fit", {"fits": [], "gaps": []})
        if not proof:
            errors.append(f"[{f['id']}] has no proof steps")
        if not fit["fits"] or not fit["gaps"]:
            errors.append(f"[{f['id']}] needs both 'fits' and 'gaps'")

        sources = []
        for title, url in f["sources"]:
            if SKIP_LINKS:
                sources.append({"title": title, "url": url})
                continue
            state, info = check_link(url)
            if state == "bad":
                errors.append(f"[{f['id']}] dead source link ({info}): {url}")
            elif state == "soft":
                warnings.append(f"[{f['id']}] could not verify link ({info}): {url}")
            sources.append({"title": title, "url": url})

        disc = f.get("discovery")
        year = discovery_year(f) if disc else None
        if disc and year is None:
            errors.append(f"[{f['id']}] discovery '{disc[0]}' has no year; add discoveryYear")
        out_facts.append({
            "id": f["id"],
            "title": f["title"],
            "hook": f["hook"],
            "category": f["category"],
            "claimType": f["claimType"],
            "widget": f.get("widget"),
            "scienceHeading": f.get("scienceHeading", "What science found"),
            "verses": verses,
            "words": words,
            "science": f["science"],
            "stats": [{"value": v, "label": l} for v, l in f.get("stats", [])],
            "discovery": {"when": disc[0], "who": disc[1], "year": year} if disc else None,
            "proofHeading": ev.get("proofHeading", "How it was found out"),
            "proof": proof,
            "fit": fit,
            "media": items,
            "sources": sources,
        })

    doc = {
        "schema": 2,
        "categories": CATEGORIES,
        "claimTypes": CLAIM_TYPES,
        "translations": {
            code: {"edition": ed[0], "translator": ed[1], "language": ed[2], **({"publisher": PUBLISHERS[code]} if code in PUBLISHERS else {})}
            for code, ed in TRANSLATIONS.items()
        },
        "intro": verse(*expand_ref(INTRO_VERSE)[0]),
        "facts": out_facts,
    }
    os.makedirs(os.path.dirname(OUT), exist_ok=True)
    with open(OUT, "w", encoding="utf-8") as fh:
        json.dump(doc, fh, ensure_ascii=False, separators=(",", ":"))

    n_audio = sum(1 for f in out_facts for m in f["media"] if m["type"] == "audio")
    n_proof = sum(len(f["proof"]) for f in out_facts)
    n_video = sum(1 for f in out_facts for m in f["media"] if m["type"] == "video")
    n_img = sum(1 for f in out_facts for m in f["media"] if m["type"] == "image")
    print(f"Wrote {OUT}: {len(out_facts)} facts, {len(verse_cache)} verses, {n_proof} proof steps, "
          f"{n_img} images, {n_video} videos, {n_audio} audio, "
          f"{os.path.getsize(OUT) // 1024} KB")
    for w in warnings:
        print("  WARN ", w)
    for e in errors:
        print("  ERROR", e)
    sys.exit(1 if errors else 0)


if __name__ == "__main__":
    main()
