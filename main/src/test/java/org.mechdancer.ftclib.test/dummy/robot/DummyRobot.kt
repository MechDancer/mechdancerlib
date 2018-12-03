package org.mechdancer.ftclib.test.dummy.robot

import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.core.structure.composite.chassis.Mecanum
import org.mechdancer.ftclib.core.structure.injector.Inject
import org.mechdancer.ftclib.core.structure.monomeric.DeviceFactory
import org.mechdancer.ftclib.core.structure.monomeric.sensor.RevColorSensor
import org.mechdancer.ftclib.core.structure.monomeric.sensor.VoltageSensor

class DummyRobot : Robot("dummyRobot", Mecanum(enable = true), DummyArm(),
        DeviceFactory.revColor("colorSensor") {
            enable = true
        }
) {

    @Inject
    lateinit var colorSensor: RevColorSensor

    @Inject
    lateinit var voltageSensor: VoltageSensor

    @Inject("mecanumChassis")
    lateinit var chassis: Mecanum

    @Inject
    private lateinit var dummyArm: DummyArm

    var armState: DummyArm.ArmState = DummyArm.ArmState.DOWN

    override fun run() {
        dummyArm.armState = armState
    }
}