package org.mechdancer.ftclib.core.structure.monomeric.effector

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

interface ContinuousServo : Structure {
	var power: Double

	var pwmOutput: Boolean

	class Config(
			name: String,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}