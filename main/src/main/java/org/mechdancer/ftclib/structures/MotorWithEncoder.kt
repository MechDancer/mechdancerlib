package org.mechdancer.ftclib.structures

import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.mechdancer.filters.signalAndSystem.PID
import org.mechdancer.ftclib.core.structure.DeviceConfig
import org.mechdancer.ftclib.devices.Motor
import org.mechdancer.ftclib.sensors.Encoder

interface MotorWithEncoder : Motor, Encoder {
	var mode: Mode

	var targetSpeed: Double

	var targetPosition: Double


	enum class Mode { SPEED_CLOSE_LOOP, OPEN_LOOP, POSITION_CLOSE_LOOP, LOCK, STOP }

	class Config(name: String, enable: Boolean = false,
	             var radians: Double = .0,
	             var direction: DcMotorSimple.Direction,
	             var pidPosition: PID = PID(1.0, .0, .0, .0, .0),
	             var pidSpeed: PID = PID(1.0, .0, .0, .0, .0)) : DeviceConfig(name, enable)

}