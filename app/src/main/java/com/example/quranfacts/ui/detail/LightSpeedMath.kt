package com.example.quranfacts.ui.detail

import kotlin.math.PI
import kotlin.math.cos

/**
 * The arithmetic behind the speed-of-light reading of Quran 32:5 (Hassab-Elnaby's method),
 * kept free of UI so it can be unit-tested. Every choice the method depends on is an
 * explicit parameter, which is the whole point of the lab.
 */
object LightSpeedMath {
    const val MOON_ORBIT_RADIUS_KM = 384_264.0
    const val SIDEREAL_MONTH_DAYS = 27.321661
    const val TROPICAL_YEAR_DAYS = 365.2422
    const val SIDEREAL_DAY_S = 86_164.0906
    const val SOLAR_DAY_S = 86_400.0
    const val LIGHT_KM_S = 299_792.458

    data class Result(
        val months: Int,
        val orbitKm: Double,
        val alphaDeg: Double,
        val cosAlpha: Double,
        val pathKm: Double,
        val distanceKm: Double,
        val daySeconds: Double,
        val speedKmS: Double,
        val diffPercent: Double,
    )

    fun compute(years: Int, sidereal: Boolean, correction: Boolean): Result {
        val months = years * 12
        val orbitKm = 2 * PI * MOON_ORBIT_RADIUS_KM
        // The Earth sweeps this angle round the Sun while the Moon completes one sidereal orbit.
        val alphaDeg = 360.0 * SIDEREAL_MONTH_DAYS / TROPICAL_YEAR_DAYS
        val cosAlpha = cos(Math.toRadians(alphaDeg))
        val pathKm = if (correction) orbitKm * cosAlpha else orbitKm
        val distanceKm = months * pathKm
        val daySeconds = if (sidereal) SIDEREAL_DAY_S else SOLAR_DAY_S
        val speed = distanceKm / daySeconds
        return Result(
            months, orbitKm, alphaDeg, cosAlpha, pathKm, distanceKm, daySeconds, speed,
            diffPercent = (speed / LIGHT_KM_S - 1) * 100,
        )
    }
}
