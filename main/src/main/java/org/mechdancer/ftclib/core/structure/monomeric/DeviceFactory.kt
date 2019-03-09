@file:JvmName("DeviceFactoryDsl")

package org.mechdancer.ftclib.core.structure.monomeric

import org.mechdancer.ftclib.core.structure.StructureBuilder
import org.mechdancer.ftclib.core.structure.monomeric.effector.ContinuousServo
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import org.mechdancer.ftclib.core.structure.monomeric.effector.Servo
import org.mechdancer.ftclib.core.structure.monomeric.sensor.ColorSensor
import org.mechdancer.ftclib.core.structure.monomeric.sensor.Encoder
import org.mechdancer.ftclib.core.structure.monomeric.sensor.TouchSensor
import org.mechdancer.ftclib.internal.impl.MotorWithEncoderImpl
import org.mechdancer.ftclib.internal.impl.effector.ContinuousServoImpl
import org.mechdancer.ftclib.internal.impl.effector.MotorImpl
import org.mechdancer.ftclib.internal.impl.effector.ServoImpl
import org.mechdancer.ftclib.internal.impl.sensor.ColorSensorImpl
import org.mechdancer.ftclib.internal.impl.sensor.EncoderImpl
import org.mechdancer.ftclib.internal.impl.sensor.TouchSensorImpl

/**
 * DSL device factory
 */
object DeviceFactory {

    /**
     * Create a [Motor]
     */
    fun motor(name: String, config: Motor.Config.() -> Unit): Motor =
        MotorImpl(Motor.Config(name).apply(config))

    /**
     * Create an [Encoder]
     */
    fun encoder(name: String, config: Encoder.Config.() -> Unit): Encoder =
        EncoderImpl(Encoder.Config(name).apply(config))

    /**
     * Create a [MotorWithEncoder]
     */
    fun motorWithEncoder(name: String, config: MotorWithEncoder.Config.() -> Unit): MotorWithEncoder =
        MotorWithEncoderImpl(MotorWithEncoder.Config(name).apply(config))

    /**
     * Create a [Servo]
     */
    fun servo(name: String, config: Servo.Config.() -> Unit): Servo =
        ServoImpl(Servo.Config(name).apply(config))

    /**
     * Create a [ContinuousServo]
     */
    fun continuousServo(name: String, config: ContinuousServo.Config.() -> Unit): ContinuousServo =
        ContinuousServoImpl(ContinuousServo.Config(name).apply(config))

    /**
     * Create a [ColorSensor]
     */
    fun colorSensor(name: String, config: ColorSensor.Config.() -> Unit): ColorSensor =
        ColorSensorImpl(ColorSensor.Config(name).apply(config))

    /**
     * Create a [TouchSensor]
     */
    fun touchSensor(name: String, config: TouchSensor.Config.() -> Unit): TouchSensor =
        TouchSensorImpl(TouchSensor.Config(name).apply(config))
}

/**
 * Create a [Motor]
 */
fun StructureBuilder.motor(name: String, config: Motor.Config.() -> Unit) {
    subStructure(DeviceFactory.motor(name, config))
}

/**
 * Create an [Encoder]
 */
fun StructureBuilder.encoder(name: String, config: Encoder.Config.() -> Unit) {
    subStructure(DeviceFactory.encoder(name, config))
}

/**
 * Create a [MotorWithEncoder]
 */
fun StructureBuilder.motorWithEncoder(name: String, config: MotorWithEncoder.Config.() -> Unit) {
    subStructure(DeviceFactory.motorWithEncoder(name, config))
}

/**
 * Create a [Servo]
 */
fun StructureBuilder.servo(name: String, config: Servo.Config.() -> Unit) {
    subStructure(DeviceFactory.servo(name, config))
}

/**
 * Create a [ContinuousServo]
 */
fun StructureBuilder.continuousServo(name: String, config: ContinuousServo.Config.() -> Unit) {
    subStructure(DeviceFactory.continuousServo(name, config))
}

/**
 * Create a [ColorSensor]
 */
fun StructureBuilder.colorSensor(name: String, config: ColorSensor.Config.() -> Unit) {
    subStructure(DeviceFactory.colorSensor(name, config))
}

/**
 * Create a [TouchSensor]
 */
fun StructureBuilder.touchSensor(name: String, config: TouchSensor.Config.() -> Unit) {
    subStructure(DeviceFactory.touchSensor(name, config))
}