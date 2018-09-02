package org.mechdancer.ftclib.core.structure

import org.mechdancer.ftclib.structures.Chassis

abstract class Robot(name: String, val chassis: Chassis, vararg subStructs: Structure)
	: AbstractStructure(name, {
	subStructure(chassis)
	subStructs.forEach { subStructure(it) }
}) {
	internal val devices = takeAll<PackingDevice<*>>()

	internal val initialisable = takeAll<OpModeFlow.Initialisable>()
	internal val autoCallable = takeAll<OpModeFlow.AutoCallable>()
	internal val stoppable = takeAll<OpModeFlow.Stoppable>()
}
