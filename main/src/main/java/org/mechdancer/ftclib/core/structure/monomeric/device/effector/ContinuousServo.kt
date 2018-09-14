package org.mechdancer.ftclib.core.structure.monomeric.device.effector

import org.mechdancer.ftclib.core.structure.monomeric.device.DeviceConfig
import org.mechdancer.ftclib.core.structure.Structure

interface ContinuousServo : Structure {
	var power: Double

	class Config(
			name: String,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}