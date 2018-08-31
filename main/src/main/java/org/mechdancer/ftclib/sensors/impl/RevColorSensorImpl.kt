package org.mechdancer.ftclib.sensors.impl

import com.qualcomm.robotcore.hardware.ColorSensor
import org.mechdancer.ftclib.core.structure.Sensor
import org.mechdancer.ftclib.sensors.RevColorSensor

class RevColorSensorImpl(name: String, enable: Boolean)
	: RevColorSensor, Sensor<ColorSensor>(name, enable) {

	constructor(config: RevColorSensor.Config) : this(config.name, config.enable)


	private val colorData = RevColorSensor.ColorData(0, 0, 0, 0)
	private var isEnableLed: Boolean = false

	override fun ColorSensor.input() {
		with(colorData) {
			red = red()
			green = green()
			alpha = alpha()
			argb = argb()
		}
	}

	override fun ColorSensor.config() {
		enableLed(isEnableLed)
	}

	override fun ColorSensor.reset() {

	}

	override fun resetData() {
		with(colorData) {
			red = 0
			green = 0
			alpha = 0
			argb = 0
		}
	}

	override fun enableLed(boolean: Boolean) {
		isEnableLed = true
	}

	override fun getColorData(): RevColorSensor.ColorData = colorData

	override fun toString(): String =
			"Rev颜色传感器[$name] | ${if (enable) "颜色值: $colorData" else "关闭"}"


}