package org.mechdancer.ftclib.core.opmode

import org.mechdancer.ftclib.core.structure.composite.Robot
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
/**
 * Robot factory
 *
 * Use generic parameter to create a robot instance.
 */
object RobotFactory {

    /**
     * Create a [clazz] robot
     */
    fun <T : Robot> createRobot(clazz: Class<OpModeWithRobot<T>>): T =
        (clazz.genericSuperclass as? ParameterizedType)?.let { type ->
            type.actualTypeArguments.find { aType -> aType is Class<*> && Robot::class.java.isAssignableFrom(aType) }
                ?.let { it as Class<*> }
                ?.let {
                    try {
                        it.getConstructor().newInstance()
                    } catch (e: NoSuchMethodException) {
                        throw IllegalArgumentException("Unable to find public non-parameters constructor of robot: ${it.name}.")
                    } catch (e: InstantiationException) {
                        throw IllegalArgumentException("Robot can't be abstract.")
                    }
                } ?: throw IllegalArgumentException("Unable to find robot type.")
        } as? T ?: createRobot(clazz.superclass as Class<OpModeWithRobot<T>>)
        ?: throw RuntimeException("Unable to create robot.")

    /**
     * Create a [T] robot
     */
    fun <T : Robot> OpModeWithRobot<T>.createRobot() = createRobot(javaClass)
}