#!/usr/bin/env python3
"""
Quran wording is never our own translation. A verse quoted inside fact text (the English “…” segments listed in
tools/i18n/quran_quotes.json) is replaced, in every language, by the matching words of that language's trusted
verse translation (tools/i18n/quran_quotes_trusted.json, each phrase verified verbatim against the verse text by
tools/i18n_quotes_put.py). Where the trusted translation words a verse differently, the phrase starts with `~`
and the text is a plain description without quotation marks.

  python3 tools/i18n_quotes.py fix [lang...]    put the trusted wording into the translations
  python3 tools/i18n_quotes.py check [lang...]  report translations that do not contain it
"""
import json, os, re, sys, unicodedata
ROOT = os.path.dirname(os.path.abspath(__file__))
SRC = os.path.join(ROOT, "i18n")
LANGS = ["uz", "ru", "tr", "fr", "id", "ur", "bn", "de"]
TRUSTED = json.load(open(os.path.join(SRC, "quran_quotes_trusted.json"), encoding="utf-8"))
EN_Q = re.compile(r"“([^”]+)”")
ANY_Q = re.compile(r"“([^”]*)”|«\s*([^»]*?)\s*»|„([^“”]*)[“”]")
N = lambda s: unicodedata.normalize("NFC", s)
STYLE = {"uz": "«{}»", "ru": "«{}»", "fr": "« {} »", "de": "„{}“"}


def style(lang, phrase):
    return phrase[1:] if phrase.startswith("~") else STYLE.get(lang, "“{}”").format(phrase)


def run(mode, langs):
    bad = fixed = 0
    for lang in langs:
        d = os.path.join(SRC, lang)
        for name in sorted(f for f in os.listdir(d) if f.endswith(".json")) if os.path.isdir(d) else []:
            en = json.load(open(os.path.join(SRC, "en", name), encoding="utf-8"))
            path = os.path.join(d, name)
            tr = json.load(open(path, encoding="utf-8"))
            changed = False
            for k, v in en.items():
                segs = EN_Q.findall(v)
                need = [s for s in segs if s in TRUSTED]
                if not need or k not in tr:
                    continue
                t = tr[k]
                ok = all(TRUSTED[s][lang].startswith("~") or N(TRUSTED[s][lang].split("… ")[0]) in N(t) for s in need)
                if ok:
                    continue
                ms = list(ANY_Q.finditer(t))
                if mode == "fix" and len(ms) == len(segs):
                    out, last = [], 0
                    for i, m in enumerate(ms):
                        out.append(t[last:m.start()])
                        out.append(style(lang, TRUSTED[segs[i]][lang]) if segs[i] in TRUSTED else m.group(0))
                        last = m.end()
                    out.append(t[last:])
                    tr[k] = "".join(out)
                    changed = True
                    fixed += 1
                else:
                    bad += 1
                    print(f"  {lang}/{name} {k}: expected quotes {need} ({len(ms)} marked, {len(segs)} in English)")
            if changed:
                json.dump(tr, open(path, "w", encoding="utf-8"), ensure_ascii=False, indent=1)
    print(f"{mode}: fixed {fixed}, remaining {bad}")
    return bad


if __name__ == "__main__":
    mode = sys.argv[1] if len(sys.argv) > 1 else ""
    if mode not in ("fix", "check"):
        print(__doc__); sys.exit(2)
    langs = sys.argv[2:] or LANGS
    if mode == "fix":
        run("fix", langs)
    sys.exit(1 if run("check", langs) else 0)
