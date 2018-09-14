package org.mechdancer.ftclib.core.structure

/**
 * 结构标识
 */
interface Structure {
	val name: String
}

/**
 * 结构
 */
sealed class StructureSealed(override val name: String) : Structure {

	abstract fun run()

	abstract override fun toString(): String
}

/**
 * 单体结构
 */
abstract class MonomericStructure(name: String) : StructureSealed(name)

/**
 * 复合结构
 */
abstract class CompositeStructure(name: String) : StructureSealed(name) {
	/**
	 * 子结构
	 */
	abstract val subStructures: List<Structure>
}