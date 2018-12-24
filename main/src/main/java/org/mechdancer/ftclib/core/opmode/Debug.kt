package org.mechdancer.ftclib.core.opmode

import com.qualcomm.robotcore.util.RobotLog
import kotlin.system.measureTimeMillis

internal fun withMeasuringTime(prefix: String, block: () -> Unit) {
    RobotLog.d("$prefix 花费 ${measureTimeMillis(block)} ms")
}