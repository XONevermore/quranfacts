#!/usr/bin/env python3
"""Records, for each Quran quotation (index in quran_quotes.json), the matching wording of the trusted translation of
every language. stdin: `#<index>` then 8 lines, in the order uz ru tr fr id ur bn de. A phrase must be copied
verbatim from that language's translation of the verse (checked); a phrase starting with `~` is not a quotation but a
plain description (no quotation marks) for places where the trusted translation words the verse differently."""
import json, os, re, sys, unicodedata
N = lambda t: unicodedata.normalize("NFC", t)
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
LANGS = ["uz", "ru", "tr", "fr", "id", "ur", "bn", "de"]
src = os.path.join(ROOT, "tools/i18n/quran_quotes.json")
dst = os.path.join(ROOT, "tools/i18n/quran_quotes_trusted.json")
quotes = json.load(open(src, encoding="utf-8"))
facts = {f["id"]: f for f in json.load(open(os.path.join(ROOT, "app/src/main/assets/facts.json"), encoding="utf-8"))["facts"]}
import glob
en = {}
for f in sorted(glob.glob(os.path.join(ROOT, "tools/i18n/en/0[1-9].json"))):
    en.update(json.load(open(f, encoding="utf-8")))
table = json.load(open(dst, encoding="utf-8")) if os.path.exists(dst) else {}

def verses_for(q):
    fids = []
    for k, v in en.items():
        if f"“{q}”" in v and k.split(".")[0] not in fids:
            fids.append(k.split(".")[0])
    return [v for fid in fids for v in facts[fid]["verses"]]

cur, rows, bad = None, [], 0
def flush():
    global bad
    if cur is None: return
    q = quotes[cur]
    if len(rows) != 8:
        print(f"#{cur}: {len(rows)} lines instead of 8"); bad += 1; return
    out = {}
    for lang, phrase in zip(LANGS, rows):
        p = phrase[1:] if phrase.startswith("~") else phrase
        if not phrase.startswith("~") and not all(any(N(part) in N(v["tr"][lang]) for v in verses_for(q)) for part in p.split("… ")):
            print(f"#{cur} {lang}: not found verbatim: {p}"); bad += 1
        out[lang] = phrase
    table[q] = out
for line in sys.stdin.read().split("\n"):
    if line.startswith("#"):
        flush(); cur = int(line[1:].split()[0]); rows = []
    elif line.strip():
        rows.append(line.strip())
flush()
json.dump(table, open(dst, "w", encoding="utf-8"), ensure_ascii=False, indent=1)
print(f"{len(table)} quotations recorded, {bad} problems")
