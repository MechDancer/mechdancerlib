package org.mechdancer.ftclib.devices

import org.mechdancer.ftclib.core.structure.DeviceConfig
import org.mechdancer.ftclib.core.structure.Structure

interface ContinuousServo : Structure {
	var power: Double

	class Config(
			name: String,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}