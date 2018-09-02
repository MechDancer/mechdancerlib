package org.mechdancer.ftclib.core.opmode

import org.mechdancer.ftclib.core.structure.Robot
import org.mechdancer.ftclib.gamepad.GamePad

abstract class RemoteControlOpMode<T : Robot>(robot: T) : BaseOpMode<T>(robot) {

	private val master = GamePad()

	private val helper = GamePad()

	final override fun loopTask() {
		master.update(gamepad1)
		helper.update(gamepad1)
		loop(master, helper)
	}

	abstract fun loop(master: GamePad, helper: GamePad)


}