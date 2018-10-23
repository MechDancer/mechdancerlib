package org.mechdancer.ftclib.core.structure.composite.chassis

import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor.Direction.FORWARD
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor.Direction.REVERSE

class Differential(override val name: String = "differentialChassis",
                   enable: Boolean) : Chassis(arrayOf(
	"左前" to REVERSE, "左后" to REVERSE,
	"右前" to FORWARD, "右后" to FORWARD), enable
) {

	fun tank(leftPower: Double, rightPower: Double) {
		powers = doubleArrayOf(leftPower, leftPower, rightPower, rightPower)
	}

}