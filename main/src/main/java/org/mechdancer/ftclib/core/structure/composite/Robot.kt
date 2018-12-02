package org.mechdancer.ftclib.core.structure.composite

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.takeAll
import org.mechdancer.ftclib.internal.impl.sensor.VoltageSensorImpl
import org.mechdancer.ftclib.util.AutoCallable
import org.mechdancer.ftclib.util.Resettable

/**
 * 机器人结构树根部
 * 由 OpMode 直接调用
 *
 * @param name 机器人名字
 * @param subStruct 可添加子结构
 */
abstract class Robot(name: String, vararg subStruct: Structure)
    : AbstractStructure(name, *subStruct, VoltageSensorImpl()), AutoCallable {

	private val rests = takeAll<Resettable>()


	fun reset() {
		rests.forEach { it.reset() }
	}

}
