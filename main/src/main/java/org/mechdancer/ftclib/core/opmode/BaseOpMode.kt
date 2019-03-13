package org.mechdancer.ftclib.core.opmode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.mechdancer.ftclib.core.opmode.RobotFactory.createRobot
import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.core.structure.takeAll
import org.mechdancer.ftclib.internal.impl.sensor.VoltageSensorImpl
import org.mechdancer.ftclib.internal.impl.takeAllDevices
import org.mechdancer.ftclib.util.OpModeLifecycle
import org.mechdancer.ftclib.util.SmartLogger
import org.mechdancer.ftclib.util.info
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Base of OpModes
 *
 * All OpModes should extends this class.
 * > Notice that non-`lateinit` members may lead to unexpected problems.
 */
@Suppress("UNCHECKED_CAST")
@Disabled
abstract class BaseOpMode<T : Robot>
constructor(opModeName: String? = null) : OpMode(), SmartLogger {

    protected val robot: T = createRobot()
    val opModeName: String = opModeName ?: javaClass.simpleName

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

//        withMeasuringTime("绑定电压传感器") {
//            voltageSensor.bind(hardwareMap) TODO
//        }

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
            //            debug("Calling initLoop task")
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
            //            debug("Calling look task")
            loopTask()

            actions.forEach {
                //                debug("Calling run of ${it.name}")
                it.run()
            }
        }

        devices.forEach { (name, device) ->
            //            debug("Calling run of device: $name")
            device.run()
        }

/*        withMeasuringTime("执行循环电压传感器") {
            voltageSensor.run()   TODO
        }*/

        period = (System.currentTimeMillis() - lastPeriod).toInt()
        lastPeriod = System.currentTimeMillis()
//        debug("Finished loop, use $period milliseconds")
    }

    final override fun stop() {

        catchException("stop") {

            catchException("stop") {
                info("Calling stop task")
                stopTask()
            }


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

        /*withMeasuringTime("解绑电压传感器") {
            voltageSensor.unbind()    TODO
        }*/

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