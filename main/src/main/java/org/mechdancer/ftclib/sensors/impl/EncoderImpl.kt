package org.mechdancer.ftclib.sensors.impl

import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.mechdancer.ftclib.core.structure.Sensor
import org.mechdancer.ftclib.sensors.Encoder
import kotlin.math.PI

class EncoderImpl(name: String, enable: Boolean,
                  radians: Double)
	: Encoder, Sensor<DcMotorEx>(name, enable) {

	constructor(config: Encoder.Config) : this(config.name, config.enable, config.radians)

	private var offset = .0

	private var position = .0

	private var speed = .0

	private val scalar = 2 * PI / radians

	override fun DcMotorEx.input() {
		position = scalar * currentPosition - offset
		speed = scalar * getVelocity(AngleUnit.RADIANS)
	}

	override fun DcMotorEx.reset() {
		reset(Math.toRadians(currentPosition.toDouble()))
		speed = getVelocity(AngleUnit.RADIANS)
	}

	override fun resetData() {
		offset = .0
		position = .0
		speed = .0
	}

	override fun reset(off: Double) {
		offset = off
	}

	override fun getPosition(): Double = position

	override fun getSpeed(): Double = speed

	override fun toString(): String = "编码器[$name] | " +
			if (enable) "位置: $position, 速度: $speed" else "关闭"

}