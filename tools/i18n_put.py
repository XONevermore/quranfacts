#!/usr/bin/env python3
"""Stores translations from plain text on stdin. Sections start with a line `## <lang> <id>` where <id> is a fact id
(or `00` for categories/claims); the lines after it are the translations, one per line, in the key order of that
fact in tools/i18n/en/*.json. No quoting or escaping needed. Merges into tools/i18n/<lang>/<chunk>.json.

  python3 tools/i18n_put.py <<'EOF'
  ## uz big-bang
  ...one line per key...
  EOF
"""
import json, os, sys
root = os.path.join(os.path.dirname(os.path.abspath(__file__)), "i18n")
en = {}
for name in sorted(os.listdir(os.path.join(root, "en"))):
    en[name] = json.load(open(os.path.join(root, "en", name), encoding="utf-8"))

def keys_for(fid):
    for name, d in en.items():
        ks = [k for k in d if k.startswith(fid + ".")]
        if ks:
            return name, ks
    sys.exit(f"unknown fact id {fid}")

sections, cur = [], None
for line in sys.stdin.read().split("\n"):
    if line.startswith("## "):
        cur = [line[3:].split(), []]
        sections.append(cur)
    elif line.strip() and cur is not None:
        cur[1].append(line.strip())
bad = False
for (lang, fid), lines in sections:
    name, ks = keys_for(fid)
    if len(lines) != len(ks):
        print(f"MISMATCH {lang} {fid}: {len(lines)} lines vs {len(ks)} keys"); bad = True; continue
    path = os.path.join(root, lang, name)
    os.makedirs(os.path.dirname(path), exist_ok=True)
    d = json.load(open(path, encoding="utf-8")) if os.path.exists(path) else {}
    d.update(zip(ks, lines))
    order = [k for k in en[name] if k in d]
    json.dump({k: d[k] for k in order}, open(path, "w", encoding="utf-8"), ensure_ascii=False, indent=1)
    print("ok", lang, fid, len(ks))
sys.exit(1 if bad else 0)
