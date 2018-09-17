package org.mechdancer.ftclib.util.config.component

internal sealed class DeviceConfig(val name: String, val port: Int) : HardwareConfigDsl {

	class ServoConfig(name: String, port: Int) : DeviceConfig(name, port)
	class Matrix12vMotorConfig(name: String, port: Int) : DeviceConfig(name, port)
	class MotorConfig(name: String, port: Int) : DeviceConfig(name, port)
	class LynxEmbeddedImuConfig(name: String, val bus: Int, port: Int) : DeviceConfig(name, port)

	private val builder = StringBuilder()

	override fun start() {
		builder.appendln(when (this) {
			is LynxEmbeddedImuConfig -> """<LynxEmbeddedImu name="$name" bus="$bus" port="$port"/>"""
			else                     -> "<${this::class.java.simpleName.substringBefore("Config")}" +
					""" name="$name" port="$port"/>"""
		})
	}

	override fun build(): String = builder.toString()
}