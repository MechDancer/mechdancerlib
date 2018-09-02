package org.mechdancer.ftclib.test.dummy

import org.mechdancer.filters.signalAndSystem.PID
import org.mechdancer.ftclib.core.structure.AbstractStructure
import org.mechdancer.ftclib.core.structure.injector.Inject
import org.mechdancer.ftclib.structures.MotorWithEncoder
import kotlin.math.PI

object DummyArm : AbstractStructure("dummyArm", {
	motorWithEncoder("core") {
		enable = true
		pidPosition = PID(0.233, .0, .0, .0, .0)
		radians = 2 * PI
	}
}) {

	@Inject
	private lateinit var core: MotorWithEncoder

	var armState = ArmState.UP
		set(value) {
			core.targetPosition = when (value) {
				ArmState.UP       -> 2 * PI
				ArmState.PARALLEL -> PI
				ArmState.DOWN     -> .0
			}
			field = value
		}

	fun lock() {
		core.mode = MotorWithEncoder.Mode.LOCK
	}

	enum class ArmState { UP, PARALLEL, DOWN }
}