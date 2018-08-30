package org.mechdancer.ftclib.devices

import com.qualcomm.robotcore.hardware.Servo
import org.mechdancer.ftclib.core.structure.Device
import org.mechdancer.ftclib.core.structure.DeviceConfig
import kotlin.math.abs

/**
 * 普通舵机功能扩展类
 * @param name 名字
 * @param origin 初始位置
 * @param ending 最远位置id
 * @param enable 使能
 */
class Servo(
		name: String,
		origin: Double,
		ending: Double,
		enable: Boolean)
	: Device<Servo>(name, enable) {

	constructor(config: Config) : this(config.name, config.origin, config.ending, config.enable)

	/**
	 * 目标位置
	 * 范围：起点到终点的区间
	 */
	var position
		get() = _position.value
		set(value) {
			_position.value = value
		}

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

	private val _position = PropertyBuffer(
			tag = "position",
			origin = origin,
			setter = { this.position = map(it) },
			isValid = { it in range })

	override fun Servo.output() = _position.sendTo(this)

	override fun resetData() = run { position = .0 }

	override fun toString() =
			"舵机[$name] | ${if (enable) "位置：$position" else "关闭"}"

	class Config(
			name: String,
			var origin: Double = .0,
			var ending: Double = .0,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}