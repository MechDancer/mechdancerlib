package org.mechdancer.ftclib.internal.algorithm

import java.lang.Math.abs

/**
 * 滤镜映射器
 * 将 [[minInput],maxInput] 值映射为 [[minOutput]..maxOutput]
 */
class Lens(private val minInput: Double, maxInput: Double,
           private val minOutput: Double, maxOutput: Double) {
	private val reverse = minOutput > maxOutput
	private val temp = abs(maxOutput - minOutput) / (maxInput - minInput)
	private val limiter = Limiter(minInput, maxInput)

	operator fun invoke(data: Double) = ((limiter(data) - minInput) * temp).let {
		if (reverse) minOutput - it else minOutput + it
	}
}