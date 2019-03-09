package org.mechdancer.ftclib.core.structure.monomeric.sensor

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * Touch sensor
 */
interface TouchSensor : Structure {

    /**
     * Force
     *
     * It will be `0` or `1` forever if
     * this sensor doesn't support.
     */
    val force: Double

    /**
     * Returns the current state of the button
     */
    fun bePressed(): Boolean

    /**
     * Returns true when the first time the button be pressed
     */
    fun isPressing(): Boolean

    /**
     * Returns true when the first time the button be released
     */
    fun isReleasing(): Boolean


    /**
     * DSL config
     *
     * @param name name
     * @param enable whether to enable
     */
    class Config(name: String, enable: Boolean = false) : DeviceConfig(name, enable)
}