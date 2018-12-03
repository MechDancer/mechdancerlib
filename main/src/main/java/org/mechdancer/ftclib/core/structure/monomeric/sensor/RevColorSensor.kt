package org.mechdancer.ftclib.core.structure.monomeric.sensor

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * Rev 颜色传感器
 */
interface RevColorSensor : Structure {
    /**
     * 颜色数值
     */
    val colorData: ColorData

    /**
     * 是否开启 LED
     */
    var enableLed: Boolean

    /**
     * 颜色数据类
     *
     * @property red 红
     * @property green 绿
     * @property blue 蓝
     * @property alpha alpha 通道
     * @property argb 全部数据组合
     */
    data class ColorData(val red: Int, val green: Int, val blue: Int, val alpha: Int, val argb: Int) {
        override fun toString(): String = "RevSensorValue(Red:$red Blue:$blue Green:$green Alpha:$alpha)"
    }

    /**
     * 配置
     *
     * @param name 名字
     * @param enable 是否启用
     */
    class Config(name: String, enable: Boolean = false) : DeviceConfig(name, enable)
}
