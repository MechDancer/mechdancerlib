package org.mechdancer.ftclib.devices

import org.mechdancer.ftclib.core.structure.DeviceConfig
import org.mechdancer.ftclib.core.structure.Structure

interface Servo : Structure {
	var position: Double

	class Config(
			name: String,
			var origin: Double = .0,
			var ending: Double = .0,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}