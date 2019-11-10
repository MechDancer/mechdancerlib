package org.mechdancer.ftclib.core.structure.composite.chassis

import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import kotlin.math.cos
import kotlin.math.sin

/**
 * Omnidirectinal chassis
 *
 * Provides polar, tank and descartes control.
 */
abstract class Omnidirectinal
(motorsConfig: Array<Pair<String, Motor.Direction>>, enable: Boolean = false)
    : Chassis(motorsConfig, enable) {

    /**
     * Descartes control parameters
     */
    data class Descartes(var x: Double,
                         var y: Double,
                         var w: Double) {
        operator fun times(other: Descartes) =
            Descartes(x * other.x, y * other.y, w * other.w)
    }

    /**
     * Polar control parameters
     */
    data class Polar(var rho: Double = .0,
                     var theta: Double = .0,
                     var omega: Double = .0) {
        val block: Descartes.() -> Unit = {
            x = rho * cos(theta)
            y = rho * sin(theta)
            w = omega
        }
    }

    /**
     * Tank control parameters
     */
    data class TankMode(var left: Double = .0,
                        var right: Double = .0,
                        var horizon: Double = .0) {
        val block: Descartes.() -> Unit = {
            x = left + right
            y = horizon
            w = left - right
        }
    }

    @PublishedApi
    internal val descartes = Descartes(.0, .0, .0)
    @PublishedApi
    internal val weights = Descartes(1.0, 1.0, 1.0)

    @PublishedApi
    internal val polar by lazy { Polar() }
    @PublishedApi
    internal val tank by lazy { TankMode() }

    /**
     * Descartes control
     */
    inline fun descartes(block: Descartes.() -> Unit) =
        descartes.run(block)

    /**
     * Polar control
     */
    inline fun polar(block: Polar.() -> Unit) =
        descartes.run(polar.apply(block).block)

    /**
     * Tank control
     */
    inline fun tank(block: TankMode.() -> Unit) =
        descartes.run(tank.apply(block).block)

    /**
     * Sets wights
     */
    inline fun weights(block: Descartes.() -> Unit) =
        weights.run(block)

    /**
     * Whether to open advanced control mode
     *
     * In this mode, calculated powers will not be assigned to motors.
     */
    var advancedControlMode = false

    /**
     * Transforms descartes parameters to powers of each wheel.
     */
    protected abstract fun Descartes.transform(): DoubleArray

    override fun run() {
        if (!advancedControlMode)
            powers = (weights * descartes).transform()
        super.run()
    }
}