package org.mechdancer.ftclib.core.structure.composite

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.composite.chassis.Chassis
import org.mechdancer.ftclib.core.structure.takeAll
import org.mechdancer.ftclib.internal.impl.sensor.VoltageSensorImpl
import org.mechdancer.ftclib.internal.impl.takeAllDevices
import org.mechdancer.ftclib.util.AutoCallable
import org.mechdancer.ftclib.util.OpModeLifecycle
import org.mechdancer.ftclib.util.Resettable

/**
 * 机器人结构树根部
 * 由 OpMode 直接调用
 *
 * @param name 机器人名字
 * @param chassis 机器人底盘
 * @param subStructs 可添加子结构
 */
abstract class Robot(name: String, protected val chassis: Chassis, vararg subStructs: Structure)
	: AbstractStructure(name, chassis, *subStructs, VoltageSensorImpl()), AutoCallable {
	internal val devices = takeAllDevices()
	internal val resttables = takeAll<Resettable>()

	internal val initialisable = takeAll<OpModeLifecycle.Initialize>()
	internal val startables = takeAll<OpModeLifecycle.Start>()
	internal val runnable = takeAll<OpModeLifecycle.Run>()
	internal val stoppable = takeAll<OpModeLifecycle.Stop>()

}
