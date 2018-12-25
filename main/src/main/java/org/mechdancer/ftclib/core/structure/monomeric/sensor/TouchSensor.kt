package org.mechdancer.ftclib.core.structure.monomeric.sensor

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * 触控传感器
 */
interface TouchSensor : Structure {

    /**
     * 力
     *
     * 若传感器不支持则永远为 `0` 或 `1`
     */
    val force: Double

    /**
     * 当前状态
     **/
    fun bePressed(): Boolean

    /**
     * 是否被按下
     *
     * 仅当按钮被按下的第一个周期为 `true`
     */
    fun isPressing(): Boolean

    /**
     * 是否释放
     *
     * 仅当按钮释放的第一个周期为 `true`
     */
    fun isReleasing(): Boolean


    /**
     * 配置
     *
     * @param name 名字
     * @param enable 是否启用
     */
    class Config(name: String, enable: Boolean = false) : DeviceConfig(name, enable)
}