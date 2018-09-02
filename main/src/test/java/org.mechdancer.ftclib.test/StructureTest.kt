package org.mechdancer.ftclib.test

import org.junit.Test
import org.mechdancer.ftclib.core.structure.flatten

class StructureTest {
	@Test
	fun test() {
		val foo = FooStructure()
		println(foo.flatten())
	}
}