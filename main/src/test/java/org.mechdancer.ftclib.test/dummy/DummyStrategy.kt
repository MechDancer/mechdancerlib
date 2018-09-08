package org.mechdancer.ftclib.test.dummy

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.mechdancer.ftclib.test.dummy.robot.DummyArm
import org.mechdancer.ftclib.test.dummy.robot.DummyRobot
import org.mechdancer.statemachine.StateMachine
import org.mechdancer.statemachine.state

object DummyStrategy {

	private val init = state {
		doing { DummyRobot.armState = DummyArm.ArmState.UP }
	}

	private val idle = state {
		doing {
			launch {
				delay(1000)
				machine1.transfer(arm1)
			}
		}
	}

	private val arm1 = state {
		doing { DummyRobot.armState = DummyArm.ArmState.DOWN }
	}

	val machine1 = StateMachine(init)


	init {
		machine1 register (init to idle)
	}

}