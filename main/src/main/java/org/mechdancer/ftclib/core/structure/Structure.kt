package org.mechdancer.ftclib.core.structure

/**
 * 机器人结构
 */
interface Structure {
    /**
     * 结构名
     */
    val name: String

    /**
     * 结构运行动作
     */
    fun run()

    override fun toString(): String
}


/**
 * 单体结构
 */
abstract class MonomericStructure(override val name: String) : Structure

/**
 * 复合结构
 * 与单体结构相比，可具有子结构。
 */
abstract class CompositeStructure(override val name: String) : Structure {
    /**
     * 子结构
     */
    abstract val subStructures: List<Structure>
}