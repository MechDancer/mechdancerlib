package org.mechdancer.ftclib.gamepad.components

class Button : IGamePadComponent<Boolean> {

	private var last = false

	fun bePressed() = value

	fun isPressing() = !last && bePressed()

	fun isReleasing() = last && bePressed()

	override var value: Boolean = false
		set(value) {
			last = field
			field = value
		}
}
