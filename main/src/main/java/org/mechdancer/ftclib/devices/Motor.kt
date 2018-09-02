package org.mechdancer.ftclib.devices

import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.mechdancer.ftclib.core.structure.DeviceConfig
import org.mechdancer.ftclib.core.structure.Structure

interface Motor : Structure {
	var power: Double

	var direction: DcMotorSimple.Direction

	class Config(
			name: String,
			var direction: DcMotorSimple.Direction = DcMotorSimple.Direction.FORWARD,
			enable: Boolean = false
	) : DeviceConfig(name, enable)
}