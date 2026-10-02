#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Composes the Google Play phone screenshots (1080 x 1920, 9:16) from real app captures in store/raw/.
Each one is a headline, then the app screen in a rounded frame bleeding off the bottom edge.
Raw captures are 1080 x 2220 from an emulator (more than 2:1, which Play rejects), hence the framing.

Usage:  python3 tools/make_store_shots.py
"""
import os

import numpy as np
from PIL import Image, ImageDraw, ImageFilter, ImageFont

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
RAW = os.path.join(ROOT, "store", "raw")
OUT = os.path.join(ROOT, "store")
SERIF = "/System/Library/Fonts/Supplemental/Georgia Bold.ttf"
SANS = "/System/Library/Fonts/Supplemental/Futura.ttc"
GOLD = (233, 199, 126)

# (raw file, headline line 1, headline line 2)  -- order = order on the store page
SHOTS = [
    ("home", "Where the Quran", "meets discovery"),
    ("fact", "Read the verse,", "see the science"),
    ("proof", "See how each proof", "was found"),
    ("fit", "Honest labels:", "fits and cautions"),
    ("timeline", "Discoveries found", "centuries later"),
    ("explore", "56 facts to explore", "by theme and strength"),
]
W, H = 1080, 1920


def background():
    top, bottom = np.array((27, 42, 87), np.float32), np.array((8, 12, 25), np.float32)
    t = np.linspace(0, 1, H, dtype=np.float32)[:, None, None]
    img = Image.fromarray(np.broadcast_to(top + (bottom - top) * t, (H, W, 3)).astype(np.uint8)).convert("RGBA")
    # A soft gold glow behind the phone, echoing the icon's horizon.
    yy, xx = np.mgrid[0:H, 0:W].astype(np.float32)
    d = np.sqrt((xx - W / 2) ** 2 + (yy - 1500) ** 2) / 900
    glow = Image.new("RGBA", (W, H), GOLD + (0,))
    glow.putalpha(Image.fromarray((np.clip(1 - d, 0, 1) ** 1.8 * 0.30 * 255).astype(np.uint8)))
    return Image.alpha_composite(img, glow)


def compose(raw_name, line1, line2, index):
    img = background()
    d = ImageDraw.Draw(img)
    f1 = ImageFont.truetype(SERIF, 84)
    for i, line in enumerate((line1, line2)):
        w = d.textlength(line, font=f1)
        d.text(((W - w) / 2, 120 + i * 104), line, font=f1, fill=(255, 255, 255) if i == 0 else GOLD)

    shot = Image.open(os.path.join(RAW, raw_name + ".png")).convert("RGBA")
    fw = 900
    fh = int(shot.height * fw / shot.width)
    shot = shot.resize((fw, fh), Image.LANCZOS)
    mask = Image.new("L", (fw * 2, fh * 2), 0)
    ImageDraw.Draw(mask).rounded_rectangle([0, 0, fw * 2 - 1, fh * 2 - 1], radius=110, fill=255)
    shot.putalpha(mask.resize((fw, fh), Image.LANCZOS))
    x, y = (W - fw) // 2, 400
    shadow = Image.new("RGBA", (W, H), (0, 0, 0, 0))
    ImageDraw.Draw(shadow).rounded_rectangle([x, y + 18, x + fw, y + fh], radius=110, fill=(0, 0, 0, 150))
    img = Image.alpha_composite(img, shadow.filter(ImageFilter.GaussianBlur(28)))
    border = Image.new("RGBA", (W, H), (0, 0, 0, 0))
    ImageDraw.Draw(border).rounded_rectangle([x - 5, y - 5, x + fw + 5, y + fh + 5], radius=115, fill=(255, 255, 255, 46))
    img = Image.alpha_composite(img, border)
    img.alpha_composite(shot, (x, y))
    path = os.path.join(OUT, f"phone-{index}.png")
    img.convert("RGB").save(path)
    return path


if __name__ == "__main__":
    for i, (raw, a, b) in enumerate(SHOTS, 1):
        print("wrote", compose(raw, a, b, i))
