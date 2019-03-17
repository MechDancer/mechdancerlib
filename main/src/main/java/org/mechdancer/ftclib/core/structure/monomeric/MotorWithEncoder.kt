package org.mechdancer.ftclib.core.structure.monomeric

import org.mechdancer.ftclib.algorithm.PID
import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import org.mechdancer.ftclib.core.structure.monomeric.sensor.Encoder
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * 电机编码器组合
 *
 * 具有电机和编码器一切性质。其不应具有多态性，但为约定行为，实现 [Motor], [Encoder]。
 * 在拥有编码器的同时为电机提供了闭环功能。
 */
interface MotorWithEncoder : Motor, Encoder, Structure {

    /**
     * 电机模式
     */
    var mode: Mode

    /**
     * 目标速度
     *
     * 在 [mode] 为 [Mode.SPEED_CLOSE_LOOP] 时有效
     */
    var targetSpeed: Double

    /**
     * 目标位置
     *
     * 在 [mode] 为 [Mode.POSITION_CLOSE_LOOP] 时有效
     */
    var targetPosition: Double

    /**
     * 锁定电机
     *
     * 通过闭位置环实现
     */
    override var lock: Boolean

    /**
     * 电机模式枚举
     */
    enum class Mode {
        /**
         * 闭速度环
         */
        SPEED_CLOSE_LOOP,
        /**
         * 开环
         */
        OPEN_LOOP,
        /**
         * 闭位置环
         */
        POSITION_CLOSE_LOOP,
        /**
         * 停止
         */
        STOP
    }

    /**
     * 配置
     *
     * @param name 名字
     * @param enable 是否启用
     * @param cpr 编码器一圈的值
     * @param direction 电机方向
     * @param pidPosition 位置环 pid 参数 [PID]
     * @param pidSpeed 速度环 pid 参数 [PID]
     */
    class Config(name: String,
                 enable: Boolean = false,
                 var cpr: Double = .0,
                 var direction: Motor.Direction = Motor.Direction.FORWARD,
                 var pidPosition: PID = PID.zero(),
                 var pidSpeed: PID = PID.zero()) : DeviceConfig(name, enable)

    companion object CPR {
        const val NeveRest3_7 = 44.4
        const val NeveRest20 = 560.0
        const val Neverest40 = 1120.0
        const val NeveRest60 = 1680.0
        const val RevRobotics20HdHex = 1120.0
        const val RevRobotics40HdHex = 2240.0
        const val RevRoboticsCoreHex = 290.0
        const val Tetrix = 1440.0
        const val Matrix12V = 1478.4
        const val MatrixLegacy = 757.12
    }

}
