package org.mechdancer.ftclib.util.config.component

import org.mechdancer.ftclib.util.config.create

class RobotConfig(private val block: RobotConfig.() -> Unit) : HardwareConfigDsl {

    private val builder = StringBuilder()

    /**
     * 添加 Rev 设备
     *
     * @param block Rev 设备配置 DSL 建造者
     */
    fun lynxUsbDevice(block: LynxUsbDeviceConfig.() -> Unit) {
        builder.append(LynxUsbDeviceConfig(block).create())
    }

    override fun start() {
        builder.apply {
            appendln("""<?xml version="1.0" encoding="utf-8" standalone="yes"?>""")
            appendln("""<Robot type="FirstInspires-FTC">""")
            block()
        }
    }

    override fun finalize() {
        builder.appendln("</Robot>")
    }

    override fun build(): String = builder.toString()
}