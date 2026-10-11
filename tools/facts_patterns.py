# -*- coding: utf-8 -*-
"""
Number patterns: the counts of verses, words and letters that short videos attach to some facts
("verse 75:4 is verse 5555", "1538 verses from soft iron to hard iron"). The app does not repeat them as
proof. It recounts each one and says plainly whether it checks out, depends on how you count, or does not hold.

Format: fact id -> list of dict(
    claim   = the pattern as it circulates, in neutral words,
    verdict = "holds" | "depends" | "fails",
    check   = what the recount found, and why it matters,
    verify  = machine checks that build_content.py runs against the full Quran text (Hafs, Kufan numbering),
)
Every whole number a check expects must also appear in the claim or check text, so the text cannot drift
from the count. No verse quotations here (translations would need the trusted-translation pass): paraphrase.

Verify kinds (see quran_count.py):
  ("surah", n, "English name")                ("verse_number", "s:a", n)       ("total_verses", n)
  ("verses_from_to", "s:a", "s:a", n)  both ends counted      ("verses_between", "s:a", "s:a", n)  ends left out
  ("verses_after", "s:a", n)  to the end of the Quran         ("abjad", "word", n)
  ("letters", "s:a", "simple"|"uthmani", n)   ("simple_text", "s:a", "text")   ("verse_has", "s:a", "words")
  ("word_index", "s:a", "word", n)  position in the surah, words as written, Bismillah not counted
  ("word_index_bismillah", "s:a", "word", n)  the same with the Bismillah counted
  ("letters_in", "word", n)   ("distinct_in", "word", n)    (the word must occur in the verse when "s:a" is given first)
  ("tokens", ["form", ...], n)  exact written forms        ("contains", "letters", n)  every word containing them
  ("all_forms", "letters", ["excluded", ...])  every word containing the letters is one of this pattern's "tokens" forms
  ("hijri_year", "YYYY-MM-DD", n)  tabular Islamic calendar
"""

SINGULAR_YAWM = ["يوم", "ويوم", "اليوم", "واليوم", "يوما", "ليوم", "فاليوم", "بيوم", "باليوم", "وباليوم"]
YAWMAIDHIN = ["يومئذ", "فيومئذ", "ويومئذ"]
YAWM_WITH_PRONOUN = ["يومكم", "يومهم"]
AYYAM = ["أيام", "أياما", "الأيام", "بأيام", "وأياما"]
SINGULAR_SHAHR = ["شهر", "الشهر", "شهرا", "بالشهر", "والشهر"]

