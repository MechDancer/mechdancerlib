package org.mechdancer.ftclib.core.structure.composite.chassis.locator

import org.mechdancer.ftclib.core.structure.composite.chassis.Mecanum
import org.mechdancer.ftclib.core.structure.monomeric.MotorWithEncoder
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import org.mechdancer.ftclib.internal.algorithm.PID
import org.mechdancer.ftclib.internal.impl.MotorWithEncoderImpl

open class MecanumWithLocator(name: String,
                              motorCpr: Double,
                              enable: Boolean,
                              lfMotorDirection: Motor.Direction,
                              lbMotorDirection: Motor.Direction,
                              rfMotorDirection: Motor.Direction,
                              rbMotorDirection: Motor.Direction,
                              lfMotorName: String = "LF",
                              lbMotorName: String = "LB",
                              rfMotorName: String = "RF",
                              rbMotorName: String = "RB")
    : Mecanum(name, enable,
    lfMotorDirection,
    lbMotorDirection,
    rfMotorDirection,
    rbMotorDirection,
    lfMotorName,
    lbMotorName,
    rfMotorName,
    rbMotorName), Locator {

    final override val subStructures: List<MotorWithEncoder> =
        arrayOf(lfMotorName, lbMotorName, rfMotorName, rbMotorName)
            .map {
                MotorWithEncoderImpl(it, enable, motorCpr,
                    when (it) {
                        lfMotorName -> lfMotorDirection
                        lbMotorName -> lbMotorDirection
                        rfMotorName -> rfMotorDirection
                        rbMotorName -> rbMotorDirection
                        else        -> throw RuntimeException()
                    }, PID.zero(), PID.zero())
            }

    override var location = Location(.0, .0, .0)

    override fun run() {
        super.run()
        location = Location(
            subStructures[0].position + subStructures[1].position + subStructures[2].position + subStructures[3].position,
            subStructures[0].position - subStructures[1].position - subStructures[2].position + subStructures[3].position,
            -subStructures[0].position - subStructures[1].position + subStructures[2].position + subStructures[3].position
        )
    }

}