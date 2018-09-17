package org.mechdancer.ftclib.test.foo

import org.junit.Test
import org.mechdancer.ftclib.core.structure.takeAll
import org.mechdancer.ftclib.internal.impl.PackingDevice
import org.mechdancer.ftclib.internal.impl.findAllDevices

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
