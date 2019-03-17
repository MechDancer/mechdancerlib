package org.mechdancer.ftclib.core.opmode

import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.gamepad.Gamepad

/**
 * Base of remote control OpMode
 *
 * Teleop classes should extend this class.
 */
abstract class RemoteControlOpMode<T : Robot> : BaseOpMode<T>() {
    private val master = Gamepad()
    private val helper = Gamepad()

    final override fun loopTask() {

        master.update(gamepad1)
        helper.update(gamepad2)

        loop(master, helper)
    }

    abstract fun loop(master: Gamepad, helper: Gamepad)
}
