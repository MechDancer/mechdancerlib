package org.mechdancer.ftclib.core.structure.monomeric.sensor

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * Color sensor
 */
interface ColorSensor : Structure {

    /**
     * Data
     */
    val colorData: ColorData

    /**
     * Whether to enable led on this sensor
     */
    var enableLed: Boolean

    /**
     * Color data
     */
    data class ColorData(
        val red: Int,
        val green: Int,
        val blue: Int,
        val alpha: Int,
        val argb: Int) {
        override fun toString(): String = "RevSensorValue(Red:$red Blue:$blue Green:$green Alpha:$alpha)"
    }

    /**
     * DSL config
     *
     * @param name name
     * @param enable whether to enable
     */
    class Config(name: String, enable: Boolean = false) : DeviceConfig(name, enable)
}