PATTERNS = {
    "iron-from-space": [
        dict(
            claim="Al-Ḥadīd, “Iron”, is surah number 57, and iron-57 is one of iron's stable isotopes.",
            verdict="holds",
            check="Both are true. But iron has four stable isotopes, 54, 56, 57 and 58, and iron-56 makes up 91.8% of all iron while iron-57 is only 2.1%. What makes an atom iron is its atomic number, 26. And the surahs are numbered in the order of the written Quran, not the order in which they were revealed.",
            verify=[("surah", 57, "Al-Hadid")],
        ),
        dict(
            claim="In abjad numerals, where every Arabic letter stands for a number, ḥadīd (حديد) adds up to 26, iron's atomic number, and al-ḥadīd (الحديد) to 57, the surah's number.",
            verdict="holds",
            check="The sums are right: ḥ 8 + d 4 + y 10 + d 4 = 26, and the article al- adds 1 + 30. But one word gives either number depending on whether al- is counted, abjad is a way of writing numbers that the Quran itself does not use, and atomic numbers were only discovered in 1913.",
            verify=[("abjad", "حديد", 26), ("abjad", "الحديد", 57)],
        ),
        dict(
            claim="Verse 17:50 names iron as something hard, and 34:10 tells how iron was made soft for David. From one verse to the other there are 1538 verses, and pure iron melts at 1538 °C.",
            verdict="depends",
            check="1538 comes out only if both end verses are counted; the verses between them number 1536. It uses the Kufan verse numbering, and other early counts number some verses differently. It also needs the Celsius scale, invented in 1742: in kelvin iron melts at 1811 K, in Fahrenheit at 2800 °F. And iron is named in other verses too (18:96, 22:21, 57:25), which give other distances.",
            verify=[
                ("verses_from_to", "17:50", "34:10", 1538),
                ("verses_between", "17:50", "34:10", 1536),
                ("verse_has", "17:50", "حديدا"),
                ("verse_has", "34:10", "وألنا له الحديد"),
                ("verse_has", "18:96", "الحديد"),
                ("verse_has", "22:21", "حديد"),
                ("verse_has", "57:25", "الحديد"),
            ],
        ),
    ],
    "fingerprints": [
        dict(
            claim="Verse 75:4, the fingertips verse, is verse number 5555 of the Quran: four fives, like two hands and two feet with five digits each.",
            verdict="holds",
            check="Counting every verse from the first, 75:4 is number 5555 of 6236 in the Kufan numbering used in today's printed Qurans. Other early counts number some verses differently, so in those it lands elsewhere.",
            verify=[("verse_number", "75:4", 5555), ("total_verses", 6236)],
        ),
        dict(
            claim="The word for fingertips in this verse has 5 letters, like the five fingers, and is built from 4 different letters, like the four limbs.",
            verdict="depends",
            check="The verse says banānahū (بنانه), meaning his fingertips: 5 letters, 4 of them different. But the fifth letter is the attached pronoun, his. The word for fingertips itself, banān (بنان), has 4 letters, 3 of them different.",
            verify=[
                ("letters_in", "75:4", "بنانه", 5),
                ("distinct_in", "75:4", "بنانه", 4),
                ("letters_in", "بنان", 4),
                ("distinct_in", "بنان", 3),
            ],
        ),
        dict(
            claim="Banānahū is the 20th word of the surah, and 20 is four limbs times five digits.",
            verdict="fails",
            check="Counting the words as they are written, banānahū is the 19th word of the surah, or the 23rd if the opening Bismillah is counted. The list that reaches 20 splits wa-lā (and not) into two words, while it keeps other joined words, like bi-yawmi (by the day), whole.",
            verify=[("word_index", "75:4", "بنانه", 19), ("word_index_bismillah", "75:4", "بنانه", 23)],
        ),
        dict(
            claim="Verse 75:4 has 23 letters, and a person has 23 pairs of chromosomes, 23 from each parent.",
            verdict="depends",
            check="In modern spelling (بلى قادرين على أن نسوي بنانه) the verse has 23 letters. In the Uthmani script of the printed mushaf, qādirīn is written without its alif (قٰدرين), which makes 22. The human count of 46 chromosomes was settled in 1956 by Joe Hin Tjio and Albert Levan.",
            verify=[
                ("simple_text", "75:4", "بلى قادرين على أن نسوي بنانه"),
                ("letters", "75:4", "simple", 23),
                ("letters", "75:4", "uthmani", 22),
            ],
        ),
    ],
    "sun-moon-light": [
        dict(
            claim="The word yawm (day) appears 365 times in the Quran, once for every day of the year.",
            verdict="depends",
            check="365 is the count of yawm in the singular with its prefixes (al-yawm, wa-yawm, bi-yawm, yawman and so on). Other forms add more: yawma'idhin (on that day) 70 times, forms with a pronoun such as yawmuhum (their day) 10, the dual 3 and the plural ayyām 27. The solar year is 365.24 days long.",
            verify=[
                ("tokens", SINGULAR_YAWM, 365),
                ("tokens", YAWMAIDHIN, 70),
                ("tokens", YAWM_WITH_PRONOUN, 10),
                ("tokens", ["يومين"], 3),
                ("tokens", AYYAM, 27),
                ("all_forms", "يوم", ["القيوم"]),
            ],
        ),
        dict(
            claim="The word shahr (month) appears 12 times, once for every month of the year.",
            verdict="holds",
            check="12 is the count of the singular (shahr, al-shahr, shahran and so on); the dual and the plural add 8 more. Verse 9:36 also states it in words: the number of months is twelve.",
            verify=[
                ("tokens", SINGULAR_SHAHR, 12),
                ("tokens", ["شهرين", "أشهر", "الأشهر"], 8),
                ("all_forms", "شهر", []),
                ("verse_has", "9:36", "اثنا عشر شهرا"),
            ],
        ),
    ],
    "moon-phases": [
        dict(
            claim="The word qamar (moon) appears 27 times, and the Moon goes around the Earth in 27.3 days.",
            verdict="holds",
            check="The count is right: 27, in all its forms. But the stages in 36:39 follow the month from new moon to new moon, 29.5 days, which is also the month of the Islamic calendar; 27.3 days is one turn measured against the stars. And most such counts match nothing: the Sun, shams, appears 33 times.",
            verify=[("contains", "قمر", 27), ("contains", "شمس", 33)],
        ),
    ],
    "moon-split": [
        dict(
            claim="From the verse on the splitting of the Moon (54:1) to the end of the Quran there are 1389 verses, and Apollo 11 landed on the Moon in 1389 AH (1969).",
            verdict="depends",
            check="Counting 54:1 itself gives 1390; 1389 is the number of verses after it. Apollo 11 landed on 20 July 1969, in the Islamic year 1389. The verse speaks of the Moon splitting, not of anyone reaching it.",
            verify=[
                ("verses_after", "54:1", 1389),
                ("verses_from_to", "54:1", "114:6", 1390),
                ("hijri_year", "1969-07-20", 1389),
            ],
        ),
    ],
}

VERDICTS = ("holds", "depends", "fails")
