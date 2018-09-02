package org.mechdancer.ftclib.core.structure

import org.mechdancer.ftclib.devices.ContinuousServo
import org.mechdancer.ftclib.devices.DeviceFactory
import org.mechdancer.ftclib.devices.Motor
import org.mechdancer.ftclib.devices.Servo
import org.mechdancer.ftclib.sensors.Encoder
import org.mechdancer.ftclib.sensors.RevColorSensor
import org.mechdancer.ftclib.structures.MotorWithEncoder

class StructureBuilder(private val structureName: String) {

	private val _subStructures = mutableListOf<Structure>()

	private fun add(structure: Structure) = _subStructures.add(structure)

	var action: (structures: List<Structure>) -> Unit = {}

	fun motor(name: String, config: Motor.Config.() -> Unit) {
		add(DeviceFactory.motor(name, config))
	}

	fun encoder(name: String, config: Encoder.Config.() -> Unit) {
		add(DeviceFactory.encoder(name, config))
	}

	fun motorWithEncoder(name: String, config: MotorWithEncoder.Config.() -> Unit) {
		add(DeviceFactory.motorWithEncoder(name, config))
	}

	fun subStructure(name: String, block: StructureBuilder.() -> Unit) {
		add(StructureBuilder(name).apply(block).build())
	}

	fun subStructure(subStructure: Structure) {
		add(subStructure)
	}

	fun servo(name: String, config: Servo.Config.() -> Unit) {
		add(DeviceFactory.servo(name, config))
	}

	fun continuousServo(name: String, config: ContinuousServo.Config.() -> Unit) {
		add(DeviceFactory.continuousServo(name, config))
	}


	fun revColor(name: String, config: RevColorSensor.Config.() -> Unit) {
		add(DeviceFactory.revColor(name, config))
	}


	fun build() = object : CompositeStructure {
		override val name = structureName
		override val subStructures: List<Structure> = ArrayList(_subStructures)
		override fun run() = action(subStructures)

		override fun toString() = "AnonymousStructure[$name]"
	}
}