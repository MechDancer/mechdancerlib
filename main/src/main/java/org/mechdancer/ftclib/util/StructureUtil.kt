package org.mechdancer.ftclib.util

/**
 * 其中接口标识参与 OpMode 标准状态流程，会被 OpMode 调用
 */
object OpModeFlow {
	/**
	 *参与 OpMode 的 `init()`
	 */
	interface Initialisable {
		fun init()
	}

	/**
	 *参与 OpMode 的 `loop()`
	 */
	interface AutoCallable {
		fun run()
	}

	/**
	 *参与 OpMode 的 `stop()`
	 */
	interface Stoppable {
		fun stop()
	}
}