package org.mechdancer.ftclib.internal.impl.effector

import com.qualcomm.robotcore.hardware.ServoControllerEx
import org.mechdancer.filters.signalAndSystem.Lens
import org.mechdancer.ftclib.core.structure.monomeric.effector.Servo
import org.mechdancer.ftclib.internal.FtcServo
import org.mechdancer.ftclib.internal.impl.Effector

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
	: Servo, Effector<FtcServo>(name, enable) {

	constructor(config: Servo.Config) : this(config.name, config.enable, config.origin, config.ending)

	private val _position = PropertyBuffer(
		tag = "position",
		origin = origin,
		setter = { this.position = map(it) })

	private val _pwmOutput = PropertyBuffer(
		tag = "pwmOutput",
		origin = true,
		setter = {
			(controller as ServoControllerEx).let { ctr ->
				if (it) ctr.setServoPwmEnable(portNumber)
				else ctr.setServoPwmDisable(portNumber)
			}
		}

	)
	/**
	 * 目标位置
	 * 范围：起点到终点的区间
	 */
	override var position by _position

	/**
	 * 是否开启 pwm 信号输出
	 */
	override var pwmOutput: Boolean by _pwmOutput

	/**
	 * 映射方案
	 */
	private val map = Lens(origin, ending, -1.0, 1.0)


	override fun FtcServo.output() {
		_position % this
		_pwmOutput % this
	}

	override fun resetData() = run { position = .0 }

	override fun toString() =
		"舵机[$name] | ${if (enable) "位置: $position" else "关闭"}"


}