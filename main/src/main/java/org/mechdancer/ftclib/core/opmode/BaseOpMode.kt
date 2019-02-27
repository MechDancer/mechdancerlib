package org.mechdancer.ftclib.core.opmode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.mechdancer.ftclib.core.opmode.RobotFactory.createRobot
import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.core.structure.takeAll
import org.mechdancer.ftclib.internal.impl.sensor.VoltageSensorImpl
import org.mechdancer.ftclib.internal.impl.takeAllDevices
import org.mechdancer.ftclib.util.OpModeLifecycle
import java.io.PrintWriter
import java.io.StringWriter

/**
 * 程序入口
 *
 * 所有 OpMode 应在该类的继承树下
 *
 * 注意: 类中成员若非 `lateinit` 可能会导致预料外的问题
 */
@Suppress("UNCHECKED_CAST")
@Disabled
abstract class BaseOpMode<T : Robot>
constructor(opModeName: String? = null) : OpMode() {

    protected val robot: T = createRobot()
    val opModeName: String = opModeName ?: javaClass.simpleName

    private val initializations = robot.takeAll<OpModeLifecycle.Initialize<T>>()
    private val starts = robot.takeAll<OpModeLifecycle.Start>()
    private val actions = robot.takeAll<OpModeLifecycle.Run>()
    private val stops = robot.takeAll<OpModeLifecycle.Stop>()

    private val devices = robot.takeAllDevices().map {
        it.first.dropWhile { name -> name != '.' }.removePrefix(".") to it.second
    }.toMap()

    private val voltageSensor = robot.takeAll<VoltageSensorImpl>()[0]

    private var lastPeriod = -1L

    var period = -1
        private set

    protected var exceptionHandler: (String, Throwable) -> Unit = { lifecycle: String, t: Throwable ->
        RobotLog.setGlobalErrorMsg("用户代码在 <$lifecycle> 抛出了:\n" +
            StringWriter().also { t.printStackTrace(PrintWriter(it)) }.toString())
    }

    private inline fun catchException(lifecycle: String, block: () -> Unit) =
        try {
            block()
        } catch (t: Throwable) {
            exceptionHandler(lifecycle, t)
        }


    private fun Structure.showName() = /*if (this is PackingDevice<*>)
        devices.entries.find { this == it.value }!!.key
    else*/ name

    final override fun init() {
        withMeasuringTime("遍历绑定设备") {
            devices.forEach {
                withMeasuringTime("绑定 ${it.key}") {
                    it.value.bind(hardwareMap, it.key)
                }
            }
        }
        withMeasuringTime("遍历初始化结构") {
            initializations.forEach {
                withMeasuringTime("初始化结构 ${it.showName()}") {
                    it.init(this)
                }
            }
        }
        withMeasuringTime("绑定电压传感器") {
            //            voltageSensor.bind(hardwareMap)   TODO
        }

        catchException("init") {
            //ask for resources
            withMeasuringTime("执行初始化任务") {
                initTask()
            }
        }

        if (enableTimeMeasuring)
            RobotLog.d("完成初始化")
    }

    final override fun init_loop() {
        catchException("init_loop") {
            //adjust sensors (if needed)
            //initialize devices
            withMeasuringTime("执行初始化循环任务") {
                initLoopTask()
            }
        }

        if (enableTimeMeasuring)
            RobotLog.d("完成一次初始化循环")
    }

    final override fun start() {
        withMeasuringTime("遍历重置设备") {
            devices.forEach {
                withMeasuringTime("重置设备 ${it.key}") {
                    it.value.reset()
                }
            }
        }
        catchException("start") {
            withMeasuringTime("重置 Robot") {
                robot.reset()
            }
            withMeasuringTime("遍历执行开始结构") {
                starts.forEach {
                    withMeasuringTime("执行开始结构 ${it.showName()}") {
                        it.start()
                    }
                }
            }
            withMeasuringTime("执行开始任务") {
                startTask()
            }
        }

        if (enableTimeMeasuring)
            RobotLog.d("完成开始")
    }

    final override fun loop() {
        catchException("loop") {
            //calculate suggestions
            //generate and execute commands
            withMeasuringTime("执行循环任务") {
                loopTask()
            }

            withMeasuringTime("遍历执行循环结构") {
                actions.forEach {
                    withMeasuringTime("执行循环结构 ${it.showName()}") {
                        it.run()
                    }
                }
            }
        }

        withMeasuringTime("遍历执行循环设备") {
            devices.forEach {
                withMeasuringTime("执行循环设备 ${it.key}") {
                    it.value.run()
                }
            }
        }

//        withMeasuringTime("执行循环电压传感器") {
//            voltageSensor.run()   TODO
//        }

        period = (System.currentTimeMillis() - lastPeriod).toInt()
        lastPeriod = System.currentTimeMillis()

        if (enableTimeMeasuring)
            RobotLog.d("完成一次循环")
    }

    final override fun stop() {
        catchException("stop") {
            withMeasuringTime("遍历执行停止结构") {
                stops.forEach {
                    withMeasuringTime("执行停止结构 ${it.showName()}") {
                        it.stop()
                    }
                }
            }
        }

        withMeasuringTime("遍历解绑设备") {
            devices.forEach {
                withMeasuringTime("解绑设备 ${it.key}") {
                    it.value.unbind()
                }
            }
        }

//
//        withMeasuringTime("解绑电压传感器") {
//            voltageSensor.unbind()    TODO
//        }

        catchException("stop") {
            //release resources
            withMeasuringTime("执行停止任务") {
                stopTask()
            }
        }

        if (enableTimeMeasuring)
            RobotLog.d("完成停止")
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