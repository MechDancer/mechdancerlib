package org.mechdancer.ftclib.internal.impl.sensor

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.mechdancer.ftclib.core.structure.monomeric.sensor.Encoder
import org.mechdancer.ftclib.internal.impl.Sensor
import kotlin.math.PI

class EncoderImpl(name: String, enable: Boolean,
                  radians: Double)
	: Encoder, Sensor<DcMotorEx>(name, enable) {

	constructor(config: Encoder.Config) : this(config.name, config.enable, config.radians)

	private var offset = .0

	override var position = .0
		private set

	override var speed = .0
		private set

	private var raw = .0

	private val scalar = 2 * PI / radians

	override fun DcMotorEx.input() {
		raw = currentPosition * scalar
		position = raw - offset
		speed = getVelocity(AngleUnit.RADIANS)
	}

	override fun DcMotorEx.reset() {
		input()
		mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
	}

	override fun resetData() {

	}

	override fun reset() {
		super.reset()
		reset(.0)
	}

	override fun reset(off: Double) {
		offset = raw - off
	}


	override fun toString(): String = "编码器[$name] | " +
			if (enable) "位置: $position, 速度: $speed" else "关闭"

}