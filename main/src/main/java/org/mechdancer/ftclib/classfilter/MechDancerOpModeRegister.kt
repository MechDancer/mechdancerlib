package org.mechdancer.ftclib.classfilter

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta
import org.firstinspires.ftc.robotcore.internal.opmode.RegisteredOpModes
import org.mechdancer.ftclib.core.opmode.BaseOpMode
import org.mechdancer.ftclib.core.opmode.RemoteControlOpMode
import org.mechdancer.ftclib.core.structure.composite.Robot

@Suppress("UNCHECKED_CAST")
object MechDancerOpModeRegister : ClassFilterAdapter() {
	private val teleOps = mutableSetOf<Class<out OpMode>>()
	private val autonomous = mutableSetOf<Class<out OpMode>>()

	private val registered by lazy { RegisteredOpModes.getInstance().opModes }

	override fun filterClass(clazz: Class<*>) {
		if (clazz.isAnnotationPresent(Disabled::class.java) ||
			!BaseOpMode::class.java.isAssignableFrom(clazz)
		) return
		if (RemoteControlOpMode::class.java.isAssignableFrom(clazz))
			teleOps.add(clazz as Class<out OpMode>)
		else autonomous.add(clazz as Class<out OpMode>)
	}

	fun register(manager: OpModeManager) {

		fun reg(clazz: Class<out OpMode>, flavor: OpModeMeta.Flavor) {
//			val robot = createRobot(clazz as Class<BaseOpMode<Robot>>)
//			val opMode = try {
//				clazz.getConstructor(String::class.java, Robot::class.java)
//					.newInstance(clazz.simpleName, robot)
//			} catch (e: NoSuchMethodException) {
//				try {
//					clazz.getConstructor(String::class.java)
//						.newInstance(clazz.simpleName)
//				}catch (e:NoSuchMethodException){
//					clazz.getConstructor()
//						.newInstance()
//				}
//			}
			val opMode =
				clazz.newInstance() as BaseOpMode<*>
			val robot = BaseOpMode::class.java.getDeclaredField("robot")
				.also { it.isAccessible = true }[opMode] as Robot
			manager.register(OpModeMeta(opMode.opModeName, flavor, robot.name), opMode)
		}

		teleOps.forEach {
			reg(it, OpModeMeta.Flavor.TELEOP)
		}
		autonomous.forEach {
			reg(it, OpModeMeta.Flavor.AUTONOMOUS)
		}
	}
}