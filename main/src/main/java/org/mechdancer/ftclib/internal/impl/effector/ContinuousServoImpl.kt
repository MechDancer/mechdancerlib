package org.mechdancer.ftclib.internal.impl.effector

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.ServoControllerEx
import org.mechdancer.ftclib.core.structure.monomeric.effector.ContinuousServo
import org.mechdancer.ftclib.internal.FtcContinuousServo
import org.mechdancer.ftclib.internal.impl.Effector

class ContinuousServoImpl(name: String, enable: Boolean)
    : Effector<FtcContinuousServo>(name, enable), ContinuousServo {

    constructor(config: ContinuousServo.Config) : this(config.name, config.enable)

    @Volatile
    private var shouldUpdatePwmEnable = false

    override var power = .0

    override var pwmOutput: Boolean = true
        set(value) {
            shouldUpdatePwmEnable = true
            field = value
        }


    override fun CRServo.output() {
        power = this@ContinuousServoImpl.power
        if (shouldUpdatePwmEnable)
            (controller as ServoControllerEx).let { ctr ->
                if (pwmOutput)
                    ctr.setServoPwmEnable(portNumber)
                else ctr.setServoPwmDisable(portNumber)
                shouldUpdatePwmEnable = false
            }
    }

    override fun toString() =
        "ContinuousServo[$name] | ${if (enable) "Power：${100 * power}%" else "Disabled"}"

}