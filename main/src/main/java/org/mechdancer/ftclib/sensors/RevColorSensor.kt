package org.mechdancer.ftclib.sensors

import org.mechdancer.ftclib.core.structure.DeviceConfig

interface RevColorSensor {
	fun getColorData(): ColorData

	fun enableLed(boolean: Boolean)

	data class ColorData(var red: Int, var green: Int, var alpha: Int, var argb: Int) {
		override fun toString(): String = "RevSensorValue(Red:$red,Green:$green,Alpha:$alpha)"
	}

	class Config(name: String, enable: Boolean = false) : DeviceConfig(name, enable)

}