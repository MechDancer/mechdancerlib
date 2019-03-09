package org.mechdancer.ftclib.internal.impl

import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.HardwareMap
import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.MonomericStructure
import org.mechdancer.ftclib.util.SmartLogger

/**
 * Device wrapper
 *
 * Contains hardware device
 *
 * @param name name
 * @param enable whether to enable
 */
sealed class PackingDevice<in T : HardwareDevice>
(name: String, val enable: Boolean) :
    MonomericStructure(name), SmartLogger {

    /**
     * Reference of hardware device
     */
    private var device: T? = null

    /**
     * Binds through [hardwareMap]
     */
    internal fun bind(hardwareMap: HardwareMap, id: String) {
        @Suppress("UNCHECKED_CAST")
        if (enable) device = hardwareMap[id] as T
    }

    /**
     * Unbinds
     */
    internal fun unbind() {
        device = null
    }

    /**
     * Config operation
     */
    protected open fun T.config() = Unit

    /**
     * Input operation (Sensor)
     */
    protected abstract fun T.input()

    /**
     * Output operation (Effector)
     */
    protected abstract fun T.output()

    /**
     * Reset operation (Device)
     */
    protected abstract fun T.reset()

    /**
     * Reset operation (Data)
     */
    protected open fun resetData() = Unit

    /**
     * Resets this device
     */
    fun reset() {
        device?.input()
        device?.reset()
        resetData()
    }

    /**
     * Runs this device
     */
    final override fun run() {
        device?.config()
        device?.input()
        device?.output()
    }

}

/**
 * Effector
 *
 * Only has [output]
 */
abstract class Effector<in T : HardwareDevice>
(name: String, enable: Boolean)
    : PackingDevice<T>(name, enable) {
    final override fun T.input() = Unit
    final override fun T.reset() = output()
}

/**
 * Sensor
 *
 * Only has [input]
 */
abstract class Sensor<in T : HardwareDevice>
(name: String, enable: Boolean)
    : PackingDevice<T>(name, enable) {
    final override fun T.output() = Unit
}

/**
 * Take all devices from a tree
 */
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
