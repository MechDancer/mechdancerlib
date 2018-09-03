package org.mechdancer.ftclib.devices.impl

import org.mechdancer.ftclib.core.structure.Device
import org.mechdancer.ftclib.devices.Servo
import org.mechdancer.ftclib.devices.TCServo
import kotlin.math.abs

/**
 * 普通舵机功能扩展类
 * @param name 名字
 * @param origin 初始位置
 * @param ending 最远位置id
 * @param enable 使能
 */
class ServoImpl(
		name: String,
		enable: Boolean,
		origin: Double,
		ending: Double)
	: Servo, Device<TCServo>(name, enable) {

	constructor(config: Servo.Config) : this(config.name, config.enable, config.origin, config.ending)

	private val _position = PropertyBuffer(
			tag = "position",
			origin = origin,
			setter = { this.position = map(it) },
			isValid = { it in range })

	/**
	 * 目标位置
	 * 范围：起点到终点的区间
	 */
	override var position by _position

	/**
	 * 映射方案
	 */
	private val map = { property: Double ->
		abs((property - origin) / (ending - origin))
	}

	/**
	 * 取值范围
	 */
	private val range =
			if (origin < ending)
				origin..ending
			else
				ending..origin


	override fun TCServo.output() = _position.sendTo(this)

	override fun resetData() = run { position = .0 }

	override fun toString() =
			"舵机[$name] | ${if (enable) "位置: $position" else "关闭"}"


}