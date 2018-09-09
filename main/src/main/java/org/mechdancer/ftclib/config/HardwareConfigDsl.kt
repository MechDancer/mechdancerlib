package org.mechdancer.ftclib.config

interface HardwareConfigDsl {
	fun start()

	fun finalize() {}

	fun build(): String

}