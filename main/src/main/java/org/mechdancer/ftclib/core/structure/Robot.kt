package org.mechdancer.ftclib.core.structure

import org.mechdancer.ftclib.structures.Chassis

abstract class Robot(name: String, vararg subStructs: Structure)
	: AbstractStructure(name, {
	subStructs.forEach { subStructure(it) }
}) {

	internal val devices = takeAll<PackingDevice<*>>()

	internal val initialisable = takeAll<OpModeFlow.Initialisable>()
	internal val autoCallable = takeAll<OpModeFlow.AutoCallable>()
	internal val stoppable = takeAll<OpModeFlow.Stoppable>()

	abstract val chassis: Chassis

}
