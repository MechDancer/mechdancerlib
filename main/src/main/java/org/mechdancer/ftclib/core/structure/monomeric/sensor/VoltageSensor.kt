package org.mechdancer.ftclib.core.structure.monomeric.sensor

import org.mechdancer.ftclib.core.structure.Structure

/**
 * 电压传感器
 * 不可单独构造，若需使用，在 [org.mechdancer.ftclib.core.structure.composite.Robot] 中使用
[org.mechdancer.ftclib.core.structure.injector.Inject] 注入即可。
 */
interface VoltageSensor : Structure {
	/**
	 * 电压值
	 */
	val voltage: Double

}