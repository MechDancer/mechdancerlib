package org.mechdancer.ftclib.test

import org.mechdancer.ftclib.core.structure.AbstractStructure
import org.mechdancer.ftclib.core.structure.injector.Inject
import org.mechdancer.ftclib.devices.Motor

object FooStructure : AbstractStructure({
	motor("banana") {
		enable = true
	}
}) {

	@Inject
	private lateinit var banana: Motor

	override fun invoke() {
		banana.power = 0.5
		banana()
	}
}