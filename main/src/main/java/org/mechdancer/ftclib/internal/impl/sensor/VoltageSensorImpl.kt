package org.mechdancer.ftclib.internal.impl.sensor

import com.qualcomm.robotcore.hardware.HardwareMap
import org.mechdancer.ftclib.core.structure.MonomericStructure
import org.mechdancer.ftclib.core.structure.monomeric.sensor.VoltageSensor
import org.mechdancer.ftclib.internal.FtcVoltageSensor

class VoltageSensorImpl(val enable: Boolean) : VoltageSensor, MonomericStructure("voltageSensor") {

    override var voltage: Double = Double.POSITIVE_INFINITY
        private set

    private var sensors: HardwareMap.DeviceMapping<FtcVoltageSensor>? = null

    internal fun bind(hardwareMap: HardwareMap) {
        if (!enable) return
        sensors = hardwareMap.voltageSensor
    }

    internal fun unbind() {
        if (!enable) return
        sensors = null
    }

    override fun run() {
        if (!enable) return
        sensors?.let { voltages ->
            voltage = voltages.minBy {
                if (it.voltage < 0)
                    Double.POSITIVE_INFINITY else it.voltage
            }?.voltage ?: Double.POSITIVE_INFINITY
        }
    }

    override fun toString(): String = "VoltageSensor | Voltage: $voltage V"

}