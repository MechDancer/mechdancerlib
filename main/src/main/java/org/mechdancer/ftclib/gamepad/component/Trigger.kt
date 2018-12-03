package org.mechdancer.ftclib.gamepad.component

/**
 * 扳机
 */
class Trigger : IGamePadComponent<Double> {
    override var raw: Double = .0

    val value
        get() = raw
}