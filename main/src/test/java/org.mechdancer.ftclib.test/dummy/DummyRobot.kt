package org.mechdancer.ftclib.test.dummy

import org.mechdancer.ftclib.core.structure.Robot
import org.mechdancer.ftclib.structures.impl.Mecanum

object DummyRobot : Robot("dummyRobot", DummyArm) {
	override val chassis: Mecanum = Mecanum(true)

	override fun run() {

	}
}