package org.mechdancer.ftclib.gamepad.component

/**
 * 扳机
 */
class Trigger : IGamePadComponent<Double> {

    var pressingThreshold = 0.7


    private var last = false

    /**
     * 当前状态
     **/
    fun bePressed() = raw > pressingThreshold

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


    val value
        get() = raw

    override var raw: Double = .0
        set(value) {
            last = field > pressingThreshold
            field = value
        }

}