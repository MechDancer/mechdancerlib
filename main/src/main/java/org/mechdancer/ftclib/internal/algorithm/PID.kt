package org.mechdancer.ftclib.internal.algorithm

import java.lang.Math.abs

/**
 * PID controller
 */
class PID(var k: Double,
          var ki: Double,
          var kd: Double,
          var integrateArea: Double,
          var deadArea: Double) {

    companion object {
        /**
         * Creates a pid controller, all parameters in which is 0.
         */
        fun zero() = PID(.0, .0, .0, .0, .0)
    }

    private var sum = 0.0
    private var last = 0.0

    /**
     * Run pid controller
     */
    operator fun invoke(data: Double): Double {
        val value = abs(data)
        sum = if (value > integrateArea) .0 else sum + data
        val result = data + kd * (data - last) + ki * sum
        last = data
        return if (value < deadArea) .0.also {
            sum = .0
        }
        else k * result
    }

    /**
     * Resets running parameters
     */
    fun reset() {
        sum = 0.0
        last = 0.0
    }

    override fun toString(): String = "PID[P:$k I:$ki D:$kd IA:$integrateArea DA:$deadArea]"
}