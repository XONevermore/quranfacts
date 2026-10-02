package com.example.quranfacts

import com.example.quranfacts.ui.detail.LightSpeedMath
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.abs

class LightSpeedMathTest {

    @Test
    fun theAuthorsVersionLandsWithinAThousandthOfAPercent() {
        val r = LightSpeedMath.compute(years = 1_000, sidereal = true, correction = true)
        assertEquals(299_790.0, r.speedKmS, 5.0)
        assertTrue("diff was ${r.diffPercent}%", abs(r.diffPercent) < 0.001)
        assertEquals(12_000, r.months)
    }

    @Test
    fun withoutTheEarthMotionCorrectionTheAnswerIsAboutTwelvePercentHigh() {
        val r = LightSpeedMath.compute(years = 1_000, sidereal = true, correction = false)
        assertEquals(336_252.0, r.speedKmS, 5.0)
        assertEquals(12.2, r.diffPercent, 0.1)
    }

    @Test
    fun fiftyThousandYearsGivesFiftyTimesTheSpeed() {
        val a = LightSpeedMath.compute(1_000, sidereal = true, correction = true)
        val b = LightSpeedMath.compute(50_000, sidereal = true, correction = true)
        assertEquals(50.0, b.speedKmS / a.speedKmS, 1e-9)
    }

    @Test
    fun aSolarDayInsteadOfASiderealDayShiftsTheResultByAboutAQuarterPercent() {
        val sid = LightSpeedMath.compute(1_000, sidereal = true, correction = true)
        val sol = LightSpeedMath.compute(1_000, sidereal = false, correction = true)
        assertEquals(0.27, abs(sol.speedKmS / sid.speedKmS - 1) * 100, 0.01)
    }

    @Test
    fun theCorrectionAngleIsTheEarthsSweepInOneSiderealMonth() {
        val r = LightSpeedMath.compute(1_000, sidereal = true, correction = true)
        assertEquals(26.93, r.alphaDeg, 0.01)
        assertEquals(0.8916, r.cosAlpha, 0.0001)
    }
}
