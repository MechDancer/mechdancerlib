package org.mechdancer.ftclib.devices

import org.mechdancer.ftclib.core.structure.DeviceConfig

interface Servo {
	var position: Double

	class Config(
			name: String,
			var origin: Double = .0,
			var ending: Double = .0,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}