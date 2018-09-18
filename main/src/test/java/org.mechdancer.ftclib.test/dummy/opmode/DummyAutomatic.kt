package org.mechdancer.ftclib.test.dummy.opmode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.mechdancer.ftclib.core.opmode.BaseOpMode
import org.mechdancer.ftclib.test.dummy.robot.DummyRobot

@Autonomous(name = "DummyAutomatic", group = "dummy")
@Disabled
class DummyAutomatic : BaseOpMode<DummyRobot>(DummyRobot()) {
	override fun initTask() {
	}

	override fun startTask() {
	}
	override fun loopTask() {
	}

	override fun stopTask() {
	}

}