package org.mechdancer.ftclib.internal.impl.effector

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.ServoControllerEx
import org.mechdancer.ftclib.core.structure.monomeric.effector.ContinuousServo
import org.mechdancer.ftclib.internal.FtcContinuousServo
import org.mechdancer.ftclib.internal.impl.Effector

/** 连续舵机功能扩展类  */
class ContinuousServoImpl(name: String, enable: Boolean)
    : Effector<FtcContinuousServo>(name, enable), ContinuousServo {

    constructor(config: ContinuousServo.Config) : this(config.name, config.enable)

    private var shouldUpdatePwmEnable = false

    /**
     * 速度
     */
    override var power = .0

    /**
     * 是否开启 pwm 信号输出
     */
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
            "连续舵机[$name] | ${if (enable) "功率：${100 * power}%" else "关闭"}"

}