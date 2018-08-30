package org.mechdancer.ftclib.core.structure

import org.mechdancer.ftclib.structures.Chassis

abstract class Robot(config: Config) : AbstractStructure(
		structure(config.name) {}
) {

	internal val devices = takeAll<PackingDevice<*>>()

	abstract val chassis: Chassis

	interface Config {
		val name: String
	}

}
