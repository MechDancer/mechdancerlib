package org.mechdancer.ftclib.core.structure.monomeric.effector

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * 连续舵机
 */
interface ContinuousServo : Structure {
	/**
	 * 功率
	 * 范围: [-1, 1]
	 */
	var power: Double

	/**
	 * 是否开启 pwm 输出
	 */
	var pwmOutput: Boolean

	/**
	 * 配置
	 *
	 * @param name 名字
	 * @param enable 是否启用
	 */
	class Config(
			name: String,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}