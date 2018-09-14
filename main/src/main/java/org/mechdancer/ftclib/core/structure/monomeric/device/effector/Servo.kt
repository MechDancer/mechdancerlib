package org.mechdancer.ftclib.core.structure.monomeric.device.effector

import org.mechdancer.ftclib.core.structure.monomeric.device.DeviceConfig
import org.mechdancer.ftclib.core.structure.Structure

interface Servo : Structure {
	var position: Double

	var pwmOutput: Boolean

	class Config(
			name: String,
			enable: Boolean = false,
			var origin: Double = .0,
			var ending: Double = .0
	) : DeviceConfig(name, enable)
}