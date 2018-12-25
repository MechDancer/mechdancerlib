package org.mechdancer.ftclib.gamepad.component

/**
 * 按钮
 */
class Button : IGamePadComponent<Boolean> {

    private var last = false

    /**
     * 当前状态
     **/
    fun bePressed() = raw

    /**
     * 是否被按下
     *
     * 仅当按钮被按下的第一个周期为 `true`
     */
    fun isPressing() = !last && bePressed()

    /**
     * 是否释放
     *
     * 仅当按钮释放的第一个周期为 `true`
     */
    fun isReleasing() = last && !bePressed()

    override var raw: Boolean = false
        set(value) {
            last = field
            field = value
        }
}
