package org.mechdancer.ftclib.core.structure.composite

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.composite.chassis.Chassis
import org.mechdancer.ftclib.core.structure.takeAll
import org.mechdancer.ftclib.internal.impl.findAllDevices
import org.mechdancer.ftclib.internal.impl.sensor.VoltageSensorImpl
import org.mechdancer.ftclib.util.OpModeFlow

/**
 * 机器人结构树根部
 * 由 OpMode 直接调用
 *
 * @param name 机器人名字
 * @param chassis 机器人底盘
 * @param subStructs 可添加子结构
 */
abstract class Robot(name: String, val chassis: Chassis, vararg subStructs: Structure)
	: AbstractStructure(name, chassis, *subStructs, VoltageSensorImpl()) {
	internal val devices = findAllDevices()

	internal val initialisable = takeAll<OpModeFlow.Initialisable>()
	internal val autoCallable = takeAll<OpModeFlow.AutoCallable>()
	internal val stoppable = takeAll<OpModeFlow.Stoppable>()
}
