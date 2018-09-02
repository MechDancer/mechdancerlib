package org.mechdancer.ftclib.core.structure

/**
 * 设备配置
 * 每种设备都拥有自己的配置，并继承该类。
 */
open class DeviceConfig(
		open var name: String,
		open var enable: Boolean)
