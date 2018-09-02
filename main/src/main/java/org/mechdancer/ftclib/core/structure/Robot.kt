package org.mechdancer.ftclib.core.structure

import org.mechdancer.ftclib.structures.Chassis

/**
 * 机器人结构树根部
 * 由 OpMode 直接调用
 *
 * @param name 机器人名字
 * @param chassis 机器人底盘
 * @param subStructs 可添加子结构
 */
abstract class Robot(name: String, val chassis: Chassis, vararg subStructs: Structure)
	: AbstractStructure(name, {
	subStructure(chassis)
	subStructs.forEach { subStructure(it) }
}) {
	internal val devices = takeAll<PackingDevice<*>>()

	internal val initialisable = takeAll<OpModeFlow.Initialisable>()
	internal val autoCallable = takeAll<OpModeFlow.AutoCallable>()
	internal val stoppable = takeAll<OpModeFlow.Stoppable>()
}
