package org.mechdancer.ftclib.core.opmode

import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.gamepad.GamePad

/**
 * 遥控专用程序入口
 * 实现手柄数据同步，遥控程序应继承该类
 */
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
