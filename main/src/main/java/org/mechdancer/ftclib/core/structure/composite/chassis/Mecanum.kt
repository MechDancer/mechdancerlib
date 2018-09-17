package org.mechdancer.ftclib.core.structure.composite.chassis

import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor.Direction.FORWARD
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor.Direction.REVERSE

class Mecanum(enable: Boolean)
	: Omnidirectinal(arrayOf(
		"左前" to REVERSE, "左后" to REVERSE,
		"右前" to FORWARD, "右后" to FORWARD), enable) {

	override val name = "mecanumChassis"

	override fun Descartes.transform() =
			doubleArrayOf(x + y - w, x - y - w, x - y + w, x + y + w)
}