package org.mechdancer.ftclib.core.structure

/**
 * Structure
 *
 * It is an specific definition of a robot part,
 * like a rocker arm or a chassis.
 * Once it is attached to robot as substructure,
 * it will has a similar life-cycle (see [org.mechdancer.ftclib.util.OpModeLifecycle]).
 *
 * It has a name, can be to string.
 * [run] function defines what this structure should do when robot is running.
 */
interface Structure {

    /**
     * Name of structure
     */
    val name: String

    /**
     * Action when running
     */
    fun run()

    override fun toString(): String
}


/**
 * Monomeric structure
 */
abstract class MonomericStructure(override val name: String) : Structure

/**
 * Composite structure
 *
 * The only difference between [CompositeStructure] and [MonomericStructure]
 * is that a composite structure can have substructures attached to it.
 */
abstract class CompositeStructure(override val name: String) : Structure {
    /**
     * SubStructures attached to it
     */
    abstract val subStructures: List<Structure>
}