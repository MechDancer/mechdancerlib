package org.mechdancer.ftclib.devices

import com.qualcomm.robotcore.hardware.CRServo
import org.mechdancer.ftclib.core.structure.Device
import org.mechdancer.ftclib.core.structure.DeviceConfig

/** 连续舵机功能扩展类  */
class ContinuousServo(name: String, enable: Boolean)
	: Device<CRServo>(name, enable) {

	constructor(config: Config) : this(config.name, config.enable)

	/**
	 * 速度
	 */
	var power
		get() = _power.value
		set(value) {
			_power.value = value
		}

	private val _power = PropertyBuffer(
			tag = "power",
			origin = .0,
			setter = { this.power = it },
			isValid = { it in -1..1 })

	override fun CRServo.output() = _power.sendTo(this)

	override fun resetData() = run { power = .0 }

	override fun toString() =
			"连续舵机[$name] | ${if (enable) "功率：${100 * power}%" else "关闭"}"

	class Config(
			name: String,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}