package org.mechdancer.ftclib.structures.impl

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.mechdancer.filters.signalAndSystem.Limiter
import org.mechdancer.filters.signalAndSystem.PID
import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.OpModeFlow
import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.devices.impl.MotorImpl
import org.mechdancer.ftclib.sensors.impl.EncoderImpl
import org.mechdancer.ftclib.structures.MotorWithEncoder
import org.mechdancer.ftclib.structures.MotorWithEncoder.Mode

class MotorWithEncoderImpl(override val name: String,
                           val enable: Boolean,
                           radians: Double,
                           direction: DcMotorSimple.Direction,
                           private val pidPosition: PID,
                           private val pidSpeed: PID
) : MotorWithEncoder,
    CompositeStructure, OpModeFlow.AutoCallable {
	constructor(config: MotorWithEncoder.Config) : this(config.name, config.enable,
			config.radians, config.direction, config.pidPosition, config.pidSpeed)


	private val motor = MotorImpl(name, enable, direction)

	private val encoder = EncoderImpl(name, enable, radians)

	override val subStructures: List<Structure> = listOf(motor, encoder)

	private val positionLimiter = Limiter(radians)

	override var power
		get() = motor.power
		set(value) {
			motor.power = when (mode) {
				Mode.SPEED_CLOSE_LOOP    -> pidSpeed(targetSpeed - getSpeed())
				Mode.OPEN_LOOP           -> value
				Mode.POSITION_CLOSE_LOOP -> pidPosition(targetPosition - getPosition())
				Mode.STOP, Mode.LOCK     -> .0
			}
		}
	override var direction: DcMotorSimple.Direction
		get() = motor.direction
		set(value) {
			motor.direction = value
		}

	override var mode = Mode.OPEN_LOOP
		set(value) {
			fun setMotorState(zeroPowerBehavior: DcMotor.ZeroPowerBehavior,
			                  runMode: DcMotor.RunMode) {
				motor.zeroPowerBehavior = zeroPowerBehavior
				motor.runMode = runMode
			}
			when (value) {
				Mode.SPEED_CLOSE_LOOP, Mode.POSITION_CLOSE_LOOP ->
					setMotorState(DcMotor.ZeroPowerBehavior.FLOAT,
							DcMotor.RunMode.RUN_USING_ENCODER)

				Mode.OPEN_LOOP, Mode.STOP                       ->
					setMotorState(DcMotor.ZeroPowerBehavior.FLOAT,
							DcMotor.RunMode.RUN_WITHOUT_ENCODER)

				Mode.LOCK                                       ->
					setMotorState(DcMotor.ZeroPowerBehavior.BRAKE,
							DcMotor.RunMode.RUN_USING_ENCODER)

			}
			field = value
		}

	override var targetSpeed = .0


	override var targetPosition = .0
		set(value) {
			field = positionLimiter(value)
		}


	override fun getPosition(): Double = encoder.getPosition()

	override fun getSpeed(): Double = encoder.getSpeed()


	override fun reset(off: Double) {
		encoder.reset(off)
	}


	override fun run() {
		if (mode == Mode.LOCK && getSpeed() <= 0.05) {
			mode = Mode.POSITION_CLOSE_LOOP
			targetPosition = getPosition()
		}
	}

	override fun toString(): String = "电机x编码器[$name] | ${if (enable) "功率: ${100 * power}% " +
			"位置: ${getPosition()} 速度: ${getSpeed()}" else "关闭"}"

}