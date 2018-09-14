package org.mechdancer.ftclib.test.dummy.robot

import org.mechdancer.filters.signalAndSystem.PID
import org.mechdancer.ftclib.core.structure.OpModeFlow
import org.mechdancer.ftclib.core.structure.injector.Inject
import org.mechdancer.ftclib.core.structure.composite.AbstractStructure
import org.mechdancer.ftclib.core.structure.monomeric.device.DeviceFactory
import org.mechdancer.ftclib.core.structure.monomeric.MotorWithEncoder
import kotlin.math.PI

class DummyArm : AbstractStructure("dummyArm",
		DeviceFactory.motorWithEncoder("core") {
			enable = true
			pidPosition = PID(0.233, .0, .0, .0, .0)
			radians = 2 * PI
		}
), OpModeFlow.Initialisable {

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

	override fun init() {
		core.mode = MotorWithEncoder.Mode.POSITION_CLOSE_LOOP
	}

	enum class ArmState { UP, PARALLEL, DOWN }
}