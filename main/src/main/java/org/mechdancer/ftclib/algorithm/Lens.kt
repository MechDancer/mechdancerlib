package org.mechdancer.ftclib.algorithm

import java.lang.Math.abs

/**
 * Lens
 *
 * Maps the value from [[minInput],maxInput] to [[minOutput],maxOutput.]
 */
class Lens(private val minInput: Double, maxInput: Double,
           private val minOutput: Double, maxOutput: Double) : Controller<Double> {
    private val reverse = minOutput > maxOutput
    private val temp = abs(maxOutput - minOutput) / (maxInput - minInput)
    private val limiter = Limiter(minInput, maxInput)

    /**
     * Runs lens
     */
    override operator fun invoke(data: Double) = ((limiter(data) - minInput) * temp).let {
        if (reverse) minOutput - it else minOutput + it
    }
}