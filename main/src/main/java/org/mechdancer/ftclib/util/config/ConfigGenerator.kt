package org.mechdancer.ftclib.util.config

import org.mechdancer.ftclib.util.config.component.HardwareConfigDsl
import org.mechdancer.ftclib.util.config.component.RobotConfig

internal fun HardwareConfigDsl.create(): String {
	start()
	finalize()
	return build()
}

fun robotConfig(block: RobotConfig.() -> Unit) = RobotConfig(block).create()
