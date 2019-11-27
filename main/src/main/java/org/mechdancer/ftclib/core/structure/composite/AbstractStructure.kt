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
abstract class AbstractStructure(name: String = "Unnamed", recursiveInject: Boolean = true, block: StructureBuilder.() -> Unit = {}) : CompositeStructure(name) {


    constructor(name: String, vararg subStructures: Structure) : this(name, true, { subStructures.forEach { subStructure(it) } })
    constructor(name: String, recursiveInject: Boolean = true, vararg subStructures: Structure) : this(name, recursiveInject, { subStructures.forEach { subStructure(it) } })
    constructor(name: String, block: StructureBuilder.() -> Unit = {}) : this(name, true, block)


    private val delegate = StructureBuilder(name).apply(block)

    final override val subStructures: List<Structure> = delegate.subStructures

    init {
        StructureInjector.inject(this, recursiveInject)
    }

    override fun run() {
        delegate.action(subStructures)
    }

    override fun toString(): String {
        val structureName = if (name == "Unnamed") "" else "[$name]"
        return "${javaClass.simpleName}$structureName"
    }
}