package org.mechdancer.ftclib.core.structure.monomeric.sensor

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

interface Encoder : Structure {
	val position: Double

	val speed:Double

	fun reset(off: Double)

	class Config(name: String, enable: Boolean = false, var radians: Double = .0) : DeviceConfig(name, enable)
}
