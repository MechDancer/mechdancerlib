package org.mechdancer.ftclib.core.opmode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.core.structure.takeAll
import org.mechdancer.ftclib.internal.impl.PackingDevice
import org.mechdancer.ftclib.internal.impl.sensor.VoltageSensorImpl
import org.mechdancer.ftclib.internal.impl.takeAllDevices
import org.mechdancer.ftclib.util.OpModeLifecycle
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.reflect.ParameterizedType
import kotlin.concurrent.thread

/**
 * 程序入口
 * 所有 OpMode 应在该类的继承树下
 */
@Suppress("UNCHECKED_CAST")
@Disabled
abstract class BaseOpMode<T : Robot> : OpMode() {

	protected val robot: T = (javaClass.genericSuperclass as? ParameterizedType)?.let { type ->
		type.actualTypeArguments.find { aType -> aType is Class<*> && Robot::class.java.isAssignableFrom(aType) }
			?.let { it -> it as Class<*> }
			?.let {
				try {
					it.getConstructor().newInstance()
				} catch (e: NoSuchMethodException) {
					throw IllegalArgumentException("未找到 Robot: ${it.name} 的公共无参构造器")
				}
			} ?: throw IllegalArgumentException("未找到 Robot 类型")
	} as T

	private val initializations = robot.takeAll<OpModeLifecycle.Initialize>()
	private val starts = robot.takeAll<OpModeLifecycle.Start>()
	private val actions = robot.takeAll<OpModeLifecycle.Run>()
	private val stops = robot.takeAll<OpModeLifecycle.Stop>()

	private val devices = robot.takeAllDevices().map {
		it.first.dropWhile { name -> name != '.' }.removePrefix(".") to it.second
	}

	private val voltageSensor = robot.takeAll<VoltageSensorImpl>()[0]

	private var lastPeriod = -1L

	var period = lastPeriod
		private set

	protected var exceptionHandler: (String, Throwable) -> Unit = { lifecycle: String, t: Throwable ->
		thread { throw t }
		RobotLog.setGlobalErrorMsg("用户代码在 <$lifecycle> 抛出了:\n" +
			StringWriter().also { t.printStackTrace(PrintWriter(it)) }.toString())
	}

	private inline fun catchException(lifecycle: String, block: () -> Unit) =
		try {
			block()
		} catch (t: Throwable) {
			exceptionHandler(lifecycle, t)
		}

	final override fun init() {
		PackingDevice.count = devices.size
		devices.forEach { it.second.bind(hardwareMap, it.first) }
		initializations.forEach { it.init() }
		voltageSensor.bind(hardwareMap)
		catchException("init") {
			//ask for resources
			initTask()
		}
	}

	final override fun init_loop() {
		catchException("init_loop") {
			//adjust sensors (if needed)
			//initialize devices
			initLoopTask()
		}
	}

	final override fun start() {
		devices.forEach { it.second.reset() }
		catchException("start") {
			robot.reset()
			startTask()
			starts.forEach { it.start() }
		}
	}

	final override fun loop() {
		while (devices.any { it.second.available && !it.second.ready })
			Thread.yield()
		catchException("loop") {
			//calculate suggestions
			//generate and execute commands
			loopTask()
			actions.forEach { it.run() }
		}
		devices.forEach { it.second.run() }
		voltageSensor.run()
		period = System.currentTimeMillis() - lastPeriod
		lastPeriod = System.currentTimeMillis()
	}

	final override fun stop() {
		catchException("stop") {
			stops.forEach { it.stop() }
		}
		devices.forEach { it.second.unbind() }
		PackingDevice.count = 0
		catchException("stop") {
			//release resources
			stopTask()
		}
	}

	final override fun getRuntime(): Double {
		return super.getRuntime()
	}

	final override fun updateTelemetry(telemetry: Telemetry?) {
		super.updateTelemetry(telemetry)
	}

	final override fun internalPostInitLoop() {
		super.internalPostInitLoop()
	}

	final override fun internalPreInit() {
		super.internalPreInit()
	}

	final override fun internalPostLoop() {
		super.internalPostLoop()
	}

	final override fun resetStartTime() {
		super.resetStartTime()
	}

	abstract fun initTask()

	open fun initLoopTask() {}

	open fun startTask() {}

	abstract fun loopTask()

	abstract fun stopTask()

}