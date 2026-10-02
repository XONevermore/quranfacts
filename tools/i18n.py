#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Translation layer for the fact text. facts.json stays English; every other language is an overlay
of flat `key -> translated string` pairs, applied by the app on top of it (data/I18n.kt).

  tools/i18n/en/NN.json   <- extracted from facts.json (the source of truth for the keys)
  tools/i18n/<lang>/NN.json  <- translations, same keys, same chunking
  app/src/main/assets/i18n/<lang>.json  <- built output the app reads

Usage:
  python3 tools/i18n.py extract          # rewrite tools/i18n/en/*.json from facts.json
  python3 tools/i18n.py check [lang...]  # compare translations with English (keys, digits, blanks)
  python3 tools/i18n.py build [lang...]  # write assets/i18n/<lang>.json (strings not translated yet stay English in the app)

Keys:  cat.<id>.name|tagline   claim.<id>.label|short|description
       <fact>.title|hook|sciH|proofH|science.N|stats.N.value|stats.N.label|disc.when|disc.who
       <fact>.proof.N.title|text|cap   <fact>.fit.N   <fact>.gap.N   <fact>.media.N.cap   <fact>.word.N
"""
import json
import os
import re
import sys

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
FACTS = os.path.join(ROOT, "app", "src", "main", "assets", "facts.json")
SRC = os.path.join(ROOT, "tools", "i18n")
OUT = os.path.join(ROOT, "app", "src", "main", "assets", "i18n")
PER_CHUNK = 7
LANGS = ["uz", "ru", "tr", "fr", "id", "ur", "bn", "de"]


def extract_pairs(doc):
    """Chunks of ordered (key, English) pairs. Chunk 0 is categories + claim types, then PER_CHUNK facts each."""
    head = {}
    for c in doc["categories"]:
        head[f"cat.{c['id']}.name"] = c["name"]
        head[f"cat.{c['id']}.tagline"] = c["tagline"]
    for c in doc["claimTypes"]:
        for k in ("label", "short", "description"):
            head[f"claim.{c['id']}.{k}"] = c[k]
    chunks = [head]
    facts = doc["facts"]
    for i in range(0, len(facts), PER_CHUNK):
        chunk = {}
        for f in facts[i:i + PER_CHUNK]:
            p = f["id"]
            chunk[f"{p}.title"] = f["title"]
            chunk[f"{p}.hook"] = f["hook"]
            chunk[f"{p}.sciH"] = f["scienceHeading"]
            chunk[f"{p}.proofH"] = f["proofHeading"]
            for n, w in enumerate(f["words"]):
                chunk[f"{p}.word.{n}"] = w["en"]
            for n, t in enumerate(f["science"]):
                chunk[f"{p}.science.{n}"] = t
            for n, s in enumerate(f["stats"]):
                chunk[f"{p}.stats.{n}.value"] = s["value"]
                chunk[f"{p}.stats.{n}.label"] = s["label"]
            if f.get("discovery"):
                chunk[f"{p}.disc.when"] = f["discovery"]["when"]
                chunk[f"{p}.disc.who"] = f["discovery"]["who"]
            for n, s in enumerate(f["proof"]):
                chunk[f"{p}.proof.{n}.title"] = s["title"]
                chunk[f"{p}.proof.{n}.text"] = s["text"]
                if s.get("media"):
                    chunk[f"{p}.proof.{n}.cap"] = s["media"]["caption"]
            for n, t in enumerate(f["fit"]["fits"]):
                chunk[f"{p}.fit.{n}"] = t
            for n, t in enumerate(f["fit"]["gaps"]):
                chunk[f"{p}.gap.{n}"] = t
            for n, m in enumerate(f["media"]):
                chunk[f"{p}.media.{n}.cap"] = m["caption"]
        chunks.append(chunk)
    return chunks


def write_json(path, obj):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as fh:
        json.dump(obj, fh, ensure_ascii=False, indent=1)
        fh.write("\n")


def load_chunks(lang):
    d = os.path.join(SRC, lang)
    out = {}
    if os.path.isdir(d):
        for name in sorted(os.listdir(d)):
            if name.endswith(".json"):
                with open(os.path.join(d, name), encoding="utf-8") as fh:
                    out[name] = json.load(fh)
    return out


def digits(s):
    """The numbers in a string, ignoring how they are grouped (1,000 / 1 000 / 1.000) or their decimal mark (13.8 / 13,8)."""
    s = re.sub(r"(?<=\d)[ ,.\u00a0\u202f](?=\d)", "", s)
    return sorted(re.findall(r"\d+", s))


def check(lang):
    """Returns (problems, merged translation dict). Digits must survive translation unchanged."""
    en = load_chunks("en")
    tr = load_chunks(lang)
    problems, merged = [], {}
    for name, src in en.items():
        got = tr.get(name)
        if got is None:
            problems.append(f"{lang}/{name}: missing file")
            continue
        for k, v in src.items():
            if k not in got:
                problems.append(f"{lang}/{name}: missing {k}")
                continue
            t = got[k]
            if not isinstance(t, str) or not t.strip():
                problems.append(f"{lang}/{name}: blank {k}")
                continue
            # "20th century" is written XX век / XXe siècle / etc. in some languages, so ordinals are not compared.
            if not re.search(r"\d+(?:st|nd|rd|th) century", v) and digits(v) != digits(t):
                problems.append(f"{lang}/{name}: digits differ in {k}: {digits(v)} vs {digits(t)}")
            merged[k] = t
        for k in got:
            if k not in src:
                problems.append(f"{lang}/{name}: unknown key {k}")
    return problems, merged


def main():
    cmd = sys.argv[1] if len(sys.argv) > 1 else ""
    langs = sys.argv[2:] or LANGS
    if cmd == "extract":
        with open(FACTS, encoding="utf-8") as fh:
            chunks = extract_pairs(json.load(fh))
        for i, ch in enumerate(chunks):
            write_json(os.path.join(SRC, "en", f"{i:02d}.json"), ch)
        print(f"{len(chunks)} chunks, {sum(len(c) for c in chunks)} strings, "
              f"{sum(len(v) for c in chunks for v in c.values())} characters")
    elif cmd in ("check", "build"):
        bad = False
        for lang in langs:
            problems, merged = check(lang)
            for p in problems[:40]:
                print("  " + p)
            if len(problems) > 40:
                print(f"  ... {len(problems) - 40} more")
            print(f"{lang}: {len(merged)} strings, {len(problems)} problems")
            real = [x for x in problems if "missing" not in x]
            bad |= bool(real) if cmd == "build" else bool(problems)
            if cmd == "build" and not real:
                os.makedirs(OUT, exist_ok=True)
                with open(os.path.join(OUT, f"{lang}.json"), "w", encoding="utf-8") as fh:
                    json.dump(merged, fh, ensure_ascii=False, separators=(",", ":"))
        sys.exit(1 if bad else 0)
    else:
        print(__doc__)
        sys.exit(2)


if __name__ == "__main__":
    main()
