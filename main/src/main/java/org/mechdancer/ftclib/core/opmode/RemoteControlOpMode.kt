package org.mechdancer.ftclib.core.opmode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.gamepad.Gamepad

/**
 * 遥控专用程序入口
 * 实现手柄数据同步，遥控程序应继承该类
 */
@TeleOp
@Disabled
abstract class RemoteControlOpMode<T : Robot>(opModeName: String) : BaseOpMode<T>(opModeName) {
	private val master = Gamepad()
	private val helper = Gamepad()

	final override fun loopTask() {
		master.update(gamepad1)
		helper.update(gamepad1)
		loop(master, helper)
	}

	abstract fun loop(master: Gamepad, helper: Gamepad)
}
