package org.mechdancer.ftclib.core.structure

/**
 * Dsl builder for [CompositeStructure].
 */
class StructureBuilder(private val structureName: String) {

    internal val subStructures = mutableListOf<Structure>()

    private fun add(structure: Structure) = subStructures.add(structure)

    /**
     * See [Structure.run]
     */
    var action: (structures: List<Structure>) -> Unit = {}

    /**
     * Creates and adds a substructure
     */
    fun subStructure(name: String, block: StructureBuilder.() -> Unit) {
        add(StructureBuilder(name).apply(block).build())
    }

    /**
     * Adds a substructure
     */
    fun subStructure(subStructure: Structure) {
        add(subStructure)
    }

    /**
     * Builds the instance
     */
    fun build() = object : CompositeStructure(structureName) {
        override val subStructures: List<Structure> = ArrayList(this@StructureBuilder.subStructures)
        override fun run() = action(subStructures)

        override fun toString() = "AnonymousStructure[$name]"
    }
}

/**
 * Constructs an anonymous structure
 *
 * @param name name
 * @param block see [StructureBuilder]
 */
inline fun structure(name: String = "Unnamed", block: StructureBuilder.() -> Unit) =
    StructureBuilder(name).apply(block).build()
