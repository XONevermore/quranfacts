# -*- coding: utf-8 -*-
"""
Facts added in the fourth batch (October 2026). Same inclusion rule as facts_more.py: a fact is here only if
  (1) it is written in a verse of the Quran, and
  (2) people have actually discovered or measured the thing it points to.
Every number below was checked against the cited paper, press release or reference work when it was written.
Same editorial rules as facts_source.py apply.
"""

FOURTH_FACTS = [
    # ================================ COSMOS ================================
    dict(
        id="six-days",
        title="Six Days and 13.8 Billion Years",
        hook="Science says 13.8 billion years. The Quran says “six days”. Here is the popular calculation that joins them, step by step, and where to be careful.",
        category="cosmos",
        claimType="interpretive",
        widget="sixdays",
        verses=["25:59", "41:9", "41:10", "41:11", "41:12", "22:47"],
        words=[
            ("أَيَّامٍۢ", "ayyām", "days", "ي و م"),
            ("يَوْمَيْنِ", "yawmayn", "two days", "ي و م"),
            ("يَوْمًا", "yawman", "a day", "ي و م"),
            ("كَأَلْفِ", "ka-alfi", "like a thousand", "أ ل ف"),
        ],
        science=[
            "The Quran says the heavens and the earth were created “in six days” (25:59, and six other verses). The Arabic word is yawm, a day, but the Quran also uses it for far longer spans: 22:47 says a day with your Lord is “like a thousand years of those which you count”, and 70:4 speaks of a day of fifty thousand years.",
            "Science dates the universe to 13.8 billion years: ESA's Planck satellite measured 13.787 ± 0.020 billion years from the afterglow of the Big Bang. The Earth is younger, about 4.54 billion years old, from the radioactive dating of meteorites and the oldest rocks.",
            "Time is not the same for everyone. Einstein's relativity says a clock runs slower when it moves fast or sits deeper in gravity, and this is measured every day: the clocks on GPS satellites would drift by about 38 microseconds a day if engineers did not correct them. Near a black hole the effect becomes enormous.",
            "A popular reading takes each “day” as one sixth of cosmic history: 13.8 billion ÷ 6 ≈ 2.3 billion years. Verse 41:9 says the earth was created “in two days”, and two such days are 4.6 billion years, close to the Earth's measured age. Try the arithmetic in the lab below.",
        ],
        stats=[
            ("13.8 bn yrs", "Age of the universe (Planck 2018)"),
            ("÷ 6 ≈ 2.3 bn", "Years per “day” if all six are equal"),
            ("4.54 bn yrs", "Age of the Earth (meteorite dating)"),
            ("38 µs/day", "Relativity correction on GPS clocks"),
        ],
        discovery=("1905 – 2018", "Einstein · Patterson · Hafele & Keating · Planck"),
        media=[
            ("File:The Earth seen from Apollo 17.jpg", "The Earth from Apollo 17. Our “day” is one turn of this planet (NASA)."),
            ("File:DSCOVR EPIC Earth Rotation.webm", "22 hours of the Earth turning, filmed by NASA's DSCOVR satellite from about 1.5 million km away."),
            ("File:Black hole - Messier 87 crop max res.jpg", "The black hole in galaxy M87 (Event Horizon Telescope, 2019). Close to it, clocks slow down enormously."),
            ("File:GPS Satellite NASA art-iif.jpg", "A GPS satellite. Its atomic clocks are corrected for relativity every day (NASA)."),
        ],
        sources=[
            ("Age of the universe", "https://en.wikipedia.org/wiki/Age_of_the_universe"),
            ("Age of the Earth", "https://en.wikipedia.org/wiki/Age_of_Earth"),
            ("Time dilation", "https://en.wikipedia.org/wiki/Time_dilation"),
            ("Hafele–Keating experiment", "https://en.wikipedia.org/wiki/Hafele%E2%80%93Keating_experiment"),
            ("Error analysis for the Global Positioning System: relativity", "https://en.wikipedia.org/wiki/Error_analysis_for_the_Global_Positioning_System"),
        ],
    ),
]
