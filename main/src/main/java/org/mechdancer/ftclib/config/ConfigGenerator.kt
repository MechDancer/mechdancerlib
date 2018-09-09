package org.mechdancer.ftclib.config

fun HardwareConfigDsl.create(): String {
	start()
	finalize()
	return build()
}

fun robotConfig(block: RobotConfig.() -> Unit) = RobotConfig(block).create()
