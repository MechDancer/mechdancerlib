package org.mechdancer.ftclib.core.structure.composite.chassis.locator

import org.mechdancer.ftclib.core.structure.composite.chassis.Mecanum
import org.mechdancer.ftclib.core.structure.monomeric.MotorWithEncoder
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import org.mechdancer.ftclib.internal.algorithm.PID
import org.mechdancer.ftclib.internal.impl.MotorWithEncoderImpl

open class MecanumWithLocator(name: String,
                              motorCpr: Double,
                              lfMotorName: String = "LF",
                              lbMotorName: String = "LB",
                              rfMotorName: String = "RF",
                              rbMotorName: String = "RB",
                              enable: Boolean)
    : Mecanum(name, lfMotorName, lbMotorName, rfMotorName, rbMotorName, enable), Locator {

    final override val subStructures: List<MotorWithEncoder> =
            arrayOf(lfMotorName, lbMotorName, rfMotorName, rbMotorName)
                    .map {
                        MotorWithEncoderImpl(it, enable, motorCpr,
                                when (it) {
                                    lfMotorName, lbMotorName -> Motor.Direction.REVERSE
                                    else                     -> Motor.Direction.FORWARD
                                }, PID.zero(), PID.zero())
                    }

    override var location = Location(.0, .0, .0)

    override fun run() {
        super.run()
        location = Location(
                subStructures[0].position - subStructures[1].position - subStructures[2].position + subStructures[3].position,
                subStructures[0].position - subStructures[1].position - subStructures[2].position + subStructures[3].position,
                subStructures[2].position + subStructures[3].position - subStructures[1].position - subStructures[0].position
        )
    }

}