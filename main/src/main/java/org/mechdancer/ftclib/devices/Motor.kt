package org.mechdancer.ftclib.devices

import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.mechdancer.ftclib.core.structure.DeviceConfig
import org.mechdancer.ftclib.core.structure.Structure

interface Motor : Structure {
	var power: Double

	var direction: DcMotorSimple.Direction

	class Config(
			name: String,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}