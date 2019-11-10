package org.mechdancer.ftclib.core.structure.composite.chassis

import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import org.mechdancer.ftclib.internal.impl.effector.MotorImpl
import org.mechdancer.ftclib.util.AutoCallable
import org.mechdancer.ftclib.util.SmartLogger
import org.mechdancer.ftclib.util.warn
import kotlin.math.abs
import kotlin.math.sign

/**
 * Base of chassis
 *
 * Abstracts the generality between chassis — [powers] and [maxPower].
 */
abstract class Chassis(motorsConfig: Array<Pair<String, Motor.Direction>>, enable: Boolean = false)
    : CompositeStructure("null_chassis"), AutoCallable, SmartLogger {

    override val subStructures: List<Motor> =
        motorsConfig.map { MotorImpl(it.first, enable, it.second) }

    open var powers = DoubleArray(motorsConfig.size) { .0 }
        get() = field.standardizeBy(maxPower)
        set(value) {
            if (value.size != field.size)
                warn("Illegal size powers: ${value.size} ≠ ${field.size}")
            else
                field = value
        }

    var maxPower = 1.0
        set(value) {
            if (value !in -1.0..1.0)
                warn("Illegal max power: $maxPower ∉ [-1,1]")
            else
                field = value
        }

    /**
     * Standardizes powers
     *
     * If the input power has a value greater than the input maximum constraint,
     * the maximum is adjusted to the constraint value,
     * and the other values are scaled down.
     *
     * @param maxPower  maximum power constraint ∈ [-1,1]
     */
    private fun DoubleArray.standardizeBy(maxPower: Double) =
        map(::abs).max()!!.let {
            if (it <= abs(maxPower))
                maxPower.sign
            else
                maxPower / it
        }.let {
            DoubleArray(size) { i ->
                this[i] * it
            }
        }

    override fun run() {
        subStructures.forEachIndexed { index: Int, motor: Motor ->
            motor.power = powers[index]
        }
    }

    override fun toString() = name

}
