package org.mechdancer.ftclib.test

import org.junit.Test
import org.mechdancer.ftclib.config.robotConfig

class ConfigTest {
	@Test
	fun testConfig() {

		robotConfig {
			lynxUsbDevice {
				name = "Expansion Hub Portal 1"
				parentModuleAddress = 3
				serialNumber = "xxxxxx"

				lynxModule("左侧REV", 3) {
					matrix12vMotor("左前", 3)
					matrix12vMotor("左后", 2)
					servo("左杆", 0)
					lynxEmbeddedImu("惯性左", 0, 0)
				}
				lynxModule("右侧REV", 2) {
					matrix12vMotor("肩膀", 0)
					matrix12vMotor("右前", 2)
					matrix12vMotor("右后", 3)
					servo("右杆", 0)
					servo("右手", 1)
					servo("左手", 2)
					lynxEmbeddedImu("惯性左", 0, 0)
				}
			}
		}

	}
}