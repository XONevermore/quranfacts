# -*- coding: utf-8 -*-
"""Evidence for the fourth batch (facts_fourth.py). Same format and rules as facts_evidence.py."""

FOURTH_EVIDENCE = {
    "six-days": dict(
        proofHeading="How the clocks and the ages were measured",
        proof=[
            ("1905 – 1915", "Einstein: time is not the same for everyone",
             "Special relativity (1905) says a moving clock runs slow. General relativity (1915) adds that a clock deeper in gravity runs slow too. There is no single, universal clock.",
             None, None),
            ("1956", "Patterson: the age of the Earth",
             "Clair Patterson measures lead isotopes in meteorites, including the Canyon Diablo iron, and finds that the Earth and the meteorites formed 4.55 ± 0.07 billion years ago.",
             "File:Canyon Diablo (meteorite).jpg",
             "A piece of the Canyon Diablo meteorite."),
            ("1959", "Pound and Rebka: gravity slows clocks",
             "At Harvard's Jefferson Laboratory, gamma rays sent up and down a 22.5 m tower shift in frequency by the tiny amount general relativity predicts.",
             "File:Jefferson Laboratory.jpg",
             "Jefferson Laboratory, Harvard, where the tower experiment was done."),
            ("1971", "Hafele and Keating fly atomic clocks",
             "Four caesium atomic clocks are flown around the world on airliners, east and then west. Compared with clocks on the ground, they lose about 59 nanoseconds going east and gain about 273 going west, as relativity predicts.",
             None, None),
            ("2010", "A clock lifted by 33 centimetres",
             "At NIST, two aluminium-ion clocks are so precise that raising one by 33 cm makes it measurably faster: time dilation at the height of a step.",
             None, None),
            ("2018", "Planck: 13.8 billion years",
             "The final results of ESA's Planck satellite give the age of the universe as 13.787 ± 0.020 billion years, from its map of the cosmic microwave background.",
             None, None),
        ],
        fit=dict(
            fits=[
                "The Quran itself uses “day” for spans of a thousand and of fifty thousand years (22:47, 70:4), so reading the six days as long ages has a basis in the text.",
                "Relativity shows that the length of a “day” depends on whose clock measures it, something no one could test before the 20th century.",
                "On the equal-days reading, two days come to 4.6 billion years, about 1.3% from the Earth's measured age of 4.54 billion.",
            ],
            gaps=[
                "The verses give no length for the days. Dividing by six assumes six equal days, and that step does not use time dilation at all.",
                "Two days × 2.3 billion is a length of time, not a date. It matches the Earth's age only if the earth's two days are the last two of the six.",
                "In 41:9–12 the earth is mentioned before the heaven, which was then still smoke. Early scholars already discussed the order of these days and how they add up to six; each reading moves the result.",
                "Time dilation is real and measured, but the Quran does not mention it, and there is no agreed clock by which to measure a divine “day”.",
                "This calculation is a modern reading that circulates online, not something the verses state.",
            ],
        ),
    ),
}
