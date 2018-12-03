package org.mechdancer.ftclib.core.structure.composite.chassis

import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor.Direction.FORWARD
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor.Direction.REVERSE

open class Differential(override val name: String = "differential_chassis",
                        enable: Boolean) : Chassis(arrayOf(
        "LF" to REVERSE, "LB" to REVERSE,
        "RF" to FORWARD, "RB" to FORWARD), enable
) {

    fun tank(leftPower: Double, rightPower: Double) {
        powers = doubleArrayOf(leftPower, leftPower, rightPower, rightPower)
    }

}