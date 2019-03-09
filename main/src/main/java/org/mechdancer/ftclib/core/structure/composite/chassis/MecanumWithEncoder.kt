package org.mechdancer.ftclib.core.structure.composite.chassis

import org.mechdancer.ftclib.core.structure.monomeric.MotorWithEncoder
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import org.mechdancer.ftclib.internal.algorithm.PID
import org.mechdancer.ftclib.internal.impl.MotorWithEncoderImpl

/**
 * Mecanum chassis with encoder
 */
open class MecanumWithEncoder(name: String,
                              motorCpr: Double,
                              enable: Boolean,
                              lfMotorDirection: Motor.Direction,
                              lbMotorDirection: Motor.Direction,
                              rfMotorDirection: Motor.Direction,
                              rbMotorDirection: Motor.Direction,
                              lfMotorName: String = "LF",
                              lbMotorName: String = "LB",
                              rfMotorName: String = "RF",
                              rbMotorName: String = "RB")
    : Mecanum(name, enable,
    lfMotorDirection,
    lbMotorDirection,
    rfMotorDirection,
    rbMotorDirection,
    lfMotorName,
    lbMotorName,
    rfMotorName,
    rbMotorName) {

    final override val subStructures: List<MotorWithEncoder> =
        arrayOf(lfMotorName, lbMotorName, rfMotorName, rbMotorName)
            .map {
                MotorWithEncoderImpl(it, enable, motorCpr,
                    when (it) {
                        lfMotorName -> lfMotorDirection
                        lbMotorName -> lbMotorDirection
                        rfMotorName -> rfMotorDirection
                        rbMotorName -> rbMotorDirection
                        else        -> throw RuntimeException()
                    }, PID.zero(), PID.zero())
            }

    var rawPositions = DoubleArray(4)
        private set

    override fun run() {
        super.run()
        rawPositions = subStructures.map { it.position }.toDoubleArray()
    }

}