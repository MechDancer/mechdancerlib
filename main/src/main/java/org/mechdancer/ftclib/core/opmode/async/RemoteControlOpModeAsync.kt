package org.mechdancer.ftclib.core.opmode.async

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.mechdancer.ftclib.algorithm.REPEAT
import org.mechdancer.ftclib.algorithm.StateMachine
import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.gamepad.Gamepad

@Disabled
abstract class RemoteControlOpModeAsync<T : Robot> : BaseOpModeAsync<T>() {
    private val master = Gamepad()
    private val helper = Gamepad()

    final override val loopMachine: StateMachine = {
        master.update(gamepad1)
        helper.update(gamepad2)
        loop(master, helper)
        REPEAT
    }

    abstract fun loop(master: Gamepad, helper: Gamepad)
}
