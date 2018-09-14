package org.mechdancer.ftclib.test.foo

import org.junit.Test
import org.mechdancer.ftclib.core.structure.monomeric.device.PackingDevice
import org.mechdancer.ftclib.core.structure.monomeric.device.findAllDevices
import org.mechdancer.ftclib.core.structure.takeAll

class StructureTest {
	@Test
	fun test() {
		val foo = FooStructure()
		println(foo.findAllDevices().joinToString(separator = "\n"))
		println()
		println()
		println()
		println(foo.takeAll<PackingDevice<*>>())
	}
}
