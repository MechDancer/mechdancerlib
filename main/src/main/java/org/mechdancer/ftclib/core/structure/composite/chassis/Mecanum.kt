package org.mechdancer.ftclib.core.structure.composite.chassis

import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor

/**
 * Mecanum chassis
 */
open class Mecanum(override val name: String = "chassis",
                   enable: Boolean = false,
                   lfMotorDirection: Motor.Direction = Motor.Direction.REVERSE,
                   lbMotorDirection: Motor.Direction = Motor.Direction.REVERSE,
                   rfMotorDirection: Motor.Direction = Motor.Direction.FORWARD,
                   rbMotorDirection: Motor.Direction = Motor.Direction.FORWARD,
                   lfMotorName: String = "LF",
                   lbMotorName: String = "LB",
                   rfMotorName: String = "RF",
                   rbMotorName: String = "RB"
) : Omnidirectinal(arrayOf(
    lfMotorName to lfMotorDirection, lbMotorName to lbMotorDirection,
    rfMotorName to rfMotorDirection, rbMotorName to rbMotorDirection), enable) {


    override fun Descartes.transform() =
        doubleArrayOf(
            +x + y - w,
            +x - y - w,
            +x - y + w,
            +x + y + w)
}