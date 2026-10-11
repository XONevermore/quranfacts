package com.example.quranfacts.ui.detail

/**
 * The arithmetic behind the popular "six days = 13.8 billion years" reading of 25:59 and 41:9,
 * kept free of UI so it can be unit-tested. The two choices the reading depends on (six equal days,
 * and where the earth's two days sit) are explicit, which is the whole point of the lab.
 */
object SixDaysMath {
    /** Age of the universe in billions of years, as usually quoted from Planck 2018 (13.787 ± 0.020). */
    const val UNIVERSE_BN = 13.8
    /** Age of the Earth in billions of years (meteorite and oldest-rock dating, 4.54 ± 0.05). */
    const val EARTH_BN = 4.54
    const val DAYS = 6
    const val EARTH_DAYS = 2

    data class Result(
        val perDayBn: Double,
        val earthSpanBn: Double,
        /** When the earth's two days begin and end, in billions of years before today. */
        val startAgoBn: Double,
        val endAgoBn: Double,
        /** How far the start of the earth's days is from the Earth's measured age, in percent. */
        val diffPercent: Double,
        /** Where the Earth's measured age falls on the six-day line, 0 = the beginning, 1 = today. */
        val earthOnLine: Double,
    )

    /** @param firstDay the earth's two days are days [firstDay] and [firstDay] + 1 (1-based). */
    fun compute(firstDay: Int): Result {
        val perDay = UNIVERSE_BN / DAYS
        val start = UNIVERSE_BN - (firstDay - 1) * perDay
        val end = start - EARTH_DAYS * perDay
        return Result(
            perDayBn = perDay,
            earthSpanBn = EARTH_DAYS * perDay,
            startAgoBn = start,
            endAgoBn = end,
            diffPercent = (start / EARTH_BN - 1) * 100,
            earthOnLine = 1 - EARTH_BN / UNIVERSE_BN,
        )
    }
}
