package org.mechdancer.ftclib.internal.algorithm

import java.lang.Math.abs

/**
 * PID 控制器
 */
class PID(private val k: Double,
          private val ki: Double,
          private val kd: Double,
          private val integrateIArea: Double,
          private val deadArea: Double) {

	/** 运行参数  */
	private var sum = 0.0
	private var last = 0.0

	operator fun invoke(data: Double): Double {
		val value = abs(data)
		sum = if (value > integrateIArea) .0 else sum + data
		val result = data + kd * (data - last) + ki * sum
		last = data
		return if (value < deadArea) .0
		else k * result
	}

	/** 重置运行间参数  */
	fun reset() {
		sum = 0.0
		last = 0.0
	}
}