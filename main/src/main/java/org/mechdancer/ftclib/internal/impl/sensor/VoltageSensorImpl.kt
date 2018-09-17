package org.mechdancer.ftclib.internal.impl.sensor

import com.qualcomm.robotcore.hardware.HardwareMap
import org.mechdancer.ftclib.core.structure.MonomericStructure
import org.mechdancer.ftclib.core.structure.monomeric.sensor.VoltageSensor
import org.mechdancer.ftclib.internal.FtcVoltageSensor
import kotlin.math.min

class VoltageSensorImpl internal constructor()
	: VoltageSensor, MonomericStructure("voltageSensor") {

	override var voltage: Double = Double.POSITIVE_INFINITY
		private set

	private var sensors: HardwareMap.DeviceMapping<FtcVoltageSensor>? = null

	internal fun bind(hardwareMap: HardwareMap) {
		sensors = hardwareMap.voltageSensor
	}

	internal fun unbind() {
		sensors = null
	}

	override fun run() {
		var result = Double.POSITIVE_INFINITY
		sensors?.let { voltages ->
			voltages.asSequence()
					.filter { it.voltage > 0 }
					.map { min(result, voltage) }
					.forEach { result = it }
		}
	}

	override fun toString(): String = "电压传感器 | 电压: ${voltage.toInt()} V"

}