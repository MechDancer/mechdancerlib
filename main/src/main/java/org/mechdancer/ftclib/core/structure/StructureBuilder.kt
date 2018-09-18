package org.mechdancer.ftclib.core.structure

/**
 * 使用 DSL 建立匿名复合结构
 * [CompositeStructure]
 */
class StructureBuilder(private val structureName: String) {

	private val _subStructures = mutableListOf<Structure>()

	private fun add(structure: Structure) = _subStructures.add(structure)

	/**
	 * 结构运行动作
	 */
	var action: (structures: List<Structure>) -> Unit = {}

	/**
	 * 添加子结构
	 *
	 * @param name 子结构名
	 * @param block 子结构 DSL 建造者
	 */
	fun subStructure(name: String, block: StructureBuilder.() -> Unit) {
		add(StructureBuilder(name).apply(block).build())
	}

	/**
	 * 添加子结构
	 *
	 * @param subStructure 子结构
	 */
	fun subStructure(subStructure: Structure) {
		add(subStructure)
	}

	/**
	 * 建造
	 */
	fun build() = object : CompositeStructure(structureName) {
		override val subStructures: List<Structure> = ArrayList(_subStructures)
		override fun run() = action(subStructures)

		override fun toString() = "AnonymousStructure[$name]"
	}
}

/**
 * 构造匿名 Structure
 *
 * @param name 结构名
 * @param block 复合结构 DSL 建造者
 */
inline fun structure(name: String = "Unnamed", block: StructureBuilder.() -> Unit) =
		StructureBuilder(name).apply(block).build()
