package org.mechdancer.ftclib.util

import android.util.Log
import com.qualcomm.robotcore.util.RobotLog

/**
 * 日志接口
 * 实现该接口的类可直接调用日志输出方法。
 */
interface SmartLogger {
	/**
	 * 日志标签
	 * 默认为类名
	 */
	val tag: String
		get() = ""
}

private fun SmartLogger.getTag() =
	tag.takeIf { it.isNotBlank() } ?: javaClass.name

fun SmartLogger.info(any: Any) =
	RobotLog.ii(getTag(), any.toString())

fun SmartLogger.debug(any: Any) =
	RobotLog.dd(getTag(), any.toString())

fun SmartLogger.error(any: Any) =
	RobotLog.ee(getTag(), any.toString())

fun SmartLogger.wtf(any: Any) =
	Log.wtf(getTag(), any.toString())

fun SmartLogger.warn(any: Any) =
	RobotLog.ww(getTag(), any.toString())