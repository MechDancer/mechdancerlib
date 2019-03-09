package org.mechdancer.ftclib.internal.impl.effector

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import org.mechdancer.ftclib.internal.FtcMotor
import org.mechdancer.ftclib.internal.impl.Effector
import kotlin.properties.Delegates

class MotorImpl(name: String,
                enable: Boolean,
                override var direction: Motor.Direction)
    : Motor, Effector<FtcMotor>(name, enable) {

    constructor(config: Motor.Config) : this(config.name, config.enable, config.direction)


    override var power = .0

    var runMode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

    var zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT


    private var lastZeroPowerBehavior = zeroPowerBehavior

    override var lock: Boolean by Delegates.observable(false) { _, old, new ->
        if (old == new) return@observable
        if (new) {
            lastZeroPowerBehavior = zeroPowerBehavior
            zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        } else
            zeroPowerBehavior = lastZeroPowerBehavior

    }

    override fun DcMotorEx.output() {
        if (lock)
            this@MotorImpl.power = .0
        power = this@MotorImpl.power * this@MotorImpl.direction.sign
        zeroPowerBehavior = this@MotorImpl.zeroPowerBehavior
        mode = runMode
    }


    override fun toString() =
        "Motor[$name] | ${if (enable) "Power: ${100 * power}%" else "Disabled"}"


}
