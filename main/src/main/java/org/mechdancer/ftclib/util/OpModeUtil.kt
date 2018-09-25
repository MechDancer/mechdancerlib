package org.mechdancer.ftclib.util

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta
import org.firstinspires.ftc.robotcore.internal.opmode.RegisteredOpModes
import org.firstinspires.ftc.robotcore.internal.system.AppUtil

object OpModeUtil {

	fun getOpModeManager() =
			OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().activity)

	fun registerOpMode(opModeMeta: OpModeMeta, clazz: Class<out OpMode>) =
			RegisteredOpModes.getInstance().register(opModeMeta, clazz)

	fun registerTeleOp(name: String, group: String, clazz: Class<out OpMode>) =
			registerOpMode(OpModeMeta(name, OpModeMeta.Flavor.TELEOP, group), clazz)

	fun registerAutonomous(name: String, group: String, clazz: Class<out OpMode>) =
			registerOpMode(OpModeMeta(name, OpModeMeta.Flavor.AUTONOMOUS, group), clazz)

	fun addListener(listener: OpModeManagerNotifier.Notifications) =
			getOpModeManager().registerListener(listener)


	fun removeListener(listener: OpModeManagerNotifier.Notifications) =
			getOpModeManager().unregisterListener(listener)

	fun switchTo(name: String) {
		getOpModeManager().run {
			stopActiveOpMode()
			initActiveOpMode(name)
		}
	}
}