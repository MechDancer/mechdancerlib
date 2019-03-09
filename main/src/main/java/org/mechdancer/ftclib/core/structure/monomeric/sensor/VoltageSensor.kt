package org.mechdancer.ftclib.core.structure.monomeric.sensor

import org.mechdancer.ftclib.core.structure.Structure

/**
 * Voltage sensor
 *
 * Can't be constructed, can be inject by using [org.mechdancer.ftclib.core.structure.injector.Inject]
 * in [org.mechdancer.ftclib.core.structure.composite.Robot].
 */
interface VoltageSensor : Structure {

    /**
     * voltage
     */
    val voltage: Double

}