package org.mechdancer.ftclib.core.structure.monomeric

import org.mechdancer.filters.signalAndSystem.PID
import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.monomeric.device.DeviceConfig
import org.mechdancer.ftclib.core.structure.monomeric.device.effector.Motor
import org.mechdancer.ftclib.core.structure.monomeric.device.sensor.Encoder

interface MotorWithEncoder : Motor, Encoder, Structure {
	var mode: Mode

	var targetSpeed: Double

	var targetPosition: Double


	enum class Mode { SPEED_CLOSE_LOOP, OPEN_LOOP, POSITION_CLOSE_LOOP, LOCK, STOP }

	class Config(name: String,
	             enable: Boolean = false,
	             var radians: Double = .0,
	             var direction: Motor.Direction = Motor.Direction.FORWARD,
	             var pidPosition: PID = PID(1.0, .0, .0, .0, .0),
	             var pidSpeed: PID = PID(1.0, .0, .0, .0, .0)) : DeviceConfig(name, enable)
}
