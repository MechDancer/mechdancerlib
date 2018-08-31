package org.mechdancer.ftclib.structures

import com.qualcomm.robotcore.hardware.DcMotor
import org.mechdancer.filters.signalAndSystem.Limiter
import org.mechdancer.filters.signalAndSystem.PID
import org.mechdancer.ftclib.core.structure.AbstractStructure
import org.mechdancer.ftclib.core.structure.DeviceConfig
import org.mechdancer.ftclib.core.structure.injector.Inject
import org.mechdancer.ftclib.devices.Motor
import org.mechdancer.ftclib.sensors.Encoder

class MotorWithEncoder(name: String, enable: Boolean,
                       private val totalRadians: Double,
                       private val pidPosition: PID,
                       private val pidSpeed: PID) : AbstractStructure(
		name, {
	motor(name) {
		this.enable = enable
	}
	encoder(name) {
		this.enable = enable
		this.radians = totalRadians
	}
}) {
	constructor(config: Config) : this(config.name, config.enable,
			config.radians, config.pidPosition, config.pidSpeed)


	@Inject(name = "#ignore#")
	private lateinit var motor: Motor
	@Inject(name = "#ignore#")
	private lateinit var encoder: Encoder

	private val positionLimiter = Limiter(totalRadians)

	var power = .0
		get() = motor.power
		set(value) {
			motor.power = when (mode) {
				Mode.SPEED_CLOSE_LOOP    -> pidSpeed(targetSpeed - speed)
				Mode.OPEN_LOOP           -> value
				Mode.POSITION_CLOSE_LOOP -> pidPosition(targetPosition - position)
				Mode.STOP, Mode.LOCK     -> .0
			}
			field = motor.power
		}
	var position = .0
		private set
		get() = encoder.position
	var speed = .0
		private set
		get() = encoder.speed

	var mode = Mode.OPEN_LOOP
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

	var targetSpeed = .0


	var targetPosition = .0
		set(value) {
			field = positionLimiter(value)
		}

	fun reset(off: Double) {
		encoder.reset(off)
	}

	enum class Mode { SPEED_CLOSE_LOOP, OPEN_LOOP, POSITION_CLOSE_LOOP, LOCK, STOP }

	override fun invoke() {
		if (mode == Mode.LOCK && speed <= 0.05) {
			mode = Mode.POSITION_CLOSE_LOOP
			targetPosition = position
		}
	}

	class Config(name: String, enable: Boolean = false,
	             var radians: Double = .0,
	             var pidPosition: PID = PID(1.0, .0, .0, .0, .0),
	             var pidSpeed: PID = PID(1.0, .0, .0, .0, .0)) : DeviceConfig(name, enable)


}