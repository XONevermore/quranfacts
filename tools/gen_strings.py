#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Writes the Android string resources (app/src/main/res/values[-xx]/strings.xml) from tools/ui_strings/<lang>.json.
English is the source of truth for keys and for the %1$s-style placeholders, which every translation must keep.

Usage: python3 tools/gen_strings.py
"""
import json
import os
import re
import sys

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SRC = os.path.join(ROOT, "tools", "ui_strings")
RES = os.path.join(ROOT, "app", "src", "main", "res")

# Android's resource qualifier per app language (Indonesian is still "in" to the platform).
FOLDER = {"en": "values", "uz": "values-uz", "ru": "values-ru", "tr": "values-tr", "fr": "values-fr",
          "id": "values-in", "ur": "values-ur", "bn": "values-bn", "de": "values-de"}
# CLDR plural categories each language needs (anything else would be flagged by lint as unused).
QUANTITIES = {"en": ["one", "other"], "uz": ["one", "other"], "ru": ["one", "few", "many", "other"],
              "tr": ["one", "other"], "fr": ["one", "many", "other"], "id": ["other"],
              "ur": ["one", "other"], "bn": ["one", "other"], "de": ["one", "other"]}

# Not translated: the brand name and a debug-only message.
FIXED = [
    ("msg_demo_plans", "Demo plans can't be bought. Create the subscription in the Play Console first."),
]

PLACEHOLDER = re.compile(r"%(\d+)\$[sd]|%%")


def escape(s):
    s = s.replace("\\", "\\\\").replace("'", "\\'").replace('"', '\\"')
    s = s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "\\n")
    if s[:1] in ("@", "?"):
        s = "\\" + s
    return s


def placeholders(s):
    return sorted(m.group(0) for m in PLACEHOLDER.finditer(s))


def load(lang):
    with open(os.path.join(SRC, f"{lang}.json"), encoding="utf-8") as fh:
        return json.load(fh)


def main():
    en = load("en")
    errors = []
    for lang, folder in FOLDER.items():
        path = os.path.join(SRC, f"{lang}.json")
        if not os.path.exists(path):
            print(f"skip {lang}: no {os.path.relpath(path, ROOT)}")
            continue
        data = load(lang)
        for k, v in en["strings"].items():
            t = data["strings"].get(k)
            if not t or not t.strip():
                errors.append(f"{lang}: missing string {k}")
            elif placeholders(t) != placeholders(v):
                errors.append(f"{lang}: placeholders differ in {k}: {placeholders(v)} vs {placeholders(t)}")
        for k in data["strings"]:
            if k not in en["strings"]:
                errors.append(f"{lang}: unknown string {k}")
        for k, forms in en["plurals"].items():
            got = data["plurals"].get(k, {})
            for q in QUANTITIES[lang]:
                if q not in got or not got[q].strip():
                    errors.append(f"{lang}: plural {k} lacks '{q}'")
            for q, t in got.items():
                if q not in QUANTITIES[lang]:
                    errors.append(f"{lang}: plural {k} has unused '{q}'")
                if placeholders(t) != placeholders(forms["other"]):
                    errors.append(f"{lang}: plural {k}/{q} placeholders differ: {placeholders(forms['other'])} vs {placeholders(t)}")
        lines = ['<?xml version="1.0" encoding="utf-8"?>', "<resources>"]
        if lang == "en":
            for k, v in FIXED:
                lines.append(f'    <string name="{k}" translatable="false">{escape(v)}</string>')
        for k in en["strings"]:
            if k in data["strings"]:
                lines.append(f'    <string name="{k}">{escape(data["strings"][k])}</string>')
        for k in en["plurals"]:
            if k in data["plurals"]:
                lines.append(f'    <plurals name="{k}">')
                for q in QUANTITIES[lang]:
                    if q in data["plurals"][k]:
                        lines.append(f'        <item quantity="{q}">{escape(data["plurals"][k][q])}</item>')
                lines.append("    </plurals>")
        lines.append("</resources>")
        out = os.path.join(RES, folder, "strings.xml")
        os.makedirs(os.path.dirname(out), exist_ok=True)
        with open(out, "w", encoding="utf-8") as fh:
            fh.write("\n".join(lines) + "\n")
        print(f"{lang}: wrote {os.path.relpath(out, ROOT)}")
    for e in errors:
        print("ERROR", e)
    sys.exit(1 if errors else 0)


if __name__ == "__main__":
    main()
