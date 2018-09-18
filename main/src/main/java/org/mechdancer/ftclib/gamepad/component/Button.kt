package org.mechdancer.ftclib.gamepad.component

/**
 * 按钮
 */
class Button : IGamePadComponent<Boolean> {

	private var last = false

	/**
	 * 当前状态
	 *
	 * @return 当前按钮状态
	 */
	fun bePressed() = value

	/**
	 * 是否被按下
	 *
	 * @return 是否一直处于按下状态
	 */
	fun isPressing() = !last && bePressed()

	/**
	 * 是否释放
	 *
	 * @return 是否一直处于释放状态
	 */
	fun isReleasing() = last && bePressed()

	override var value: Boolean = false
		set(value) {
			last = field
			field = value
		}
}
