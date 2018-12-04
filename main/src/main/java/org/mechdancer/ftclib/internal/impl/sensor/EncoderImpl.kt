package org.mechdancer.ftclib.internal.impl.sensor

import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.mechdancer.ftclib.core.structure.monomeric.sensor.Encoder
import org.mechdancer.ftclib.internal.impl.Sensor
import kotlin.math.PI

class EncoderImpl(name: String, enable: Boolean,
                  cpr: Double)
    : Encoder, Sensor<DcMotorEx>(name, enable) {

    constructor(config: Encoder.Config) : this(config.name, config.enable, config.cpr)

    private var offset = .0

    override var position = .0
        private set

    override var speed = .0
        private set

    private var raw = .0

    private val ratio = 2 * PI / cpr

    override fun DcMotorEx.input() {
        raw = currentPosition * ratio
        position = raw - offset
        speed = getVelocity(AngleUnit.DEGREES) * ratio
    }

    override fun DcMotorEx.reset() {
    }

    override fun resetData() {
        reset(.0)
    }

    override fun reset(off: Double) {
        offset = raw - off
    }


    override fun toString(): String = "编码器[$name] | " +
            if (enable) "位置: $position, 速度: $speed" else "关闭"

}