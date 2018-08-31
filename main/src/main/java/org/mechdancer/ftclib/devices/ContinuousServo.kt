package org.mechdancer.ftclib.devices

import org.mechdancer.ftclib.core.structure.DeviceConfig

interface ContinuousServo {
	var power: Double

	class Config(
			name: String,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}