package org.mechdancer.ftclib.devices

import org.mechdancer.ftclib.devices.impl.ContinuousServoImpl
import org.mechdancer.ftclib.devices.impl.MotorImpl
import org.mechdancer.ftclib.devices.impl.ServoImpl
import org.mechdancer.ftclib.sensors.Encoder
import org.mechdancer.ftclib.sensors.RevColorSensor
import org.mechdancer.ftclib.sensors.impl.EncoderImpl
import org.mechdancer.ftclib.sensors.impl.RevColorSensorImpl
import org.mechdancer.ftclib.structures.MotorWithEncoder
import org.mechdancer.ftclib.structures.impl.MotorWithEncoderImpl

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

