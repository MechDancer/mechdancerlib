package org.mechdancer.ftclib.core.structure

import org.mechdancer.ftclib.devices.ContinuousServo
import org.mechdancer.ftclib.devices.Motor
import org.mechdancer.ftclib.devices.Servo
import org.mechdancer.ftclib.sensors.REVColorSensor

class StructureBuilder(private val structureName: String) {

	private val _subStructures = mutableListOf<Structure>()

	var action: (structures: List<Structure>) -> Unit = {}

	fun motor(name: String, config: Motor.Config.() -> Unit) {
		Motor(Motor.Config(name).apply(config)).let(_subStructures::add)
	}

	fun subStructure(name: String, block: StructureBuilder.() -> Unit) {
		StructureBuilder(name).apply(block).build().let(_subStructures::add)
	}

	fun subStructure(subStructure: Structure) {
		subStructure.let(_subStructures::add)
	}

	fun servo(name: String, config: Servo.Config.() -> Unit) {
		Servo(Servo.Config(name).apply(config)).let(_subStructures::add)
	}

	fun continuousServo(name: String, config: ContinuousServo.Config.() -> Unit) {
		ContinuousServo(ContinuousServo.Config(name).apply(config)).let(_subStructures::add)
	}

	fun revColor(name: String, config: REVColorSensor.Config.() -> Unit) {
		REVColorSensor(REVColorSensor.Config(name).apply(config)).let(_subStructures::add)
	}


	fun build() = object : Structure {
		override val name = structureName

		override val subStructures = ArrayList(_subStructures)

		override fun invoke() = action(subStructures)

		override fun toString() = "AnonymousStructure[$name]"
	}
}