package org.mechdancer.ftclib.core.structure

import org.mechdancer.ftclib.structures.Chassis

abstract class Robot(name: String, vararg subStructs: Structure)
	: AbstractStructure(name, {
	subStructs.forEach { subStructure(it) }
}) {

	internal val devices = takeAll<PackingDevice<*>>()

	abstract val chassis: Chassis

}
