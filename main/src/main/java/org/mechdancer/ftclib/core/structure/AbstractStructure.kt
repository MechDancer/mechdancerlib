package org.mechdancer.ftclib.core.structure

import org.mechdancer.ftclib.core.structure.injector.StructureInjector

/**
 * 用于构造非匿名 Structure
 * 将一个现有的 structure 作为代理
 * @param structure 匿名 structure
 */
abstract class AbstractStructure(structure: CompositeStructure) : CompositeStructure by structure {
	/**
	 * 建立全新的 Structure
	 */
	constructor() : this(structure { })

	/**
	 * 省略 [structure] 的语法糖
	 */
	constructor(block: StructureBuilder.() -> Unit) : this(structure(block = block))

	/**
	 * 省略 [structure] 的语法糖
	 */
	constructor(name: String, block: StructureBuilder.() -> Unit) : this(structure(name, block))

	init {
		@Suppress("LeakingThis")
		StructureInjector.inject(this)
	}

	override fun toString(): String {
		val structureName = if (name == "Unnamed") "" else "[$name]"
		return "${javaClass.simpleName}$structureName"
	}
}