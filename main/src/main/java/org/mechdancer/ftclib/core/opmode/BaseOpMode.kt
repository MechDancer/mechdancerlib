package org.mechdancer.ftclib.core.opmode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.core.structure.takeAll
import org.mechdancer.ftclib.internal.impl.PackingDevice
import org.mechdancer.ftclib.internal.impl.sensor.VoltageSensorImpl

/**
 * 程序入口
 * 所有 OpMode 应在该类的继承树下
 */
@Disabled
abstract class BaseOpMode<T : Robot>(protected val robot: T) : OpMode() {
	final override fun init() {
		PackingDevice.count = robot.devices.size
		robot.devices.forEach { it.second.bind(hardwareMap, it.first) }
		robot.initialisable.forEach { it.init() }
		robot.takeAll<VoltageSensorImpl>()[0].bind(hardwareMap)

		//ask for resources
		initTask()
	}

	final override fun init_loop() {
		//adjust sensors (if needed)
		//initialize devices
		initLoopTask()
	}

	final override fun start() {
		robot.devices.forEach { it.second.reset() }
		robot.resttables.forEach { it.reset() }
		startTask()
		robot.startables.forEach { it.start() }
	}

	final override fun loop() {
		while (robot.devices.any { it.second.available && !it.second.ready })
			Thread.yield()
		//calculate suggestions
		//generate and execute commands
		loopTask()
		robot.runnable.forEach { it.run() }
		robot.devices.forEach { it.second.run() }
		robot.takeAll<VoltageSensorImpl>()[0].run()
	}

	final override fun stop() {
		robot.stoppable.forEach { it.stop() }
		robot.devices.forEach { it.second.unbind() }
		PackingDevice.count = 0
		//release resources
		stopTask()
	}

	abstract fun initTask()

	open fun initLoopTask() {}

	open fun startTask() {}

	abstract fun loopTask()

	abstract fun stopTask()

}