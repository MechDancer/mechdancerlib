package org.mechdancer.ftclib.algorithm

/**
 * *Controllers* are kinds of algorithms, having the capability of mapping [T] to [T].
 */
interface Controller<T> : (T) -> T