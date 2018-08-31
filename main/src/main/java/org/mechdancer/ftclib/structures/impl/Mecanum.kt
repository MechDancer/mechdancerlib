package org.mechdancer.ftclib.structures.impl

import org.mechdancer.ftclib.structures.Omnidirectinal

class Mecanum(enable: Boolean)
	: Omnidirectinal(arrayOf("左前", "左后", "右前", "右后"), enable) {
	override val name = "麦克纳姆"

	override fun Descartes.transform() =
			doubleArrayOf(y + x - w, y - x - w, y - x + w, y + x + w)
}