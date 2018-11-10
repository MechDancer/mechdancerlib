package org.mechdancer.ftclib.core.structure.composite.chassis

import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor.Direction.FORWARD
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor.Direction.REVERSE

class Mecanum(override val name: String = "mecanum_chassis",
              lfMotorName: String = "LF",
              lbMotorName: String = "LB",
              rfMotorName: String = "RF",
              rbMotorName: String = "RB"
              , enable: Boolean)
	: Omnidirectinal(arrayOf(
	lfMotorName to REVERSE, lbMotorName to REVERSE,
	rfMotorName to FORWARD, rbMotorName to FORWARD), enable) {


	override fun Descartes.transform() =
		doubleArrayOf(x + y - w, x - y - w, x - y + w, x + y + w)
}