package org.mechdancer.ftclib.sensors

import org.mechdancer.ftclib.core.structure.DeviceConfig

interface Encoder {
	fun getPosition(): Double

	fun getSpeed(): Double

	fun reset(off: Double)

	class Config(name: String, enable: Boolean = false, var radians: Double = .0) : DeviceConfig(name, enable)

}