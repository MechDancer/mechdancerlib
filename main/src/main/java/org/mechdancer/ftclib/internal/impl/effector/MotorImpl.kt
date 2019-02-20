package org.mechdancer.ftclib.internal.impl.effector

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import org.mechdancer.ftclib.internal.FtcMotor
import org.mechdancer.ftclib.internal.impl.Effector

/**
 * 电机
 * 输出设备
 */
class MotorImpl(name: String,
                enable: Boolean,
                override var direction: Motor.Direction)
    : Motor, Effector<FtcMotor>(name, enable) {

    constructor(config: Motor.Config) : this(config.name, config.enable, config.direction)


    /**
     * 功率
     * 范围：[-1, 1]
     */
    override var power = .0

    /**
     * 运行模式
     * [DcMotor.RunMode]
     */
    var runMode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

    /**
     * 零功率行为
     * [DcMotor.ZeroPowerBehavior]
     */
    var zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE


    override fun DcMotorEx.output() {
        power = this@MotorImpl.power * this@MotorImpl.direction.sign
        zeroPowerBehavior = this@MotorImpl.zeroPowerBehavior
        mode = runMode
    }


    override fun toString() =
        "电机[$name] | ${if (enable) "功率: ${100 * power}%" else "关闭"}"


}
