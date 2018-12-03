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


    private val _power = PropertyBuffer(
            tag = "power",
            origin = .0,
            setter = { this.power = it })

    private val _pwmOutput = PropertyBuffer(
            tag = "pwmOutput",
            origin = true,
            setter = {
                (controller as ServoControllerEx).let { ctr ->
                    if (it) ctr.setServoPwmEnable(portNumber)
                    else ctr.setServoPwmDisable(portNumber)
                }
            }
    )

    /**
     * 速度
     */
    override var power by _power

    /**
     * 是否开启 pwm 信号输出
     */
    override var pwmOutput: Boolean by _pwmOutput

    override fun CRServo.output() {
        _power % this
        _pwmOutput % this
    }

    override fun resetData() = run { power = .0 }

    override fun toString() =
            "连续舵机[$name] | ${if (enable) "功率：${100 * power}%" else "关闭"}"


}