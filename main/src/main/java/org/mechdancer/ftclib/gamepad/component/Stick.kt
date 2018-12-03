package org.mechdancer.ftclib.gamepad.component

import kotlin.math.sqrt

/**
 * 摇杆
 */
class Stick : IGamePadComponent<DoubleArray> {

    /**
     * x 值
     */
    val x get() = raw[0]
    /**
     * y 值
     */
    val y get() = raw[1]

    /**
     * 角度
     */
    val theta get() = Math.atan2(y, x)

    /**
     * 半径
     */
    val radius get() = sqrt(x * x + y * y)

    /**
     * 手感系数
     */
    var feel = 1.0

    /**
     * 死区
     * 位于死区内时会阈值化为 0
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
            field[0] = nonlinearMap(value[0], feel, diedArea)
            field[1] = nonlinearMap(-value[1], feel, diedArea)
        }

}