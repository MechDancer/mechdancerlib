package org.mechdancer.ftclib.util

/**
 * 实现对应接口标识着该结构将参与 OpMode 相应流程，会被 OpMode 调用
 */
object OpModeLifecycle {
	/**
	 *参与 OpMode 的 `init()`
	 */
	interface Initialize {
		fun init()
	}

	/**
	 * 参与 OpMode 的 `start()`
	 */
	interface Start {
		fun start()
	}

	/**
	 *参与 OpMode 的 `loop()`
	 */
	interface Run {
		fun run()
	}

	/**
	 *参与 OpMode 的 `stop()`
	 */
	interface Stop {
		fun stop()
	}
}

/**
 * 实现该接口的结构将具有重置能力
 */
interface Resettable {
	fun reset()
}

/**
 * 可自动调用即为参与 [com.qualcomm.robotcore.eventloop.opmode.OpMode.loop]。
 */
typealias AutoCallable = OpModeLifecycle.Run