package org.mechdancer.ftclib.test.dummy.opmode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.mechdancer.ftclib.core.opmode.BaseOpMode
import org.mechdancer.ftclib.test.dummy.robot.DummyArm
import org.mechdancer.ftclib.test.dummy.robot.DummyRobot
import org.mechdancer.statemachine.builder.linearStateMachine

@Autonomous(name = "DummyAutomatic", group = "dummy")
@Disabled
class DummyAutomatic : BaseOpMode<DummyRobot>(DummyRobot()) {


	private val armStrategy = linearStateMachine {
		once {
			todo = { robot.armState = DummyArm.ArmState.UP }
		}
		delay { time = 3000 }
		once {
			todo = { robot.armState = DummyArm.ArmState.DOWN }
			until
		}
		build()
	}

	override fun initTask() {
	}

	override fun startTask() {
	}

	override fun loopTask() {
		armStrategy()
		if (armStrategy.isCompleted)
			armStrategy.reset()
	}

	override fun stopTask() {
	}

}