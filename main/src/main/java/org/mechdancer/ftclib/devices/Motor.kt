package org.mechdancer.ftclib.devices

import com.qualcomm.robotcore.hardware.DcMotorEx
import org.mechdancer.ftclib.core.structure.Device
import org.mechdancer.ftclib.core.structure.DeviceConfig

/**
 * 电机
 * 输出设备
 */
class Motor(name: String, enable: Boolean)
	: Device<DcMotorEx>(name, enable) {

	constructor(config: Config) : this(config.name, config.enable)

	/**
	 * 功率
	 * 范围：[-1, 1]
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

	override fun DcMotorEx.output() = _power.sendTo(this)

	override fun resetData() = run { power = .0 }

	override fun toString() =
			"电机[$name] | ${if (enable) "功率：${100 * power}%" else "关闭"}"

	class Config(
			name: String,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}
