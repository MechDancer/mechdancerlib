package org.mechdancer.ftclib.core.structure

/**
 * 机器人结构标识
 */
interface Structure {
	/**
	 * 结构名
	 */
	val name: String
}

/**
 * 机器人结构
 */
sealed class StructureSealed(override val name: String) : Structure {

	/**
	 * 结构运行动作
	 */
	abstract fun run()

	abstract override fun toString(): String
}

/**
 * 单体结构
 */
abstract class MonomericStructure(name: String) : StructureSealed(name)

/**
 * 复合结构
 * 与单体结构相比，可具有子结构。
 */
abstract class CompositeStructure(name: String) : StructureSealed(name) {
	/**
	 * 子结构
	 */
	abstract val subStructures: List<Structure>
}