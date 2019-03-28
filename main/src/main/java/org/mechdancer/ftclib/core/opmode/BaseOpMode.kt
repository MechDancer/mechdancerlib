package org.mechdancer.ftclib.core.opmode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.util.RobotLog
import org.mechdancer.ftclib.core.opmode.RobotFactory.createRobot
import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.core.structure.takeAll
import org.mechdancer.ftclib.internal.impl.sensor.VoltageSensorImpl
import org.mechdancer.ftclib.internal.impl.takeAllDevices
import org.mechdancer.ftclib.util.OpModeLifecycle
import org.mechdancer.ftclib.util.info
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Base of OpModes
 *
 * All OpModes should extends this class.
 * > ~~Notice that non-`lateinit` members may lead to unexpected problems.~~ Fixed.
 */
@Disabled
abstract class BaseOpMode<T : Robot> : OpModeWithRobot<T>() {

    protected val robot: T = createRobot()

    private val initializations = robot.takeAll<OpModeLifecycle.Initialize<T>>()
    private val starts = robot.takeAll<OpModeLifecycle.Start>()
    private val actions = robot.takeAll<OpModeLifecycle.Run>()
    private val stops = robot.takeAll<OpModeLifecycle.Stop>()

    private val devices = robot.takeAllDevices().map {
        it.first.dropWhile { name -> name != '.' }.removePrefix(".") to it.second
    }

    private val voltageSensor = robot.takeAll<VoltageSensorImpl>()[0]

    private var lastPeriod = -1L

    var period = -1
        private set

    protected var exceptionHandler: (String, Throwable) -> Unit = { lifecycle: String, t: Throwable ->
        RobotLog.setGlobalErrorMsg("User code throw an Exception in <$lifecycle> :\n" +
            StringWriter().also { t.printStackTrace(PrintWriter(it)) }.toString())
    }

    private inline fun catchException(lifecycle: String, block: () -> Unit) =
        try {
            block()
        } catch (t: Throwable) {
            exceptionHandler(lifecycle, t)
        }


    final override fun init() {
        devices.forEach { (name, device) ->
            info("Binding device: $name")
            device.bind(hardwareMap, name)
        }

        withMeasuringTime("Binding voltage sensor") {
            voltageSensor.bind(hardwareMap)
        }

        catchException("init") {
            initializations.forEach {
                info("Calling init of ${it.name}")
                it.init(this)
            }
            info("Calling init task")
            initTask()
            info("Initialized")
        }

    }

    final override fun init_loop() {
        catchException("init_loop") {
            initLoopTask()
        }
    }

    final override fun start() {
        catchException("start") {
            devices.forEach { (name, device) ->
                info("Calling reset of device: $name")
                device.reset()
            }

            info("Calling reset of robot")
            robot.reset()

            starts.forEach {
                info("Calling start of ${it.name}")
                it.start()
            }

            info("Calling start task")
            startTask()
        }
        info("Started")
    }

    final override fun loop() {

        catchException("loop") {
            loopTask()

            actions.forEach {
                it.run()
            }
        }

        devices.forEach { (_, device) ->
            device.run()
        }

        withMeasuringTime("Running voltage sensor") {
            voltageSensor.run()
        }

        period = (System.currentTimeMillis() - lastPeriod).toInt()
        lastPeriod = System.currentTimeMillis()
    }

    final override fun stop() {

        catchException("stop") {

            info("Calling stop task")
            stopTask()


            stops.forEach {
                info("Calling stop of ${it.name}")
                it.stop()
            }
        }

        devices.forEach { (name, device) ->
            info("Unbinding device: $name")
            device.unbind()
        }

        info("Stopped")

        withMeasuringTime("Unbinding voltage sensor") {
            voltageSensor.unbind()
        }

    }

    abstract fun initTask()

    open fun initLoopTask() {}

    open fun startTask() {}

    abstract fun loopTask()

    abstract fun stopTask()

}