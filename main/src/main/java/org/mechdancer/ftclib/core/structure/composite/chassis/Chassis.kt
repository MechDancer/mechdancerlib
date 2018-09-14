package org.mechdancer.ftclib.core.structure.composite.chassis

import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.OpModeFlow
import org.mechdancer.ftclib.core.structure.monomeric.device.effector.Motor
import org.mechdancer.ftclib.internal.impl.effector.MotorImpl
import java.util.logging.Logger
import kotlin.math.abs

abstract class Chassis(motorsConfig: Array<Pair<String, Motor.Direction>>, enable: Boolean)
	:  CompositeStructure("chassis"), OpModeFlow.AutoCallable {

	final override val subStructures = motorsConfig.map { MotorImpl(it.first, enable, it.second) }

	open var powers = DoubleArray(motorsConfig.size) { .0 }
		get() = field.standardizeBy(maxPower)
		set(value) {
			if (value.size != field.size)
				logger.warning("电机功率输入与电机数量不符(${value.size} ≠ ${field.size})")
			else
				field = value
		}

	open var maxPower = 1.0
		set(value) {
			if (value !in 0..1)
				logger.warning("电机功率限制不合理($maxPower ∉ [0,1])")
			else
				field = value
		}

	/**
	 * 功率标准化
	 * 若传入的功率中存在大于传入的最大值约束的值，将最大值调整为约束值，其他值按比例缩小
	 * @param maxPower 最大功率约束∈[0,1]
	 */
	private fun DoubleArray.standardizeBy(maxPower: Double) =
			(this.maxBy { abs(it) } ?: 1.0).let {
				if (it <= maxPower) this
				else this.map { p -> p / it * maxPower }.toDoubleArray()
			}

	override fun run() {
		subStructures.forEachIndexed { index: Int, motor: Motor ->
			motor.power = powers[index]
		}
	}

	override fun toString() = name

	protected companion object {
		val logger: Logger = Logger.getLogger("Chassis")
	}
}
