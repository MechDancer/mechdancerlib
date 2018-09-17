package org.mechdancer.ftclib.core.structure.monomeric.effector

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

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