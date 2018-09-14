package org.mechdancer.ftclib.core.structure.monomeric.device.sensor

import org.mechdancer.ftclib.core.structure.monomeric.device.DeviceConfig
import org.mechdancer.ftclib.core.structure.Structure

interface RevColorSensor : Structure {
	val colorData: ColorData

	var enableLed:Boolean

	data class ColorData(val red: Int, val green: Int,val blue:Int, val alpha: Int, val argb: Int) {
		override fun toString(): String = "RevSensorValue(Red:$red Blue:$blue Green:$green Alpha:$alpha)"
	}

	class Config(name: String, enable: Boolean = false) : DeviceConfig(name, enable)
}
