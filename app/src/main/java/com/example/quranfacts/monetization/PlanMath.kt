package com.example.quranfacts.monetization

/**
 * Small, pure helpers for showing Google Play subscription plans. Google Play describes billing periods
 * as ISO 8601 durations ("P1M", "P1Y", "P1W", "P7D"); prices come as micros of the local currency.
 */
object PlanMath {
    private val PERIOD = Regex("^P(?:(\\d+)Y)?(?:(\\d+)M)?(?:(\\d+)W)?(?:(\\d+)D)?$")

    enum class PeriodUnit { Year, Month, Week, Day }

    /** "P1M" -> [(Month, 1)], "P3M" -> [(Month, 3)], "P1Y6M" -> [(Year, 1), (Month, 6)]; null if it is not a period. */
    fun parts(iso: String): List<Pair<PeriodUnit, Int>>? {
        val m = PERIOD.matchEntire(iso) ?: return null
        val (y, mo, w, d) = m.destructured
        return listOf(PeriodUnit.Year to y, PeriodUnit.Month to mo, PeriodUnit.Week to w, PeriodUnit.Day to d)
            .mapNotNull { (unit, n) -> n.toIntOrNull()?.takeIf { it > 0 }?.let { unit to it } }
            .ifEmpty { null }
    }

    /** Length in months (a year is exactly 12, a week about 0.23), for ordering plans and comparing prices. */
    fun months(iso: String): Double {
        val m = PERIOD.matchEntire(iso) ?: return Double.MAX_VALUE
        val (y, mo, w, d) = m.destructured.toList().map { it.toIntOrNull() ?: 0 }
        return y * 12.0 + mo + (w * 7 + d) / DAYS_PER_MONTH
    }

    private const val DAYS_PER_MONTH = 365.25 / 12

    /**
     * How much cheaper a longer plan is per month than a shorter one, as a whole percentage, or null if it is
     * not cheaper. For example a 19.99 yearly plan against a 2.99 monthly plan saves 44%.
     */
    fun savingPercent(longMicros: Long, longPeriod: String, shortMicros: Long, shortPeriod: String): Int? {
        val long = months(longPeriod)
        val short = months(shortPeriod)
        if (long <= short || shortMicros <= 0 || long == Double.MAX_VALUE) return null
        val shortForSameTime = shortMicros.toDouble() * long / short
        val saving = ((1 - longMicros / shortForSameTime) * 100).toInt()
        return saving.takeIf { it >= 5 }
    }
}
