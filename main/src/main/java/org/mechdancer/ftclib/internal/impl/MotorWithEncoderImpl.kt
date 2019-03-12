package org.mechdancer.ftclib.internal.impl

import com.qualcomm.robotcore.hardware.DcMotor
import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.monomeric.MotorWithEncoder
import org.mechdancer.ftclib.core.structure.monomeric.MotorWithEncoder.Mode
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import org.mechdancer.ftclib.internal.algorithm.PID
import org.mechdancer.ftclib.internal.impl.effector.MotorImpl
import org.mechdancer.ftclib.internal.impl.sensor.EncoderImpl
import org.mechdancer.ftclib.util.AutoCallable
import org.mechdancer.ftclib.util.Resettable

class MotorWithEncoderImpl(name: String,
                           val enable: Boolean,
                           cpr: Double,
                           direction: Motor.Direction,
                           val pidPosition: PID,
                           val pidSpeed: PID
) : MotorWithEncoder, Resettable,
    CompositeStructure(name), AutoCallable {

    constructor(config: MotorWithEncoder.Config) : this(config.name, config.enable,
        config.cpr, config.direction, config.pidPosition, config.pidSpeed)


    val motor = MotorImpl(name, enable, direction)

    val encoder = EncoderImpl(name, enable, cpr)

    override val subStructures: List<Structure> = listOf(motor, encoder)

    override var power = .0

    override var direction: Motor.Direction
        get() = motor.direction
        set(value) {
            motor.direction = value
        }

    override var mode = Mode.OPEN_LOOP

    override var targetSpeed = .0

    override var targetPosition = .0

    override val position: Double
        get() = encoder.position * direction.sign

    override val speed: Double
        get() = encoder.speed * direction.sign

    override var lock: Boolean = false

    override fun reset(off: Double) {
        encoder.reset(off)
        motor.runMode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        pidPosition.reset()
        pidSpeed.reset()
    }

    override fun reset() {
        reset(.0)
        mode = Mode.OPEN_LOOP
    }


    override fun run() {
        if (lock) {
            motor.lock = speed >= 0.05
            mode = Mode.POSITION_CLOSE_LOOP
            targetPosition = position
        }

        power = when (mode) {
            Mode.SPEED_CLOSE_LOOP    -> pidSpeed(targetSpeed - speed)
            Mode.OPEN_LOOP           -> power
            Mode.POSITION_CLOSE_LOOP -> pidPosition(targetPosition - position)
            Mode.STOP                -> .0
        }

        motor.power = power
    }

    override fun toString(): String = "MotorWithEncoder[$name] | ${if (enable) "Power: ${100 * power}% " +
        "Position: $position rad, Speed: $speed rad/s" else "Disabled"}"

}