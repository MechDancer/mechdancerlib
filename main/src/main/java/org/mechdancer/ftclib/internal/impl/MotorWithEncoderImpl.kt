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


    private val motor = MotorImpl(name, enable, direction)

    private val encoder = EncoderImpl(name, enable, cpr)

    override val subStructures: List<Structure> = listOf(motor, encoder)


    override var power
        get() = motor.power
        set(value) {
            motor.power = value
        }
    override var direction: Motor.Direction
        get() = motor.direction
        set(value) {
            motor.direction = value
        }

    override var mode = Mode.OPEN_LOOP
        set(value) {
            fun setMotorState(zeroPowerBehavior: DcMotor.ZeroPowerBehavior,
                              runMode: DcMotor.RunMode) {
                motor.zeroPowerBehavior = zeroPowerBehavior
                motor.runMode = runMode
            }
            when (value) {
                Mode.SPEED_CLOSE_LOOP, Mode.POSITION_CLOSE_LOOP ->
                    setMotorState(DcMotor.ZeroPowerBehavior.FLOAT,
                        DcMotor.RunMode.RUN_USING_ENCODER)

                Mode.OPEN_LOOP, Mode.STOP                       ->
                    setMotorState(DcMotor.ZeroPowerBehavior.FLOAT,
                        DcMotor.RunMode.RUN_WITHOUT_ENCODER)

                Mode.LOCK                                       ->
                    setMotorState(DcMotor.ZeroPowerBehavior.BRAKE,
                        DcMotor.RunMode.RUN_USING_ENCODER)

            }
            field = value
        }

    override var targetSpeed = .0


    override var targetPosition = .0

    override val position: Double
        get() = encoder.position * direction.sign

    override val speed: Double
        get() = encoder.speed * direction.sign

    override fun lock() {
        mode = Mode.LOCK
    }

    override fun reset(off: Double) {
        encoder.reset(off)
        pidPosition.reset()
        pidSpeed.reset()
    }

    override fun reset() {
        reset(.0)
    }


    override fun run() {
        motor.power = when (mode) {
            Mode.SPEED_CLOSE_LOOP    -> pidSpeed(targetSpeed - speed)
            Mode.OPEN_LOOP           -> motor.power
            Mode.POSITION_CLOSE_LOOP -> pidPosition(targetPosition - position)
            Mode.STOP, Mode.LOCK     -> .0
        }

        if (mode == Mode.LOCK && speed <= 0.05) {
            mode = Mode.POSITION_CLOSE_LOOP
            targetPosition = position
        }
    }

    override fun toString(): String = "电机x编码器[$name] | ${if (enable) "功率: ${100 * power}% " +
        "位置: $position 速度: $speed" else "关闭"}"

}