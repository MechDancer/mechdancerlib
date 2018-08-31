package org.mechdancer.ftclib.sensors

import com.qualcomm.robotcore.hardware.ColorSensor
import org.mechdancer.ftclib.core.structure.DeviceConfig
import org.mechdancer.ftclib.core.structure.Sensor

class RevColorSensor(name: String, enable: Boolean)
	: Sensor<ColorSensor>(name, enable) {

	constructor(config: Config) : this(config.name, config.enable)


	data class ColorSensorData(var red: Int, var green: Int, var alpha: Int, var argb: Int) {
		override fun toString(): String = "REVSensorValue(Red:$red,Green:$green,Alpha:$alpha)"
	}

	val colorValue = ColorSensorData(0, 0, 0, 0)
	var enableLed: Boolean = false

	override fun ColorSensor.input() {
		with(colorValue) {
			red = red()
			green = green()
			alpha = alpha()
			argb = argb()
		}
	}

	override fun ColorSensor.config() {
		enableLed(enableLed)
	}

	override fun ColorSensor.reset() {

	}

	override fun resetData() {
		with(colorValue) {
			red = 0
			green = 0
			alpha = 0
			argb = 0
		}
	}

	override fun toString(): String =
			"Rev颜色传感器[$name] | ${if (enable) "颜色值: $colorValue" else "关闭"}"

	class Config(name: String, enable: Boolean = false) : DeviceConfig(name, enable)


}