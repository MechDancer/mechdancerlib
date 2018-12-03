package org.mechdancer.ftclib.core.structure.monomeric.effector

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * 舵机
 */
interface Servo : Structure {
    /**
     * 舵机位置
     * 范围: [初始位置, 末位置]
     */
    var position: Double

    var pwmOutput: Boolean

    /**
     * 配置
     *
     * @param name 名字
     * @param enable 是否启用
     * @param origin 初始位置（弧度）
     * @param ending 末位置（弧度）
     */
    class Config(
            name: String,
            enable: Boolean = false,
            var origin: Double = .0,
            var ending: Double = .0
    ) : DeviceConfig(name, enable)
}