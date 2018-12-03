package org.mechdancer.ftclib.core.structure.composite.chassis.locator

import org.mechdancer.algebra.core.Vector
import org.mechdancer.algebra.implement.vector.listVectorOf

class Location(val x: Double, val y: Double, val theta: Double)
    : Vector by listVectorOf(x, y, theta)