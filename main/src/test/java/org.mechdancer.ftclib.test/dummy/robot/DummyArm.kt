package org.mechdancer.ftclib.test.dummy.robot

import org.mechdancer.ftclib.core.structure.composite.AbstractStructure
import org.mechdancer.ftclib.core.structure.injector.Inject
import org.mechdancer.ftclib.core.structure.monomeric.DeviceFactory
import org.mechdancer.ftclib.core.structure.monomeric.MotorWithEncoder
import org.mechdancer.ftclib.internal.algorithm.PID
import org.mechdancer.ftclib.util.Resettable
import kotlin.math.PI

class DummyArm : AbstractStructure("dummyArm",
        DeviceFactory.motorWithEncoder("core") {
            enable = true
            pidPosition = PID(0.233, .0, .0, .0, .0)
            cpr = 1120.0
        }
), Resettable {

    @Inject
    private lateinit var core: MotorWithEncoder

    var armState = ArmState.UP
        set(value) {
            core.targetPosition = when (value) {
                ArmState.UP       -> 2 * PI
                ArmState.PARALLEL -> PI
                ArmState.DOWN     -> .0
            }
            field = value
        }

    fun lock() {
        core.lock()
    }

    override fun reset() {
        core.mode = MotorWithEncoder.Mode.POSITION_CLOSE_LOOP
        armState = ArmState.UP
    }

    enum class ArmState { UP, PARALLEL, DOWN }
}