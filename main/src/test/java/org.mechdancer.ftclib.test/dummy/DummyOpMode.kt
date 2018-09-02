package org.mechdancer.ftclib.test.dummy

import org.mechdancer.ftclib.core.opmode.RemoteControlOpMode
import org.mechdancer.ftclib.gamepad.GamePad

class DummyOpMode : RemoteControlOpMode<DummyRobot>(DummyRobot) {

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