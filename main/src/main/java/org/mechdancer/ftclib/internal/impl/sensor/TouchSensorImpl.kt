package org.mechdancer.ftclib.internal.impl.sensor

import org.mechdancer.ftclib.core.structure.monomeric.sensor.TouchSensor
import org.mechdancer.ftclib.internal.FtcTouchSensor
import org.mechdancer.ftclib.internal.impl.Sensor

class TouchSensorImpl(name: String, enable: Boolean)
    : TouchSensor, Sensor<FtcTouchSensor>(name, enable) {

    constructor(config: TouchSensor.Config) : this(config.name, config.enable)

    private var last = false

    private var raw = false

    override var force: Double = .0
        private set

    override fun bePressed(): Boolean = raw

    override fun isPressing(): Boolean = !last && bePressed()

    override fun isReleasing(): Boolean = last && !bePressed()

    override fun toString(): String = "TouchSensor[$name] | BePressed: $raw"

    override fun resetData() {
        last = false
        raw = false
        force = .0
    }

    override fun FtcTouchSensor.input() {
        force = value
        last = raw
        raw = isPressed
    }

    override fun FtcTouchSensor.reset() {
    }
}