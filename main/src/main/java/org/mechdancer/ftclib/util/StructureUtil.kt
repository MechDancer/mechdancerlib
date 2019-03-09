package org.mechdancer.ftclib.util

import org.mechdancer.ftclib.core.opmode.BaseOpMode
import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.composite.Robot

/**
 * OpMode life cycle
 *
 * 实现相应接口标识着该 [Structure] 将参与 OpMode 相应生命周期，会被 OpMode 调用。
 * 只有 [Structure] 子类继承有效。
 */
object OpModeLifecycle {
    /**
     *参与 OpMode 的 `init()`
     */
    interface Initialize<T : Robot> : Structure {
        fun init(opMode: BaseOpMode<T>)
    }

    /**
     * 参与 OpMode 的 `start()`
     */
    interface Start : Structure {
        fun start()
    }

    /**
     *参与 OpMode 的 `loop()`
     */
    interface Run : Structure

    /**
     *参与 OpMode 的 `stop()`
     */
    interface Stop : Structure {
        fun stop()
    }
}

/**
 * 实现该接口的结构将具有重置能力
 * 会在 `robot.reset()` 时调用。
 */
interface Resettable : Structure {
    fun reset()
}

/**
 * 可自动调用即为参与 [com.qualcomm.robotcore.eventloop.opmode.OpMode.loop]。
 */
typealias AutoCallable = OpModeLifecycle.Run