package org.mechdancer.ftclib.internal.impl

/**
 * 设备配置
 * 每种设备都拥有自己的配置，并继承该类。
 */
abstract class DeviceConfig(
		var name: String,
		var enable: Boolean)
