package org.mechdancer.ftclib.devices

import org.mechdancer.ftclib.core.structure.DeviceConfig
import org.mechdancer.ftclib.core.structure.Structure

interface Servo : Structure {
	var position: Double

	class Config(
			name: String,
			enable: Boolean = false,
			var origin: Double = .0,
			var ending: Double = .0
	) : DeviceConfig(name, enable)
}