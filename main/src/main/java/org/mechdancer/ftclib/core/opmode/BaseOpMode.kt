package org.mechdancer.ftclib.core.opmode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.core.structure.takeAll
import org.mechdancer.ftclib.internal.impl.PackingDevice
import org.mechdancer.ftclib.internal.impl.sensor.VoltageSensorImpl
import org.mechdancer.ftclib.internal.impl.takeAllDevices
import org.mechdancer.ftclib.util.OpModeLifecycle

/**
 * 程序入口
 * 所有 OpMode 应在该类的继承树下
 */
@Disabled
abstract class BaseOpMode<T : Robot>(protected val robot: T) : OpMode() {

	private val initializations = robot.takeAll<OpModeLifecycle.Initialize>()
	private val starts = robot.takeAll<OpModeLifecycle.Start>()
	private val actions = robot.takeAll<OpModeLifecycle.Run>()
	private val stops = robot.takeAll<OpModeLifecycle.Stop>()

	private val devices = robot.takeAllDevices()
	private val voltageSensor = robot.takeAll<VoltageSensorImpl>()[0]

	final override fun init() {
		PackingDevice.count = devices.size
		devices.forEach { it.second.bind(hardwareMap, it.first) }
		initializations.forEach { it.init() }
		voltageSensor.bind(hardwareMap)

		//ask for resources
		initTask()
	}

	final override fun init_loop() {
		//adjust sensors (if needed)
		//initialize devices
		initLoopTask()
	}

	final override fun start() {
		devices.forEach { it.second.reset() }
		robot.reset()
		startTask()
		starts.forEach { it.start() }
	}

	final override fun loop() {
		while (devices.any { it.second.available && !it.second.ready })
			Thread.yield()
		//calculate suggestions
		//generate and execute commands
		loopTask()
		actions.forEach { it.run() }
		devices.forEach { it.second.run() }
		voltageSensor.run()
	}

	final override fun stop() {
		stops.forEach { it.stop() }
		devices.forEach { it.second.unbind() }
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