package org.mechdancer.ftclib.test

import org.junit.Test
import org.mechdancer.ftclib.util.config.component.DeviceConfig
import org.mechdancer.ftclib.util.config.robotConfig

class ConfigTest {
	@Test
	fun testConfig() {

		val xml = robotConfig {
			lynxUsbDevice {
				name = "Expansion Hub Portal 1"
				parentModuleAddress = 3
				serialNumber = "xxxxxx"

				lynxModule("左侧REV", 3) {
					device(DeviceConfig.Matrix12vMotorConfig("左前", 0))
					device(DeviceConfig.Matrix12vMotorConfig("右前", 1))
					device(DeviceConfig.Matrix12vMotorConfig("左后", 2))
					device(DeviceConfig.Matrix12vMotorConfig("右后", 3))
					device(DeviceConfig.LynxEmbeddedImuConfig("惯性左", 0, 0))
				}
				lynxModule("右侧REV", 2) {
					device(DeviceConfig.Matrix12vMotorConfig("肩膀", 0))
					device(DeviceConfig.Matrix12vMotorConfig("右前", 1))
					device(DeviceConfig.Matrix12vMotorConfig("右后", 2))
					device(DeviceConfig.ServoConfig("右杆", 0))
					device(DeviceConfig.ServoConfig("右手", 1))
					device(DeviceConfig.ServoConfig("左手", 2))
					device(DeviceConfig.LynxEmbeddedImuConfig("惯性右", 0, 0))
				}
			}
		}

		println(xml)
	}

}