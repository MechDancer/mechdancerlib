package org.mechdancer.ftclib.internal.impl.effector

import com.qualcomm.robotcore.hardware.ServoControllerEx
import org.mechdancer.ftclib.core.structure.monomeric.effector.Servo
import org.mechdancer.ftclib.internal.FtcServo
import org.mechdancer.ftclib.internal.algorithm.Lens
import org.mechdancer.ftclib.internal.impl.Effector

class ServoImpl(
    name: String,
    enable: Boolean,
    origin: Double,
    ending: Double)
    : Servo, Effector<FtcServo>(name, enable) {

    constructor(config: Servo.Config) : this(config.name, config.enable, config.origin, config.ending)

    @Volatile
    private var shouldUpdatePwmEnable = false

    override var position = .0

    override var pwmOutput: Boolean = true
        set(value) {
            shouldUpdatePwmEnable = true
            field = value
        }

    private val map = Lens(origin, ending, .0, 1.0)


    override fun FtcServo.output() {
        position = map(this@ServoImpl.position)
        if (shouldUpdatePwmEnable)
            (controller as ServoControllerEx).let { ctr ->
                if (pwmOutput)
                    ctr.setServoPwmEnable(portNumber)
                else ctr.setServoPwmDisable(portNumber)
                shouldUpdatePwmEnable = false
            }
    }


    override fun toString() =
        "Servo[$name] | ${if (enable) "Position: $position rad" else "Disabled"}"

}