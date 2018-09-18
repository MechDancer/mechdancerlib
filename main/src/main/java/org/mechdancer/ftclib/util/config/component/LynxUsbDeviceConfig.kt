package org.mechdancer.ftclib.util.config.component

import org.mechdancer.ftclib.util.config.create

/**
 * Rev 设备配置
 */
class LynxUsbDeviceConfig(private val block: LynxUsbDeviceConfig.() -> Unit) : HardwareConfigDsl {

	private val builder = StringBuilder()

	/**
	 * Rev 设备名
	 */
	var name: String = "Expansion Hub Portal 1"
	/**
	 * 父模块地址
	 */
	var parentModuleAddress: Int = 1
	/**
	 * 序列号
	 */
	var serialNumber: String = ""

	/**
	 * 添加 Rev 模块
	 *
	 * @param name 模块名
	 * @param port 模块端口
	 * @param block Rev 模块配置 DSL 建造者
	 */
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