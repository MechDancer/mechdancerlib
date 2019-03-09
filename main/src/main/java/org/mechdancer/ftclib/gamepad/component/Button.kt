package org.mechdancer.ftclib.gamepad.component

/**
 * Button
 */
class Button : IGamePadComponent<Boolean> {

    private var last = false

    /**
     * Returns the current state of the button
     */
    fun bePressed() = raw


    /**
     * Returns true when the first time the button be pressed
     */
    fun isPressing() = !last && bePressed()

    /**
     * Returns true when the first time the button be released
     */
    fun isReleasing() = last && !bePressed()

    override var raw: Boolean = false
        set(value) {
            last = field
            field = value
        }
}
