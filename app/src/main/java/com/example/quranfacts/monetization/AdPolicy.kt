package com.example.quranfacts.monetization

/**
 * How often a free user sees a full-screen ad: "sometimes". At most one ad every [EVERY_FACTS] facts opened,
 * and never two within [MIN_GAP_MS]. Kept free of Android types so it can be unit-tested.
 */
object AdPolicy {
    const val EVERY_FACTS = 4
    const val MIN_GAP_MS = 3 * 60_000L

    /**
     * @param factsSinceAd facts opened since the last ad, including the one being opened now
     * @param lastAdAt when the last ad was shown (epoch millis), or 0 if never
     */
    fun shouldShow(factsSinceAd: Int, lastAdAt: Long, now: Long): Boolean =
        factsSinceAd >= EVERY_FACTS && (lastAdAt == 0L || now - lastAdAt >= MIN_GAP_MS)
}
