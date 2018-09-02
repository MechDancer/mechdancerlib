package org.mechdancer.ftclib.test.dummy

import org.mechdancer.ftclib.core.structure.Robot
import org.mechdancer.ftclib.core.structure.injector.Inject
import org.mechdancer.ftclib.devices.DeviceFactory
import org.mechdancer.ftclib.sensors.RevColorSensor
import org.mechdancer.ftclib.structures.impl.Mecanum

object DummyRobot : Robot("dummyRobot", DummyArm,
		DeviceFactory.revColor("colorSensor") {
			enable = true
		}
) {
	override val chassis: Mecanum = Mecanum(true)

	@Inject
	private lateinit var arm: DummyArm

	@Inject
	lateinit var colorSensor: RevColorSensor

	var armState: DummyArm.ArmState = DummyArm.ArmState.DOWN

	override fun run() {
		arm.armState = armState
	}
}