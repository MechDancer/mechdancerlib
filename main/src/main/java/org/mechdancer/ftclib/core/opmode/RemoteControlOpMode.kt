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
abstract class RemoteControlOpMode<T : Robot>(opModeName: String? = null) : BaseOpMode<T>(opModeName) {
    private val master = Gamepad()
    private val helper = Gamepad()

    final override fun loopTask() {
        withMeasuringTime("刷新主手柄") {
            master.update(gamepad1)
        }
        withMeasuringTime("刷新副手柄") {
            helper.update(gamepad2)
        }
        withMeasuringTime("执行循环任务") {
            loop(master, helper)
        }
    }

    abstract fun loop(master: Gamepad, helper: Gamepad)
}
