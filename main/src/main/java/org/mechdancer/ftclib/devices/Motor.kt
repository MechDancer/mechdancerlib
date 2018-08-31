package org.mechdancer.ftclib.devices

import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.mechdancer.ftclib.core.structure.DeviceConfig

interface Motor {
	var power: Double

	var direction: DcMotorSimple.Direction

	class Config(
			name: String,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}