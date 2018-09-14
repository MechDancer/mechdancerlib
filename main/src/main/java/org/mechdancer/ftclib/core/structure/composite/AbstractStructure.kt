package org.mechdancer.ftclib.core.structure.composite

import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.StructureBuilder
import org.mechdancer.ftclib.core.structure.injector.StructureInjector
import org.mechdancer.ftclib.core.structure.structure

/**
 * 用于构造非匿名 Structure
 * 将一个现有的 structure 作为代理
 * @param delegate 匿名 structure
 */
@Suppress("LeakingThis")
abstract class AbstractStructure(private val delegate: CompositeStructure) : CompositeStructure(delegate.name) {
	/**
	 * 建立全新的 Structure
	 */
	constructor() : this(structure { })

	/**
	 * 省略 [structure] {...} 的语法糖
	 */
	constructor(block: StructureBuilder.() -> Unit) : this(structure(block = block))

	/**
	 * 省略 [structure] {...} 的语法糖
	 */
	constructor(name: String, block: StructureBuilder.() -> Unit) : this(structure(name, block))

	/**
	 * 带名，可将子结构直接加入构造器
	 */
	constructor(name: String, vararg subStructs: Structure) : this(structure(name) { subStructs.forEach { subStructure(it) } })

	/**
	 * 可将子结构直接加入构造器
	 */
	constructor(vararg subStructs: Structure) : this({ subStructs.forEach { subStructure(it) } })

	final override val subStructures: List<Structure> = delegate.subStructures

	init {
		StructureInjector.inject(this)
	}

	override fun run() {
		delegate.run()
	}

	override fun toString(): String {
		val structureName = if (name == "Unnamed") "" else "[$name]"
		return "${javaClass.simpleName}$structureName"
	}
}