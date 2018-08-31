package org.mechdancer.ftclib.devices

import com.qualcomm.robotcore.hardware.DcMotor
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

	private val _power = PropertyBuffer(
			tag = "power",
			origin = .0,
			setter = { this.power = it },
			isValid = { it in -1..1 })

	private val _zeroBehavior = PropertyBuffer(
			tag = "zeroBehavior",
			origin = DcMotor.ZeroPowerBehavior.FLOAT,
			setter = { this.zeroPowerBehavior = it }
	)

	private val _runMode = PropertyBuffer(
			tag = "runMode",
			origin = DcMotor.RunMode.RUN_USING_ENCODER,
			setter = { this.mode = it }
	)
	/**
	 * 功率
	 * 范围：[-1, 1]
	 */
	var power by _power

	var runMode by _runMode
		internal set

	var zeroPowerBehavior by _zeroBehavior
		internal set


	override fun DcMotorEx.output() {
		_power.sendTo(this)
		_zeroBehavior.sendTo(this)
		_runMode.sendTo(this)
	}

	override fun resetData() {
		power = .0
	}

	override fun toString() =
			"电机[$name] | ${if (enable) "功率：${100 * power}%" else "关闭"}"

	class Config(
			name: String,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}
