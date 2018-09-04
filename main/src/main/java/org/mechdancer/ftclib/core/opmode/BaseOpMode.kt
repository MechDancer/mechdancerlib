package org.mechdancer.ftclib.core.opmode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.mechdancer.ftclib.core.structure.PackingDevice
import org.mechdancer.ftclib.core.structure.model.Robot

/**
 * 程序入口
 * 所有 OpMode 应在该类的继承树下
 */
@Disabled
abstract class BaseOpMode<T : Robot>(protected val robot: T) : OpMode() {
	final override fun init() {
		PackingDevice.count = robot.devices.size
		PackingDevice.prefix = robot.name
		robot.devices.forEach { it.bind(hardwareMap) }
		robot.initialisable.forEach { it.init() }
		//TODO ask for resources
		initTask()
	}

	final override fun init_loop() {
		//TODO adjust sensors (if needed)
		//TODO initialize devices
		initLoopTask()
	}

	final override fun start() {
		robot.devices.forEach { it.reset() }
		startTask()
	}

	final override fun loop() {
		while (robot.devices.any { it.available && !it.ready })
			Thread.yield()
		//TODO calculate suggestions
		//TODO generate and execute commands
		loopTask()
		robot.run()
		robot.autoCallable.forEach { it.run() }
		robot.devices.forEach { it.run() }
	}

	final override fun stop() {
		robot.stoppable.forEach { it.stop() }
		robot.devices.forEach { it.unbind() }
		PackingDevice.count = 0
		//TODO release resources
		stopTask()
	}

	abstract fun initTask()

	open fun initLoopTask() {}

	open fun startTask() {}

	abstract fun loopTask()

	abstract fun stopTask()

}