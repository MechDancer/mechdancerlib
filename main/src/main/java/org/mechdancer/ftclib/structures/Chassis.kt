package org.mechdancer.ftclib.structures

import org.mechdancer.ftclib.core.structure.CompositeStructure

interface Chassis : CompositeStructure {
	var powers: DoubleArray

	var maxPower: Double
}