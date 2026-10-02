#!/usr/bin/env python3
"""Key-word meanings are never our own translation: each word's meaning is the phrase of the trusted verse translation
(the one named in More) that renders it, copied verbatim from a verse the fact cites.

  tools/i18n/word_phrases/NN.json   {"<fact>.<n>": {"en": ..., "uz": ..., ... "de": ...}}   NN = fact chunk (as tools/i18n/en/NN.json)
usage:
  i18n_words.py dump <chunk>     print the work sheet (words + the cited verses in every language)
  i18n_words.py verify           every phrase must occur verbatim in a cited verse of its language
  i18n_words.py apply            write phrases into facts.json (en) and tools/i18n/<lang>/NN.json (word keys)
"""
import json, os, re, sys, unicodedata
N = lambda t: unicodedata.normalize("NFC", t)
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
FACTS = os.path.join(ROOT, "app/src/main/assets/facts.json")
PH = os.path.join(ROOT, "tools/i18n/word_phrases")
LANGS = ["en", "uz", "ru", "tr", "fr", "id", "ur", "bn", "de"]
PER_CHUNK = 7
doc = json.load(open(FACTS, encoding="utf-8"))
facts = doc["facts"]
chunk_of = {f["id"]: 1 + i // PER_CHUNK for i, f in enumerate(facts)}

def load():
    t = {}
    if os.path.isdir(PH):
        for n in sorted(os.listdir(PH)):
            if n.endswith(".json"): t.update(json.load(open(os.path.join(PH, n), encoding="utf-8")))
    return t

def norm(s): return re.sub(r"\s+", " ", N(s)).strip().lower()

def verify(t):
    bad = 0
    for f in facts:
        for n, w in enumerate(f["words"]):
            k = f"{f['id']}.{n}"
            row = t.get(k)
            if not row: print("missing", k); bad += 1; continue
            for l in LANGS:
                p = row.get(l, "")
                if not p.strip(): print("blank", k, l); bad += 1; continue
                if not any(norm(p) in norm(v["tr"][l]) for v in f["verses"]):
                    print("not verbatim", k, l, p); bad += 1
    return bad

cmd = sys.argv[1] if len(sys.argv) > 1 else ""
if cmd == "dump":
    c = int(sys.argv[2])
    for f in facts:
        if chunk_of[f["id"]] != c: continue
        print(f"\n##### {f['id']}")
        for v in f["verses"]:
            print(f"-- verse {v['surah']}:{v['ayah']}  {v['ar']}")
            for l in LANGS: print(f"   {l}: {v['tr'][l]}")
        for n, w in enumerate(f["words"]):
            print(f"  WORD {f['id']}.{n}: {w['ar']}  ({w['tr']}, root {w['root']}) — was: {w['en']}")
elif cmd == "verify":
    b = verify(load()); print(f"{b} problems")
elif cmd == "apply":
    t = load()
    if verify(t): sys.exit("fix problems first")
    for f in facts:
        for n, w in enumerate(f["words"]): w["en"] = t[f"{f['id']}.{n}"]["en"]
    open(FACTS, "w", encoding="utf-8").write(json.dumps(doc, ensure_ascii=False, separators=(",", ":")))
    for l in LANGS[1:]:
        for c in sorted({chunk_of[f["id"]] for f in facts}):
            p = os.path.join(ROOT, f"tools/i18n/{l}/{c:02d}.json")
            d = json.load(open(p, encoding="utf-8"))
            for f in facts:
                if chunk_of[f["id"]] == c:
                    for n in range(len(f["words"])): d[f"{f['id']}.word.{n}"] = t[f"{f['id']}.{n}"][l]
            json.dump(d, open(p, "w", encoding="utf-8"), ensure_ascii=False, indent=1); open(p, "a").write("\n")
    print("applied")
