package org.mechdancer.ftclib.core.structure.composite

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.takeAll
import org.mechdancer.ftclib.internal.impl.sensor.VoltageSensorImpl
import org.mechdancer.ftclib.util.AutoCallable
import org.mechdancer.ftclib.util.Resettable

/**
 * Root of structure tree
 *
 * Calls directly by OpMode.
 *
 * @param name name
 * @param subStruct substructures
 */
abstract class Robot(name: String, vararg subStruct: Structure)
    : AbstractStructure(name, *subStruct, VoltageSensorImpl()), AutoCallable {

    private val rests = takeAll<Resettable>()


    /**
     * Resets the robot
     *
     * Structures marked with [Resettable] will be called.
     */
    open fun reset() {
        rests.forEach { it.reset() }
    }

}
