# -*- coding: utf-8 -*-
"""
Recounts the number patterns in facts_patterns.py against the full Quran text, so a count in the app is never
typed from a video or from memory. Text: Al Quran Cloud's quran-simple-clean (modern spelling) and quran-uthmani
(the script of the printed mushaf), both the Hafs text with the Kufan verse numbering (6236 verses).

Used by build_content.py; `python3 tools/quran_count.py` runs the checks on their own.
"""
import json
import os
import re
import sys
import unicodedata
from datetime import date

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
CACHE_DIR = os.path.join(ROOT, "tools", ".cache")
EDITIONS = ("quran-simple-clean", "quran-uthmani")
BISMILLAH = ["بسم", "الله", "الرحمن", "الرحيم"]

# Abjad (mashriqi order): the value of each letter when Arabic letters are used as numerals.
ABJAD = dict(zip(
    "ابجدهوزحطيكلمنسعفصقرشتثخذضظغ",
    [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000],
))
ABJAD.update({"أ": 1, "إ": 1, "آ": 1, "ٱ": 1, "ى": 10, "ة": 5, "ؤ": 6, "ئ": 10})


def _plain(word):
    """A word without vowel signs, for comparing the Uthmani spelling with the plain one."""
    return "".join(c for c in word if unicodedata.category(c) != "Mn").replace("ٱ", "ا").replace("ـ", "")


def letters(text):
    """The letters of a text: base letters only, so vowel signs, the small alif, the small waw and pause marks don't count."""
    return [c for c in text if unicodedata.category(c) == "Lo"]


def words(text):
    """Words as written, separated by spaces; pause marks standing alone are not words."""
    return [w for w in text.split() if letters(w)]


class Quran:
    def __init__(self, fetch=None):
        self.ed = {e: self._load(e, fetch) for e in EDITIONS}
        simple = self.ed["quran-simple-clean"]
        self.number = {(s["n"], a[0]): a[1] for s in simple for a in s["ayahs"]}
        self.names = {s["n"]: s["en"] for s in simple}
        self.total = len(self.number)
        self.tokens = [w for s in simple for a in s["ayahs"] for w in words(self._body(s["n"], a[0], a[2]))]

    @staticmethod
    def _load(edition, fetch):
        path = os.path.join(CACHE_DIR, f"{edition}.json")
        if not os.path.exists(path):
            if fetch is None:
                raise RuntimeError(f"offline and {edition} not cached")
            data = fetch(f"https://api.alquran.cloud/v1/quran/{edition}")["data"]["surahs"]
            slim = [{"n": s["number"], "en": s["englishName"],
                     "ayahs": [(a["numberInSurah"], a["number"], a["text"].replace("﻿", "")) for a in s["ayahs"]]}
                    for s in data]
            os.makedirs(CACHE_DIR, exist_ok=True)
            with open(path, "w", encoding="utf-8") as fh:
                json.dump(slim, fh, ensure_ascii=False)
        with open(path, encoding="utf-8") as fh:
            return json.load(fh)

    @staticmethod
    def _body(surah, ayah, text):
        """The verse without the Bismillah that the API glues onto the first verse of every surah but 1 and 9."""
        toks = text.split()
        if ayah == 1 and surah not in (1, 9) and [_plain(t) for t in toks[:4]] == BISMILLAH:
            return " ".join(toks[4:])
        return text

    def verse(self, ref, edition="quran-simple-clean"):
        s, a = (int(x) for x in ref.split(":"))
        return self._body(s, a, self.ed[edition][s - 1]["ayahs"][a - 1][2])

    def word_index(self, ref, word):
        s, a = (int(x) for x in ref.split(":"))
        before = sum(len(words(self.verse(f"{s}:{n}"))) for n in range(1, a))
        return before + words(self.verse(ref)).index(word) + 1


