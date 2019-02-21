package org.mechdancer.ftclib.internal.impl.sensor

import org.mechdancer.ftclib.core.structure.monomeric.sensor.ColorSensor
import org.mechdancer.ftclib.internal.FtcColorSensor
import org.mechdancer.ftclib.internal.impl.Sensor

class ColorSensorImpl(name: String, enable: Boolean)
    : ColorSensor, Sensor<FtcColorSensor>(name, enable) {

    constructor(config: ColorSensor.Config) : this(config.name, config.enable)


    override var colorData = ColorSensor.ColorData(0, 0, 0, 0, 0)
        private set

    override var enableLed: Boolean = false

    override fun FtcColorSensor.input() {
        colorData = ColorSensor.ColorData(red(), green(), blue(), alpha(), argb())
    }

    override fun FtcColorSensor.config() {
        enableLed(enableLed)
    }

    override fun FtcColorSensor.reset() {

    }

    override fun resetData() {
        colorData = ColorSensor.ColorData(0, 0, 0, 0, 0)
    }


    override fun toString(): String =
        "颜色传感器[$name] | ${if (enable) "颜色值: $colorData" else "关闭"}"


}