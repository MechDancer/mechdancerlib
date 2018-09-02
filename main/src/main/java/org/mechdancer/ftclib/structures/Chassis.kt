package org.mechdancer.ftclib.structures

import org.mechdancer.ftclib.core.structure.Structure

interface Chassis : Structure {
	var powers: DoubleArray

	var maxPower: Double
}