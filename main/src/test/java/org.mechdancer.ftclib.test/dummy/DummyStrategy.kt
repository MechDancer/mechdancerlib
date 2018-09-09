package org.mechdancer.ftclib.test.dummy

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.mechdancer.ftclib.test.dummy.robot.DummyArm
import org.mechdancer.ftclib.test.dummy.robot.DummyRobot
import org.mechdancer.statemachine.IState
import org.mechdancer.statemachine.StateMachine

object DummyStrategy {

	enum class States1 : IState {
		Init {
			override val loop: Boolean = false
			override fun doing() {
				DummyRobot.armState = DummyArm.ArmState.UP
			}
		},
		Idle {
			override val loop: Boolean = false
			override fun doing() {
				launch {
					delay(1000)
					machine1.transfer(Arm1)
				}
			}
		},
		Arm1 {
			override val loop: Boolean = false
			override fun doing() {
				DummyRobot.armState = DummyArm.ArmState.DOWN
			}
		}
	}


	val machine1 = StateMachine(States1.Init)


	init {
		machine1 register (States1.Init to States1.Idle)
	}

}