package org.mechdancer.ftclib.test.dummy.robot

import org.mechdancer.ftclib.core.structure.injector.Inject
import org.mechdancer.ftclib.core.structure.model.Robot
import org.mechdancer.ftclib.devices.DeviceFactory
import org.mechdancer.ftclib.sensors.RevColorSensor
import org.mechdancer.ftclib.structures.impl.Mecanum

object DummyRobot : Robot("dummyRobot", Mecanum(true), DummyArm,
		DeviceFactory.revColor("colorSensor") {
			enable = true
		}
) {

	@Inject
	lateinit var colorSensor: RevColorSensor

	var armState: DummyArm.ArmState = DummyArm.ArmState.DOWN

	override fun run() {
		DummyArm.armState = armState

	}
}