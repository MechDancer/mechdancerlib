@file:JvmName("DeviceFactoryDsl")

package org.mechdancer.ftclib.core.structure.monomeric

import org.mechdancer.ftclib.core.structure.StructureBuilder
import org.mechdancer.ftclib.core.structure.monomeric.effector.ContinuousServo
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import org.mechdancer.ftclib.core.structure.monomeric.effector.Servo
import org.mechdancer.ftclib.core.structure.monomeric.sensor.Encoder
import org.mechdancer.ftclib.core.structure.monomeric.sensor.RevColorSensor
import org.mechdancer.ftclib.internal.impl.MotorWithEncoderImpl
import org.mechdancer.ftclib.internal.impl.effector.ContinuousServoImpl
import org.mechdancer.ftclib.internal.impl.effector.MotorImpl
import org.mechdancer.ftclib.internal.impl.effector.ServoImpl
import org.mechdancer.ftclib.internal.impl.sensor.EncoderImpl
import org.mechdancer.ftclib.internal.impl.sensor.RevColorSensorImpl

/**
 * DSL 设备工厂
 */
object DeviceFactory {
    fun motor(name: String, config: Motor.Config.() -> Unit): Motor =
            MotorImpl(Motor.Config(name).apply(config))

    fun encoder(name: String, config: Encoder.Config.() -> Unit): Encoder =
            EncoderImpl(Encoder.Config(name).apply(config))

    fun motorWithEncoder(name: String, config: MotorWithEncoder.Config.() -> Unit): MotorWithEncoder =
            MotorWithEncoderImpl(MotorWithEncoder.Config(name).apply(config))

    fun servo(name: String, config: Servo.Config.() -> Unit): Servo =
            ServoImpl(Servo.Config(name).apply(config))

    fun continuousServo(name: String, config: ContinuousServo.Config.() -> Unit): ContinuousServo =
            ContinuousServoImpl(ContinuousServo.Config(name).apply(config))

    fun revColor(name: String, config: RevColorSensor.Config.() -> Unit): RevColorSensor =
            RevColorSensorImpl(RevColorSensor.Config(name).apply(config))
}

fun StructureBuilder.motor(name: String, config: Motor.Config.() -> Unit) {
    subStructure(DeviceFactory.motor(name, config))
}

fun StructureBuilder.encoder(name: String, config: Encoder.Config.() -> Unit) {
    subStructure(DeviceFactory.encoder(name, config))
}

fun StructureBuilder.motorWithEncoder(name: String, config: MotorWithEncoder.Config.() -> Unit) {
    subStructure(DeviceFactory.motorWithEncoder(name, config))
}


fun StructureBuilder.servo(name: String, config: Servo.Config.() -> Unit) {
    subStructure(DeviceFactory.servo(name, config))
}

fun StructureBuilder.continuousServo(name: String, config: ContinuousServo.Config.() -> Unit) {
    subStructure(DeviceFactory.continuousServo(name, config))
}

fun StructureBuilder.revColor(name: String, config: RevColorSensor.Config.() -> Unit) {
    subStructure(DeviceFactory.revColor(name, config))
}