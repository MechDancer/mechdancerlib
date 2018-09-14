package org.mechdancer.ftclib.test.dummy.robot

import org.mechdancer.ftclib.core.structure.injector.Inject
import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.core.structure.monomeric.device.DeviceFactory
import org.mechdancer.ftclib.core.structure.monomeric.device.sensor.RevColorSensor
import org.mechdancer.ftclib.core.structure.composite.chassis.Mecanum

class DummyRobot : Robot("dummyRobot", Mecanum(true), DummyArm(),
		DeviceFactory.revColor("colorSensor") {
			enable = true
		}
) {

	@Inject
	lateinit var colorSensor: RevColorSensor

	@Inject
	lateinit var dummyArm: DummyArm

	var armState: DummyArm.ArmState = DummyArm.ArmState.DOWN

	override fun run() {
		dummyArm.armState = armState
	}
}