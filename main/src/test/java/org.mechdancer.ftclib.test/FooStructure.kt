package org.mechdancer.ftclib.test

import org.mechdancer.filters.signalAndSystem.PID
import org.mechdancer.ftclib.core.structure.AbstractStructure
import org.mechdancer.ftclib.core.structure.injector.Inject
import org.mechdancer.ftclib.devices.Servo
import org.mechdancer.ftclib.devices.motorWithEncoder
import org.mechdancer.ftclib.devices.servo
import org.mechdancer.ftclib.structures.MotorWithEncoder
import org.mechdancer.ftclib.structures.MotorWithEncoder.Mode
import kotlin.math.PI

class FooStructure : AbstractStructure({
	motorWithEncoder("fooMotor") {
		enable = true
		radians = 2.0 * PI
		pidPosition = PID(0.233, .0, .0, .0, .0)
	}
	servo("barServo") {
		enable = true
		origin = .0
		ending = 130.0
	}
}) {

	@Inject
	lateinit var fooMotor: MotorWithEncoder

	@Inject
	private lateinit var barServo: Servo

	override fun run() {
		fooMotor.mode = Mode.POSITION_CLOSE_LOOP
		fooMotor.targetPosition = 2 * PI
		barServo.position = 100.0
	}

}