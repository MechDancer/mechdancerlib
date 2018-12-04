package org.mechdancer.ftclib.core.structure.composite.chassis

import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor

open class Mecanum(override val name: String = "mecanum_chassis",
                   lfMotorDirection: Motor.Direction,
                   lbMotorDirection: Motor.Direction,
                   rfMotorDirection: Motor.Direction,
                   rbMotorDirection: Motor.Direction,
                   enable: Boolean,
                   lfMotorName: String = "LF",
                   lbMotorName: String = "LB",
                   rfMotorName: String = "RF",
                   rbMotorName: String = "RB"
) : Omnidirectinal(arrayOf(
    lfMotorName to lfMotorDirection, lbMotorName to lbMotorDirection,
    rfMotorName to rfMotorDirection, rbMotorName to rbMotorDirection), enable) {


    override fun Descartes.transform() =
        doubleArrayOf(x + y - w, x - y - w, x - y + w, x + y + w)
}