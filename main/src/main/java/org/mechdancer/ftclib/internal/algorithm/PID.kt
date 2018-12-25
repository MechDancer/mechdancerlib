package org.mechdancer.ftclib.internal.algorithm

import java.lang.Math.abs

/**
 * PID 控制器
 */
class PID(var k: Double,
          var ki: Double,
          var kd: Double,
          var integrateArea: Double,
          var deadArea: Double) {

    companion object {
        fun zero() = PID(.0, .0, .0, .0, .0)
    }

    /** 运行参数  */
    private var sum = 0.0
    private var last = 0.0

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

    /** 重置运行间参数  */
    fun reset() {
        sum = 0.0
        last = 0.0
    }

    override fun toString(): String = "PID[P:$k I:$ki D:$kd IA:$integrateArea DA:$deadArea]"
}