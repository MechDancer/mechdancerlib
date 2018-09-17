package org.mechdancer.ftclib.util

import android.util.Log

interface SmartLogger {
	val tag: String
		get() = ""
}

private fun SmartLogger.getTag() =
		tag.takeIf { it.isNotBlank() } ?: javaClass.name

fun SmartLogger.info(any: Any) =
		Log.i(getTag(), any.toString())

fun SmartLogger.debug(any: Any) =
		Log.d(getTag(), any.toString())

fun SmartLogger.error(any: Any) =
		Log.e(getTag(), any.toString())

fun SmartLogger.wtf(any: Any) =
		Log.wtf(getTag(), any.toString())

fun SmartLogger.warn(any: Any) =
		Log.w(getTag(), any.toString())