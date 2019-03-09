package org.mechdancer.ftclib.gamepad.component

import kotlin.math.sqrt

/**
 * Joystick
 */
class Stick : IGamePadComponent<DoubleArray> {

    /**
     * X value
     */
    val x get() = raw[0]
    /**
     * Y value
     */
    val y get() = raw[1]

    /**
     * Angle (rad)
     */
    val theta get() = Math.atan2(y, x)

    /**
     * Radius
     */
    val radius get() = sqrt(x * x + y * y)

    /**
     * Tactile coefficient
     */
    var feel = 1.0

    /**
     * Dead area
     *
     * Values will be set to 0 if it's in [diedArea].
     */
    var diedArea = .0

    //================================================
    private fun mapExpression(x: Double, f: Double) =
        (f / ((1 - f) * (x + 1) + f) - f) / (f - 1)

    private fun nonlinearMap(x: Double, feel: Double, diedArea: Double) = when {
        Math.abs(x) < diedArea -> .0
        feel == 1.0            -> x
        x < 0                  -> mapExpression(x, feel)
        else                   -> -mapExpression(-x, feel)
    }

    override var raw: DoubleArray = doubleArrayOf(.0, .0)
        set(value) {
            field = doubleArrayOf(
                nonlinearMap(value[0], feel, diedArea),
                nonlinearMap(-value[1], feel, diedArea))
        }

}