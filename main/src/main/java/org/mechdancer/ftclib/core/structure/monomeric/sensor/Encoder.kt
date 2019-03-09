package org.mechdancer.ftclib.core.structure.monomeric.sensor

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * Encoder
 */
interface Encoder : Structure {

    /**
     * Current position (rad)
     */
    val position: Double

    /**
     * Current speed (rad/s)
     */
    val speed: Double

    /**
     * Resets this encoder to [off]
     */
    fun reset(off: Double)

    /**
     * DSL config
     *
     * @param name name
     * @param enable whether to enable
     * @param cpr pulses per second
     */
    class Config(
        name: String,
        enable: Boolean = false,
        var cpr: Double = .0) : DeviceConfig(name, enable)
}
