package org.mechdancer.ftclib.core.structure

/**
 * 使用 DSL 建立匿名 [CompositeStructure]
 */
class StructureBuilder(private val structureName: String) {

	private val _subStructures = mutableListOf<Structure>()

	private fun add(structure: Structure) = _subStructures.add(structure)

	var action: (structures: List<Structure>) -> Unit = {}

	fun subStructure(name: String, block: StructureBuilder.() -> Unit) {
		add(StructureBuilder(name).apply(block).build())
	}

	fun subStructure(subStructure: Structure) {
		add(subStructure)
	}


	fun build() = object : CompositeStructure(structureName) {
		override val subStructures: List<Structure> = ArrayList(_subStructures)
		override fun run() = action(subStructures)

		override fun toString() = "AnonymousStructure[$name]"
	}
}

/**
 * 构造匿名 Structure
 */
inline fun structure(name: String = "Unnamed", block: StructureBuilder.() -> Unit) =
		StructureBuilder(name).apply(block).build()
