package org.mechdancer.ftclib.util.config.component

import org.mechdancer.ftclib.util.config.create

/**
 * Rev 模块配置
 */
class LynxModuleConfig(val name: String, val port: Int, private val block: LynxModuleConfig.() -> Unit) : HardwareConfigDsl {

    private val builder = StringBuilder()


    fun device(deviceConfig: DeviceConfig) {
        builder.append(deviceConfig.create())
    }

//	/**
//	 * 添加舵机
//	 *
//	 * @param name 名字
//	 * @param port 端口
//	 */
//	fun servo(name: String, port: Int) {
//		builder.append(DeviceConfig.ServoConfig(name, port).create())
//	}
//
//	/**
//	 * 添加 Matrix12V 电机
//	 *
//	 * @param name 名字
//	 * @param port 端口
//	 */
//	fun matrix12vMotor(name: String, port: Int) {
//		builder.append(DeviceConfig.Matrix12vMotorConfig(name, port).create())
//	}
//
//	/**
//	 * 添加电机
//	 *
//	 * @param name 名字
//	 * @param port 端口
//	 */
//	fun motor(name: String, port: Int) {
//		builder.append(DeviceConfig.MotorConfig(name, port).create())
//	}
//
//	/**
//	 * 添加内置 IMU 模块
//	 *
//	 * @param name 名字
//	 * @param bus  总线
//	 * @param port 端口
//	 */
//	fun lynxEmbeddedImu(name: String, bus: Int, port: Int) {
//		builder.append(DeviceConfig.LynxEmbeddedImuConfig(name, bus, port).create())
//	}


    override fun start() {
        builder.appendln("""<LynxModule name="$name" port="$port">""")
        block()
    }

    override fun finalize() {
        builder.appendln("</LynxModule>")
    }

    override fun build(): String = builder.toString()
}