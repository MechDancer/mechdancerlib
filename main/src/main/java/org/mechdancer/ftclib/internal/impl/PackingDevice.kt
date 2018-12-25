package org.mechdancer.ftclib.internal.impl

import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.HardwareMap
import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.MonomericStructure
import org.mechdancer.ftclib.util.SmartLogger

/**
 * 设备
 * @param name 名字
 * @param enable 使能
 */
sealed class PackingDevice<in T : HardwareDevice>
(name: String, val enable: Boolean) :
        MonomericStructure(name), SmartLogger {

    /**
     * 对真实设备的引用
     */
    private var device: T? = null

    /**
     * 绑定设备
     */
    internal fun bind(hardwareMap: HardwareMap, id: String) {
        @Suppress("UNCHECKED_CAST")
        if (enable) device = hardwareMap[id] as T
    }

    /**
     * 解除设备绑定
     */
    internal fun unbind() {
        device = null
    }

    /**
     * 配置操作
     */
    protected open fun T.config() = Unit

    /**
     * 输入操作（传感器）
     */
    protected abstract fun T.input()

    /**
     * 输出操作（动力设备、灯）
     */
    protected abstract fun T.output()

    /**
     * 重置操作（重置设备）
     */
    protected abstract fun T.reset()

    /**
     * 重置操作（清除数据）
     */
    protected abstract fun resetData()

    /**
     * 重置操作
     */
    fun reset() {
        device?.input()
        device?.reset()
        resetData()
    }

    /**
     * 执行所有传递指令的操作
     */
    final override fun run() {
        device?.config()
        device?.input()
        device?.output()
    }


}

/**
 * 输出设备
 */
abstract class Effector<in T : HardwareDevice>
(name: String, enable: Boolean)
    : PackingDevice<T>(name, enable) {
    final override fun T.input() = Unit
    final override fun T.reset() = output()
}

/**
 * 传感器
 */
abstract class Sensor<in T : HardwareDevice>
(name: String, enable: Boolean)
    : PackingDevice<T>(name, enable) {
    final override fun T.output() = Unit
}

fun CompositeStructure.takeAllDevices(prefix: String = name): List<Pair<String, PackingDevice<*>>> =
        subStructures.fold(mutableListOf()) { acc, structure ->
            acc.addAll((structure as? CompositeStructure)?.let {
                structure.takeAllDevices("$prefix.${structure.name}")
            } ?: if (structure is PackingDevice<*>) listOf(
                    (if (prefix.split(".").last() != structure.name
                    ) "$prefix.${structure.name}" else prefix) to structure)
            else listOf())
            acc
        }
