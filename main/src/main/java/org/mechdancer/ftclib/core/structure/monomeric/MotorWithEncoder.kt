package org.mechdancer.ftclib.core.structure.monomeric

import org.mechdancer.filters.signalAndSystem.PID
import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import org.mechdancer.ftclib.core.structure.monomeric.sensor.Encoder
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * 电机编码器组合
 * 具有电机和编码器一切性质。其不应具有多态性，但为约定行为，实现 [Motor], [Encoder]。
 * 在拥有编码器的同时为电机提供了闭环功能。
 */
interface MotorWithEncoder : Motor, Encoder, Structure {
	/**
	 * 电机模式
	 * [Mode]
	 */
	var mode: Mode

	/**
	 * 目标速度
	 * 在 [mode] 为 [Mode.SPEED_CLOSE_LOOP] 时有效
	 */
	var targetSpeed: Double

	/**
	 * 目标位置
	 * 在 [mode] 为 [Mode.POSITION_CLOSE_LOOP] 时有效
	 */
	var targetPosition: Double

	/**
	 * 锁定电机
	 */
	fun lock()

	/**
	 * 电机模式枚举
	 */
	enum class Mode {
		/**
		 * 闭速度环
		 */
		SPEED_CLOSE_LOOP,
		/**
		 * 开环
		 */
		OPEN_LOOP,
		/**
		 * 闭位置环
		 */
		POSITION_CLOSE_LOOP,
		/**
		 * 锁定
		 * 内部状态
		 */
		LOCK,
		/**
		 * 停止
		 */
		STOP
	}

	/**
	 * 配置
	 *
	 * @param name 名字
	 * @param enable 是否启用
	 * @param radians 总弧度
	 * @param direction 电机方向
	 * @param pidPosition 位置环 pid 参数 [PID]
	 * @param pidSpeed 速度环 pid 参数 [PID]
	 */
	class Config(name: String,
	             enable: Boolean = false,
	             var radians: Double = .0,
	             var direction: Motor.Direction = Motor.Direction.FORWARD,
	             var pidPosition: PID = PID(1.0, .0, .0, .0, .0),
	             var pidSpeed: PID = PID(1.0, .0, .0, .0, .0)) : DeviceConfig(name, enable)
}
