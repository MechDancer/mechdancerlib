package org.mechdancer.ftclib.core.structure.monomeric.device.sensor

import org.mechdancer.ftclib.core.structure.monomeric.device.DeviceConfig
import org.mechdancer.ftclib.core.structure.Structure

interface Encoder : Structure {
	val position: Double

	val speed:Double

	fun reset(off: Double)

	class Config(name: String, enable: Boolean = false, var radians: Double = .0) : DeviceConfig(name, enable)
}
