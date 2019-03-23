package org.mechdancer.ftclib.classfilter

import com.qualcomm.robotcore.eventloop.opmode.*
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta
import org.firstinspires.ftc.robotcore.internal.opmode.RegisteredOpModes
import org.mechdancer.ftclib.core.opmode.OpModeWithRobot
import org.mechdancer.ftclib.core.opmode.RemoteControlOpMode
import org.mechdancer.ftclib.core.structure.composite.Robot
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
object MechDancerOpModeRegister : ClassFilterAdapter() {
    private val teleops = mutableSetOf<Class<out OpMode>>()
    private val autonomous = mutableSetOf<Class<out OpMode>>()

    private val registered by lazy { RegisteredOpModes.getInstance().opModes }

    override fun filterClass(clazz: Class<*>) {
        if (!OpModeWithRobot::class.java.isAssignableFrom(clazz)
            || clazz.isAnnotationPresent(Disabled::class.java)
            || clazz.isAnnotationPresent(TeleOp::class.java)
            || clazz.isAnnotationPresent(Autonomous::class.java)
        ) return
        if (RemoteControlOpMode::class.java.isAssignableFrom(clazz))
            teleops.add(clazz as Class<out OpMode>)
        else autonomous.add(clazz as Class<out OpMode>)
    }

    fun register(manager: OpModeManager) {

        fun reg(clazz: Class<out OpMode>, flavor: OpModeMeta.Flavor) {
            val name = clazz
                .takeIf { it.isAnnotationPresent(Naming::class.java) }
                ?.let { it.getAnnotation(Naming::class.java) }
                ?.name
                ?.takeIf { it.isNotEmpty() }
                ?: clazz.simpleName

            fun findRobotClass(clazz2: Class<*>): Class<out Robot>? =
                (clazz2.genericSuperclass as? ParameterizedType)?.let { type ->
                    type.actualTypeArguments.find { aType -> aType is Class<*> && Robot::class.java.isAssignableFrom(aType) }
                        ?.let { it as? Class<out Robot> }
                } ?: clazz2.superclass?.let { findRobotClass(it) }

            val robot = findRobotClass(clazz)

            manager.register(OpModeMeta(name, flavor, robot?.simpleName ?: "GG"), clazz)
        }

        teleops.forEach {
            reg(it, OpModeMeta.Flavor.TELEOP)
        }
        autonomous.forEach {
            reg(it, OpModeMeta.Flavor.AUTONOMOUS)
        }
    }
}
