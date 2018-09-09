package org.mechdancer.ftclib.config

class LynxModuleConfig(val name: String, val port: Int, private val block: LynxModuleConfig.() -> Unit) : HardwareConfigDsl {

	private val builder = StringBuilder()

	fun servo(name: String, port: Int) {
		builder.append(DeviceConfig.ServoConfig(name, port).create())
	}

	fun matrix12vMotor(name: String, port: Int) {
		builder.append(DeviceConfig.Matrix12vMotorConfig(name, port).create())
	}

	fun motor(name: String, port: Int) {
		builder.append(DeviceConfig.MotorConfig(name, port).create())
	}

	fun lynxEmbeddedImu(name: String, bus: Int, port: Int) {
		builder.append(DeviceConfig.LynxEmbeddedImuConfig(name, bus, port).create())
	}


	override fun start() {
		builder.appendln("""<LynxModule name="$name" port="$port">""")
		block()
	}

	override fun finalize() {
		builder.appendln("</LynxModule>")
	}

	override fun build(): String = builder.toString()
}