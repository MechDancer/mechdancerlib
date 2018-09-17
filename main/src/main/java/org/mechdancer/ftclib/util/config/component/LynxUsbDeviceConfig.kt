package org.mechdancer.ftclib.util.config.component

import org.mechdancer.ftclib.util.config.create

class LynxUsbDeviceConfig(private val block: LynxUsbDeviceConfig.() -> Unit) : HardwareConfigDsl {

	private val builder = StringBuilder()


	var name: String = "Expansion Hub Portal 1"
	var parentModuleAddress: Int = 1
	var serialNumber: String = ""


	fun lynxModule(name: String, port: Int, block: LynxModuleConfig.() -> Unit) {
		builder.append(LynxModuleConfig(name, port, block).create())
	}


	override fun start() {
		block()
		builder.insert(0, """<LynxUsbDevice name="$name" parentModuleAddress="$parentModuleAddress" serialNumber="$serialNumber">
			|
		""".trimMargin())
	}

	override fun finalize() {
		builder.appendln("</LynxUsbDevice>")
	}

	override fun build(): String = builder.toString()
}