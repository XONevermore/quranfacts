package com.example.quranfacts

import com.example.quranfacts.ui.detail.SixDaysMath
import org.junit.Assert.assertEquals
import org.junit.Test

class SixDaysMathTest {
    @Test
    fun eachDayIsASixthOfCosmicHistory() {
        val r = SixDaysMath.compute(firstDay = 5)
        assertEquals(2.3, r.perDayBn, 1e-9)
        assertEquals(4.6, r.earthSpanBn, 1e-9)
    }

    @Test
    fun lastTwoDaysStartNearTheEarthsAge() {
        val r = SixDaysMath.compute(firstDay = 5)
        assertEquals(4.6, r.startAgoBn, 1e-9)
        assertEquals(0.0, r.endAgoBn, 1e-9)
        assertEquals(1.3, r.diffPercent, 0.05)
    }

    @Test
    fun firstTwoDaysEndLongBeforeTheEarthFormed() {
        val r = SixDaysMath.compute(firstDay = 1)
        assertEquals(13.8, r.startAgoBn, 1e-9)
        assertEquals(9.2, r.endAgoBn, 1e-9)
    }

    @Test
    fun earthFormsAtTheStartOfDayFive() {
        val r = SixDaysMath.compute(firstDay = 1)
        assertEquals(4, (r.earthOnLine * SixDaysMath.DAYS).toInt())
    }
}
