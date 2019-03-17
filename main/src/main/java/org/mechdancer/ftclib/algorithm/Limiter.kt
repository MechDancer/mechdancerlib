package org.mechdancer.ftclib.algorithm

import java.lang.Math.abs

/**
 * Limiter
 *
 * Values grater than max will be reduced to max,
 * smaller than min will be magnified to min.
 */
class Limiter(min: Double, max: Double) {
    private val min: Double
    private val max: Double

    init {
        if (min <= max) {
            this.min = min
            this.max = max
        } else throw IllegalArgumentException("Min is grater that max")
    }

    constructor(max: Double = 1.0) : this(-abs(max), abs(max))

    /**
     * Runs limiter
     */
    operator fun invoke(data: Double) = if (data < min) min else if (data > max) max else data
}