package org.mechdancer.ftclib.core.structure.composite

import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.StructureBuilder
import org.mechdancer.ftclib.core.structure.injector.StructureInjector

/**
 * Abstract structure
 *
 * Uses an existent structure as its delegate,
 * in order to create a type for that anonymous structure.
 */
@Suppress("LeakingThis")
abstract class AbstractStructure(name: String = "Unnamed", block: StructureBuilder.() -> Unit = {}) : CompositeStructure(name) {


    constructor(name: String, vararg subStructures: Structure) : this(name, { subStructures.forEach { subStructure(it) } })


    private val delegate = StructureBuilder(name).apply(block)

    final override val subStructures: List<Structure> = delegate.subStructures

    init {
        StructureInjector.inject(this)
    }

    override fun run() {
        delegate.action(subStructures)
    }

    override fun toString(): String {
        val structureName = if (name == "Unnamed") "" else "[$name]"
        return "${javaClass.simpleName}$structureName"
    }
}