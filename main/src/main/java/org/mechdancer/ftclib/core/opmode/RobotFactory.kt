package org.mechdancer.ftclib.core.opmode

import org.mechdancer.ftclib.core.structure.composite.Robot
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
object RobotFactory {
    fun <T : Robot> createRobot(clazz: Class<BaseOpMode<T>>) =
        (clazz.genericSuperclass as? ParameterizedType)?.let { type ->
            type.actualTypeArguments.find { aType -> aType is Class<*> && Robot::class.java.isAssignableFrom(aType) }
                ?.let { it as Class<*> }
                ?.let {
                    try {
                        it.getConstructor().newInstance()
                    } catch (e: NoSuchMethodException) {
                        throw IllegalArgumentException("未找到 Robot: ${it.name} 的公共无参构造器")
                    } catch (e: InstantiationException) {
                        throw IllegalArgumentException("Robot 不能是抽象的")
                    }
                } ?: throw IllegalArgumentException("未找到 Robot 类型")
        } as T

    fun <T : Robot> BaseOpMode<T>.createRobot() = createRobot(javaClass)
}