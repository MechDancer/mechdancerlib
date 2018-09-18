package org.mechdancer.ftclib.core.structure.monomeric.sensor

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * 编码器
 */
interface Encoder : Structure {
	/**
	 * 位置
	 * 弧度
	 */
	val position: Double

	/**
	 * 速度
	 */
	val speed: Double

	/**
	 * 重置
	 */
	fun reset(off: Double)

	/**
	 * 配置
	 *
	 * @param name 名字
	 * @param enable 是否启用
	 * @param radians 总弧度
	 */
	class Config(
			name: String,
			enable: Boolean = false,
			var radians: Double = .0) : DeviceConfig(name, enable)
}
