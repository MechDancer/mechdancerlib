package org.mechdancer.ftclib.gamepad.component

import kotlin.math.sqrt

class Stick : IGamePadComponent<DoubleArray> {

	val x get() = value[0]
	val y get() = value[1]

	val theta get() = Math.atan2(y, x)
	val radius get() = sqrt(x * x + y * y)

	var feel = 1.0
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

	override var value: DoubleArray = doubleArrayOf(.0, .0)
		set(value) {
			field[0] = nonlinearMap(value[0], feel, diedArea)
			field[1] = nonlinearMap(-value[1], feel, diedArea)
		}

}