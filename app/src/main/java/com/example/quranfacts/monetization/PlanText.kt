package com.example.quranfacts.monetization

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.example.quranfacts.R
import com.example.quranfacts.monetization.PlanMath.PeriodUnit

/** Google Play's billing periods as words in the app's language. The wording is in strings.xml. */

private fun PeriodUnit.singular() = when (this) {
    PeriodUnit.Year -> R.string.unit_year
    PeriodUnit.Month -> R.string.unit_month
    PeriodUnit.Week -> R.string.unit_week
    PeriodUnit.Day -> R.string.unit_day
}

private fun PeriodUnit.counted() = when (this) {
    PeriodUnit.Year -> R.plurals.n_years
    PeriodUnit.Month -> R.plurals.n_months
    PeriodUnit.Week -> R.plurals.n_weeks
    PeriodUnit.Day -> R.plurals.n_days
}

/** "P1M" -> "month", "P3M" -> "3 months", "P7D" -> "7 days". */
@Composable
fun periodName(iso: String): String {
    val parts = PlanMath.parts(iso) ?: return iso
    return parts.map { (unit, n) ->
        if (n == 1) stringResource(unit.singular()) else pluralStringResource(unit.counted(), n, n)
    }.joinToString(" ")
}

/** Like [periodName] but always with a number ("1 week", "7 days"); used for free trials. */
@Composable
fun periodWithCount(iso: String): String {
    val parts = PlanMath.parts(iso) ?: return iso
    return parts.map { (unit, n) -> pluralStringResource(unit.counted(), n, n) }.joinToString(" ")
}

/** A plan's name from its period: "Monthly", "Yearly", "Weekly", or "Every 3 months". */
@Composable
fun planTitle(iso: String): String = when (iso) {
    "P1W" -> stringResource(R.string.plan_weekly)
    "P1M" -> stringResource(R.string.plan_monthly)
    "P1Y" -> stringResource(R.string.plan_yearly)
    else -> stringResource(R.string.plan_every, periodName(iso))
}
