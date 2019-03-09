package org.mechdancer.ftclib.gamepad.component

/**
 * Trigger
 *
 * Usually, triggers are used as button is very common,
 * since it has pressingThreshold, it has become to a button.
 */
class Trigger : IGamePadComponent<Double> {

    /**
     * Pressing threshold
     *
     * If trigger value is greater than that,
     * this trigger can be counted as *be pressed*.
     */
    var pressingThreshold = 0.7


    private var last = false

    /**
     * Returns the current state of the button
     */
    fun bePressed() = raw > pressingThreshold

    /**
     * Returns true when the first time the button be pressed
     */
    fun isPressing() = !last && bePressed()

    /**
     * Returns true when the first time the button be released
     */
    fun isReleasing() = last && !bePressed()


    /**
     * Value of the trigger
     */
    val value
        get() = raw

    override var raw: Double = .0
        set(value) {
            last = field > pressingThreshold
            field = value
        }

}