package org.mechdancer.ftclib.core.opmode

import com.qualcomm.robotcore.util.RobotLog
import org.mechdancer.ftclib.gamepad.Gamepad
import kotlin.random.Random
import kotlin.system.measureNanoTime

internal inline fun withMeasuringTime(prefix: String, block: () -> Unit) {
    if (enableTimeMeasuring)
        RobotLog.d("$prefix 花费 ${measureNanoTime(block) * 1E-6} ms")
    else block()
}

internal fun Gamepad.random() {
    leftStick.raw = doubleArrayOf(Random.nextDouble(-1.0, 1.0), Random.nextDouble(-1.0, 1.0))
    rightStick.raw = doubleArrayOf(Random.nextDouble(-1.0, 1.0), Random.nextDouble(-1.0, 1.0))
    rightTrigger.raw = Random.nextDouble(.0, 1.0)
    leftTrigger.raw = Random.nextDouble(.0, 1.0)

    a.raw = Random.nextBoolean()
    b.raw = Random.nextBoolean()
    x.raw = Random.nextBoolean()
    y.raw = Random.nextBoolean()
    up.raw = Random.nextBoolean()
    down.raw = Random.nextBoolean()
    left.raw = Random.nextBoolean()
    right.raw = Random.nextBoolean()

    leftBumper.raw = Random.nextBoolean()
    rightBumper.raw = Random.nextBoolean()
}

var enableTimeMeasuring = false