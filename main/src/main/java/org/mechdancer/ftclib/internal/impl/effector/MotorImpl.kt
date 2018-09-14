package org.mechdancer.ftclib.internal.impl.effector

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.mechdancer.ftclib.core.structure.monomeric.device.Effector
import org.mechdancer.ftclib.core.structure.monomeric.device.effector.Motor
import org.mechdancer.ftclib.internal.impl.TCMotor

/**
 * 电机
 * 输出设备
 */
class MotorImpl(name: String,
                enable: Boolean,
                direction: Motor.Direction)
	: Motor, Effector<TCMotor>(name, enable) {

	constructor(config: Motor.Config) : this(config.name, config.enable, config.direction)

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
	private val _direction = PropertyBuffer(
			tag = "direction",
			origin = direction,
			setter = {
				this.direction =
						when (it) {
							Motor.Direction.FORWARD -> DcMotorSimple.Direction.FORWARD
							Motor.Direction.REVERSE -> DcMotorSimple.Direction.REVERSE
						}
			}
	)
	/**
	 * 功率
	 * 范围：[-1, 1]
	 */
	override var power by _power

	override var direction by _direction

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
			"电机[$name] | ${if (enable) "功率: ${100 * power}%" else "关闭"}"


}
