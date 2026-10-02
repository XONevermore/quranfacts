package com.example.quranfacts

import com.example.quranfacts.monetization.AdPolicy
import com.example.quranfacts.monetization.PlanMath
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MonetizationTest {
    private val now = 1_000_000_000L

    @Test
    fun noAdForTheFirstFewFacts() {
        (1 until AdPolicy.EVERY_FACTS).forEach { assertFalse("fact $it", AdPolicy.shouldShow(it, lastAdAt = 0, now = now)) }
        assertTrue(AdPolicy.shouldShow(AdPolicy.EVERY_FACTS, lastAdAt = 0, now = now))
    }

    @Test
    fun neverTwoAdsWithinTheGap() {
        val justNow = now - AdPolicy.MIN_GAP_MS + 1
        assertFalse(AdPolicy.shouldShow(10, lastAdAt = justNow, now = now))
        assertTrue(AdPolicy.shouldShow(10, lastAdAt = now - AdPolicy.MIN_GAP_MS, now = now))
    }

    @Test
    fun enoughTimeIsNotEnoughWithoutEnoughFacts() {
        assertFalse(AdPolicy.shouldShow(AdPolicy.EVERY_FACTS - 1, lastAdAt = now - 24 * 3_600_000L, now = now))
    }

    @Test
    fun billingPeriodsAreParsed() {
        assertEquals(listOf(PlanMath.PeriodUnit.Month to 1), PlanMath.parts("P1M"))
        assertEquals(listOf(PlanMath.PeriodUnit.Year to 1), PlanMath.parts("P1Y"))
        assertEquals(listOf(PlanMath.PeriodUnit.Month to 3), PlanMath.parts("P3M"))
        assertEquals(listOf(PlanMath.PeriodUnit.Day to 7), PlanMath.parts("P7D"))
        assertEquals(listOf(PlanMath.PeriodUnit.Year to 1, PlanMath.PeriodUnit.Month to 6), PlanMath.parts("P1Y6M"))
        assertNull(PlanMath.parts("garbage"))
        assertNull(PlanMath.parts("P0D"))
    }

    @Test
    fun yearlySavingIsWorkedOutPerMonth() {
        // 19.99 a year against 2.99 a month (35.88 a year) saves 44%.
        assertEquals(44, PlanMath.savingPercent(19_990_000, "P1Y", 2_990_000, "P1M"))
        // A plan is never compared with itself or with a longer one, and tiny savings are not advertised.
        assertNull(PlanMath.savingPercent(2_990_000, "P1M", 2_990_000, "P1M"))
        assertNull(PlanMath.savingPercent(35_000_000, "P1Y", 2_990_000, "P1M"))
        assertEquals(12.0, PlanMath.months("P1Y"), 0.0)
        assertTrue(PlanMath.months("P1Y") > PlanMath.months("P6M"))
    }
}
