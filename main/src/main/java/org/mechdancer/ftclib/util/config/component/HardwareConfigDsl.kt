package org.mechdancer.ftclib.util.config.component

internal interface HardwareConfigDsl {
	fun start()

	fun finalize() {}

	fun build(): String

}