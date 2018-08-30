package org.mechdancer.ftclib.core.structure

import org.mechdancer.ftclib.core.structure.injector.StructureInjector

/**
 * 用于构造非匿名Structure
 */
abstract class AbstractStructure(structure: Structure) : Structure by structure {

	constructor() : this(structure { })
	constructor(block: StructureBuilder.() -> Unit) : this(structure(block = block))

	init {
		@Suppress("LeakingThis")
		StructureInjector.inject(this)
	}

	override fun toString(): String {
		val structureName = if (name == "Unnamed") "" else "[$name]"
		return "${javaClass.simpleName}$structureName"
	}
}