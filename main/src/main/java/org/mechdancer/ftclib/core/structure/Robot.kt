package org.mechdancer.ftclib.core.structure

import org.mechdancer.ftclib.structures.impl.ChassisImpl

abstract class Robot(config: Config) : AbstractStructure(
		structure(config.name) {}
) {

	internal val devices = takeAll<PackingDevice<*>>()

	abstract val chassis: ChassisImpl

	interface Config {
		val name: String
	}

}