def hijri_year(iso):
    """The year of the tabular (arithmetical) Islamic calendar on a Gregorian date."""
    jd = date.fromisoformat(iso).toordinal() + 1721424.5
    return int((30 * (jd - 1948439.5) + 10646) // 10631)


def run(q, verify, forms):
    """Value the check found, expected value. `forms` are every 'tokens' list of the same pattern."""
    kind, *args = verify
    if kind == "surah":
        return q.names[args[0]], args[1]
    if kind == "verse_number":
        return q.number[tuple(int(x) for x in args[0].split(":"))], args[1]
    if kind == "total_verses":
        return q.total, args[0]
    if kind in ("verses_from_to", "verses_between"):
        a, b = (q.number[tuple(int(x) for x in r.split(":"))] for r in args[:2])
        return (b - a + 1 if kind == "verses_from_to" else b - a - 1), args[2]
    if kind == "verses_after":
        return q.total - q.number[tuple(int(x) for x in args[0].split(":"))], args[1]
    if kind == "abjad":
        return sum(ABJAD[c] for c in args[0]), args[1]
    if kind == "letters":
        return len(letters(q.verse(args[0], "quran-" + ("simple-clean" if args[1] == "simple" else "uthmani")))), args[2]
    if kind == "simple_text":
        return q.verse(args[0]), args[1]
    if kind == "verse_has":
        return args[1] in q.verse(args[0]), True
    if kind in ("word_index", "word_index_bismillah"):
        extra = 4 if kind == "word_index_bismillah" else 0
        return q.word_index(args[0], args[1]) + extra, args[2]
    if kind in ("letters_in", "distinct_in"):
        if len(args) == 3:
            if args[1] not in words(q.verse(args[0])):
                return f"'{args[1]}' not in {args[0]}", args[2]
            args = args[1:]
        ls = letters(args[0])
        return (len(ls) if kind == "letters_in" else len(set(ls))), args[1]
    if kind == "tokens":
        wanted = set(args[0])
        return sum(1 for t in q.tokens if t in wanted), args[1]
    if kind == "contains":
        return sum(1 for t in q.tokens if args[0] in t), args[1]
    if kind == "all_forms":
        known = set(args[1]) | {f for fs in forms for f in fs}
        return sorted({t for t in q.tokens if args[0] in t} - known), []
    if kind == "hijri_year":
        return hijri_year(args[0]), args[1]
    raise ValueError(f"unknown verify kind: {kind}")


def check_patterns(q, patterns, verdicts):
    """Errors for every count that does not match, and every expected number missing from the text."""
    errors = []
    for fact_id, items in patterns.items():
        for i, p in enumerate(items):
            where = f"[{fact_id}] pattern {i}"
            if p.get("verdict") not in verdicts:
                errors.append(f"{where}: verdict must be one of {verdicts}")
            if not p.get("claim", "").strip() or not p.get("check", "").strip():
                errors.append(f"{where}: needs both a claim and a check")
            if not p.get("verify"):
                errors.append(f"{where}: has nothing to verify")
            text_numbers = set(re.findall(r"\d+", p.get("claim", "") + " " + p.get("check", "")))
            forms = [v[1] for v in p.get("verify", []) if v[0] == "tokens"]
            for v in p.get("verify", []):
                try:
                    got, want = run(q, v, forms)
                except Exception as e:  # a bad reference is an error, not a crash
                    errors.append(f"{where}: {v[0]} failed: {e}")
                    continue
                if got != want:
                    errors.append(f"{where}: {v[0]} {v[1:-1]} gives {got}, the text says {want}")
                if isinstance(want, int) and not isinstance(want, bool) and str(want) not in text_numbers:
                    errors.append(f"{where}: {v[0]} checks {want}, but that number is not in the text")
    return errors


if __name__ == "__main__":
    sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
    from facts_patterns import PATTERNS, VERDICTS

    def fetch(url):
        import urllib.request
        with urllib.request.urlopen(urllib.request.Request(url, headers={"User-Agent": "QuranFactsApp"}), timeout=60) as r:
            return json.loads(r.read())

    problems = check_patterns(Quran(fetch), PATTERNS, VERDICTS)
    for p in problems:
        print("ERROR", p)
    print(f"{sum(len(v) for v in PATTERNS.values())} patterns, {len(problems)} problems")
    sys.exit(1 if problems else 0)
