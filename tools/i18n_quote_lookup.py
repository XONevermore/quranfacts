#!/usr/bin/env python3
"""Groups Quran quotations by the verse they come from and prints that verse in every language's trusted translation,
so each quotation's matching phrase can be copied verbatim (tools/i18n/quran_quotes.json).
usage: i18n_quote_lookup.py <first verse group> <how many>"""
import json, os, re, sys, glob, difflib
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
facts = {f["id"]: f for f in json.load(open(os.path.join(ROOT, "app/src/main/assets/facts.json"), encoding="utf-8"))["facts"]}
quotes = json.load(open(os.path.join(ROOT, "tools/i18n/quran_quotes.json"), encoding="utf-8"))
keys = list(quotes)
en = {}
for f in sorted(glob.glob(os.path.join(ROOT, "tools/i18n/en/0[1-9].json"))):
    en.update(json.load(open(f, encoding="utf-8")))
groups = {}
for qi, q in enumerate(keys):
    fids = []
    for k, v in en.items():
        if f"“{q}”" in v and k.split(".")[0] not in fids:
            fids.append(k.split(".")[0])
    best = None
    for fid in fids:
        for v in facts[fid]["verses"]:
            r = difflib.SequenceMatcher(None, q.lower(), v["tr"]["en"].lower()).find_longest_match(0, len(q), 0, len(v["tr"]["en"])).size
            if not best or r > best[0]: best = (r, fid, v)
    g = (best[2]["surah"], best[2]["ayah"], best[1]) if best else ("?", "?", fids[0] if fids else "?")
    groups.setdefault(g, {"v": best[2] if best else None, "q": []})["q"].append((qi, q))
order = list(groups)
a, n = int(sys.argv[1]), int(sys.argv[2])
for g in order[a:a + n]:
    d = groups[g]
    print(f"\n=== group {order.index(g)}  verse {g[0]}:{g[1]}  (fact {g[2]})")
    for qi, q in d["q"]:
        print(f"   #{qi} “{q}”")
    if d["v"]:
        for l in ["uz", "ru", "tr", "fr", "id", "ur", "bn", "de"]:
            t = d["v"]["tr"][l]
            if l == "uz" and re.search(r"\) *\([А-ЯЁҚҒҲЎа-яёқғҳў]{3,}.{80,}", t): t = t[: t.rfind(" (")]
            print(f"   {l}: {t}")
print(f"\n({len(order)} groups)")
