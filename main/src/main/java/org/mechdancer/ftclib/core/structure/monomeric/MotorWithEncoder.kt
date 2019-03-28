package org.mechdancer.ftclib.core.structure.monomeric

import org.mechdancer.ftclib.algorithm.PID
import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import org.mechdancer.ftclib.core.structure.monomeric.sensor.Encoder
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * A combination of [Motor] and [Encoder]
 */
interface MotorWithEncoder : Motor, Encoder, Structure {

    /**
     * Mode of the motor
     */
    var mode: Mode

    /**
     * Target speed
     *
     * Available when [mode] equals to [Mode.SPEED_CLOSE_LOOP]
     */
    var targetSpeed: Double

    /**
     * Target speed
     *
     * Available when [mode] equals to [Mode.POSITION_CLOSE_LOOP]
     */
    var targetPosition: Double

    /**
     * Lock this motor
     *
     * Implemented through position-close-loop control
     */
    override var lock: Boolean

    /**
     * Motor mode
     */
    enum class Mode {
        /**
         * Speed-close-loop control
         *
         * See [targetSpeed]
         */
        SPEED_CLOSE_LOOP,
        /**
         * Open loop
         *
         * See [power]
         */
        OPEN_LOOP,
        /**
         * Position-close-loop control
         *
         * See [targetPosition]
         */
        POSITION_CLOSE_LOOP,
        /**
         * Stop
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
