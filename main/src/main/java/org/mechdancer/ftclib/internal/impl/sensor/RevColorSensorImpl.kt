package org.mechdancer.ftclib.internal.impl.sensor

import com.qualcomm.robotcore.hardware.ColorSensor
import org.mechdancer.ftclib.core.structure.monomeric.sensor.RevColorSensor
import org.mechdancer.ftclib.internal.impl.Sensor

class RevColorSensorImpl(name: String, enable: Boolean)
    : RevColorSensor, Sensor<ColorSensor>(name, enable) {

    constructor(config: RevColorSensor.Config) : this(config.name, config.enable)


    override var colorData = RevColorSensor.ColorData(0, 0, 0, 0, 0)
        private set

    override var enableLed: Boolean = false

    override fun ColorSensor.input() {
        colorData = RevColorSensor.ColorData(red(), green(), blue(), alpha(), argb())
    }

    override fun ColorSensor.config() {
        enableLed(enableLed)
    }

    override fun ColorSensor.reset() {

    }

    override fun resetData() {
        colorData = RevColorSensor.ColorData(0, 0, 0, 0, 0)
    }


    override fun toString(): String =
            "Rev颜色传感器[$name] | ${if (enable) "颜色值: $colorData" else "关闭"}"


}