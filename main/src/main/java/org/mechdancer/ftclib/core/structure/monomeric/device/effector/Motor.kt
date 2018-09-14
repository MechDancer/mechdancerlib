package org.mechdancer.ftclib.core.structure.monomeric.device.effector

import org.mechdancer.ftclib.core.structure.monomeric.device.DeviceConfig
import org.mechdancer.ftclib.core.structure.Structure

interface Motor : Structure {
	var power: Double

	var direction: Direction

	class Config(
			name: String,
			enable: Boolean = false,
			var direction: Direction = Direction.FORWARD
	) : DeviceConfig(name, enable)

	enum class Direction {
		FORWARD,
		REVERSE;

		fun reverse(): Direction = when (this) {
			FORWARD -> REVERSE
			REVERSE -> FORWARD
		}
	}
}