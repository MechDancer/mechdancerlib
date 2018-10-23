package org.mechdancer.ftclib.core.structure.monomeric.effector

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

interface Motor : Structure {
	/**
	 * 功率
	 * 范围: [-1, 1]
	 */
	var power: Double

	/**
	 * 电机方向
	 */
	var direction: Direction

	/**
	 * 配置
	 *
	 * @param name 名字
	 * @param enable 是否启用
	 */
	class Config(
		name: String,
		enable: Boolean = false,
		var direction: Direction = Direction.FORWARD
	) : DeviceConfig(name, enable)

	/**
	 * 电机方向枚举
	 */
	enum class Direction(internal val sign: Int) {
		/**
		 * 正向
		 * 逆时针
		 */
		FORWARD(+1),
		/**
		 * 反向
		 * 顺时针
		 */
		REVERSE(-1);

		/**
		 * 求当前方向的逆
		 */
		fun reverse(): Direction = when (this) {
			FORWARD -> REVERSE
			REVERSE -> FORWARD
		}
	}
}