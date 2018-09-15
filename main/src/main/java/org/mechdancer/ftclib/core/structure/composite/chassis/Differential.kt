package org.mechdancer.ftclib.core.structure.composite.chassis

import org.mechdancer.ftclib.core.structure.monomeric.device.effector.Motor.Direction.FORWARD
import org.mechdancer.ftclib.core.structure.monomeric.device.effector.Motor.Direction.REVERSE

class Differential(enable: Boolean) : Chassis(arrayOf(
		"左前" to REVERSE, "左后" to REVERSE,
		"右前" to FORWARD, "右后" to FORWARD), enable
) {

	override val name: String = "differentialChassis"

	fun tank(leftPower: Double, rightPower: Double) {
		powers = doubleArrayOf(leftPower, leftPower, rightPower, rightPower)
	}

}