package org.mechdancer.ftclib.internal.impl.effector

import com.qualcomm.robotcore.hardware.CRServo
import org.mechdancer.ftclib.core.structure.monomeric.device.Effector
import org.mechdancer.ftclib.internal.impl.TCContinuousServo
import org.mechdancer.ftclib.core.structure.monomeric.device.effector.ContinuousServo

/** 连续舵机功能扩展类  */
class ContinuousServoImpl(name: String, enable: Boolean)
	: Effector<TCContinuousServo>(name, enable), ContinuousServo {

	constructor(config: ContinuousServo.Config) : this(config.name, config.enable)


	private val _power = PropertyBuffer(
			tag = "power",
			origin = .0,
			setter = { this.power = it },
			isValid = { it in -1..1 })

	/**
	 * 速度
	 */
	override var power by _power


	override fun CRServo.output() = _power.sendTo(this)

	override fun resetData() = run { power = .0 }

	override fun toString() =
			"连续舵机[$name] | ${if (enable) "功率：${100 * power}%" else "关闭"}"


}