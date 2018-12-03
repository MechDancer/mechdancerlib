package org.mechdancer.ftclib.test.foo

import org.mechdancer.ftclib.core.structure.composite.AbstractStructure
import org.mechdancer.ftclib.core.structure.injector.Inject
import org.mechdancer.ftclib.core.structure.monomeric.MotorWithEncoder
import org.mechdancer.ftclib.core.structure.monomeric.MotorWithEncoder.Mode
import org.mechdancer.ftclib.core.structure.monomeric.effector.Servo
import org.mechdancer.ftclib.core.structure.monomeric.motorWithEncoder
import org.mechdancer.ftclib.core.structure.monomeric.servo
import org.mechdancer.ftclib.internal.algorithm.PID
import kotlin.math.PI

class FooStructure : AbstractStructure("fooo", {
    motorWithEncoder("fooMotor") {
        enable = true
        cpr = 2.0 * PI
        pidPosition = PID(0.233, .0, .0, .0, .0)
    }
    servo("barServo") {
        enable = true
        origin = .0
        ending = 130.0
    }
}) {

    @Inject
    lateinit var fooMotor: MotorWithEncoder

    @Inject
    private lateinit var barServo: Servo

    override fun run() {
        fooMotor.mode = Mode.POSITION_CLOSE_LOOP
        fooMotor.targetPosition = 2 * PI
        barServo.position = 100.0
    }

}