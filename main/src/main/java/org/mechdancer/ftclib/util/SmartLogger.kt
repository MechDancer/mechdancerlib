package org.mechdancer.ftclib.util

import com.qualcomm.robotcore.util.RobotLog

/**
 * Logger interface
 *
 * Classes implemented this interface can output logs.
 */
interface SmartLogger {

    /**
     * TAG of the logger
     *
     * Default is class name.
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

fun SmartLogger.warn(any: Any) =
    RobotLog.ww(getTag(), any.toString())

inline fun SmartLogger.info(block: () -> Any) =
    info(block())

inline fun SmartLogger.debug(block: () -> Any) =
    debug(block())

inline fun SmartLogger.error(block: () -> Any) =
    error(block())

inline fun SmartLogger.warn(block: () -> Any) =
    warn(block())