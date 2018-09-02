package org.mechdancer.ftclib.sensors

import org.mechdancer.ftclib.core.structure.DeviceConfig
import org.mechdancer.ftclib.core.structure.Structure

interface Encoder : Structure {
	fun getPosition(): Double

	fun getSpeed(): Double

	fun reset(off: Double)

	class Config(name: String, enable: Boolean = false, var radians: Double = .0) : DeviceConfig(name, enable)
}
