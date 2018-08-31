package org.mechdancer.ftclib.sensors

import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.mechdancer.ftclib.core.structure.DeviceConfig
import org.mechdancer.ftclib.core.structure.Sensor
import kotlin.math.PI

class Encoder(name: String, enable: Boolean,
              totalRadians: Double)
	: Sensor<DcMotorEx>(name, enable) {

	constructor(config: Config) : this(config.name, config.enable, config.radians)

	private var offset = .0

	var position = .0
		private set
	var speed = .0
		private set

	private val scalar = 2 * PI / totalRadians

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

	fun reset(off: Double) {
		offset = off
	}

	override fun toString(): String = "编码器[$name] | " +
			if (enable) "位置: $position, 速度: $speed" else "关闭"

	class Config(name: String, enable: Boolean = false, var radians: Double = .0) : DeviceConfig(name, enable)

}