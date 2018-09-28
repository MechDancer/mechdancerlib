package org.mechdancer.ftclib.test.dummy.opmode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.mechdancer.ftclib.core.opmode.RemoteControlOpMode
import org.mechdancer.ftclib.gamepad.Gamepad
import org.mechdancer.ftclib.test.dummy.robot.DummyArm.ArmState
import org.mechdancer.ftclib.test.dummy.robot.DummyRobot

@TeleOp(name = "DummyRemoteControl", group = "dummy")
@Disabled
class DummyRemoteControl : RemoteControlOpMode<DummyRobot>(DummyRobot()) {

	override fun initTask() {

	}

	override fun loop(master: Gamepad, helper: Gamepad) {
		if (master.a.isPressing())
			when (robot.armState) {
				ArmState.DOWN     -> ArmState.PARALLEL
				ArmState.PARALLEL -> ArmState.UP
				ArmState.UP       -> ArmState.DOWN
			}
	}

	override fun stopTask() {

	}
}