package org.mechdancer.ftclib.algorithm

/**
 * Counter
 *
 * Usually used to estimate whether a condition is stable.
 */
class Counter(private val limit: Long) : Controller<Boolean> {
    private var count: Long = 0

    /**
     * Returns `true` when condition satisfied [limit] times continuously.
     */
    override operator fun invoke(condition: Boolean): Boolean {
        if (condition)
            count++
        else
            count = 0

        return count >= limit
    }

    /**
     * Resets the counter
     */
    fun reset() {
        count = 0
    }
}