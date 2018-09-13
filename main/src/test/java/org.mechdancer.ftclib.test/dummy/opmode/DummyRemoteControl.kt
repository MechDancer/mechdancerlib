package org.mechdancer.ftclib.test.dummy.opmode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.mechdancer.ftclib.core.opmode.RemoteControlOpMode
import org.mechdancer.ftclib.gamepad.GamePad
import org.mechdancer.ftclib.test.dummy.robot.DummyArm
import org.mechdancer.ftclib.test.dummy.robot.DummyRobot

@TeleOp(name = "DummyRemoteControl", group = "dummy")
@Disabled
class DummyRemoteControl : RemoteControlOpMode<DummyRobot>(DummyRobot()) {

	override fun initTask() {

	}

	override fun loop(master: GamePad, helper: GamePad) {
		if (master.a.isPressing())
			when (robot.armState) {
				DummyArm.ArmState.DOWN     -> DummyArm.ArmState.PARALLEL
				DummyArm.ArmState.PARALLEL -> DummyArm.ArmState.UP
				DummyArm.ArmState.UP       -> DummyArm.ArmState.DOWN
			}
	}

	override fun stopTask() {

	}
}