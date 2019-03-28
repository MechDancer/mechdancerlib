package org.mechdancer.ftclib.core.opmode.async

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.util.RobotLog
import org.mechdancer.ftclib.algorithm.*
import org.mechdancer.ftclib.core.opmode.OpModeWithRobot
import org.mechdancer.ftclib.core.opmode.RobotFactory.createRobot
import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.core.structure.takeAll
import org.mechdancer.ftclib.internal.impl.sensor.VoltageSensorImpl
import org.mechdancer.ftclib.internal.impl.takeAllDevices
import org.mechdancer.ftclib.util.OpModeLifecycle
import org.mechdancer.ftclib.util.info
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*

@Disabled
abstract class BaseOpModeAsync<T : Robot> : OpModeWithRobot<T>() {

    private val jumpQueue: Queue<State> = LinkedList()

    protected val robot = createRobot()

    private val initializations = robot.takeAll<OpModeLifecycle.Initialize<T>>()
    private val starts = robot.takeAll<OpModeLifecycle.Start>()
    private val actions = robot.takeAll<OpModeLifecycle.Run>()
    private val stops = robot.takeAll<OpModeLifecycle.Stop>()

    private val devices = robot.takeAllDevices().map {
        it.first.dropWhile { name -> name != '.' }.removePrefix(".") to it.second
    }

    private val voltageSensor = robot.takeAll<VoltageSensorImpl>()[0]

    private var lastAsyncPeriod = -1L
    private var lastSyncPeriod = -1L

    var asyncPeriod = -1
        private set
    var syncPeriod = -1
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

    private fun runDevices() {
        devices.forEach { (_, device) ->
            device.run()
        }
        voltageSensor.run()
    }

    internal val core =
        StellateStateMachine {
            jumpQueue.poll()?.also { state = it } ?: state
        }
            .add(State.Initializing) {
                devices.forEach { (name, device) ->
                    info("Binding device: $name")
                    device.bind(hardwareMap, name)
                }
                info("Binding voltage sensor")
                voltageSensor.bind(hardwareMap)
                robot.reset()
                info("Calling init task")
                catchException("init") {
                    initializations.forEach { it.init(this) }
                    initTask.runToFinish()
                }
                info("Initialized")
                jumpQueue.offer(State.InitLoop)
            }
            .add(State.InitLoop) {
                catchException("init_loop") {
                    actions.forEach {
                        it.run()
                    }
                    initLoopMachine.run()
                }
                asyncPeriod = (System.currentTimeMillis() - lastAsyncPeriod).toInt()
                lastAsyncPeriod = System.currentTimeMillis()
            }
            .add(State.Starting) {
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
                    startTask.runToFinish()
                }
                jumpQueue.offer(State.Loop)
            }
            .add(State.Loop) {
                catchException("loop") {
                    actions.forEach {
                        it.run()
                    }
                    if (loopMachine.run() == FINISH) requestOpModeStop()
                }
                asyncPeriod = (System.currentTimeMillis() - lastAsyncPeriod).toInt()
                lastAsyncPeriod = System.currentTimeMillis()
            }
            .add(State.Stopping) {
                stopTask.runToFinish()
                jumpQueue.offer(State.AfterStopLoop)
            }
            .add(State.AfterStopLoop) {
                catchException("after_stop_loop") {
                    actions.forEach {
                        it.run()
                    }
                    runDevices()
                    if (afterStopMachine.run() == FINISH) requestOpModeTerminate()
                }
            }
            .add(State.Terminating) {
                catchException("terminate") {

                    info("Calling terminate task")
                    terminateTask.runToFinish()

                    stops.forEach {
                        info("Calling stop of ${it.name}")
                        it.stop()
                    }
                }

                devices.forEach { (name, device) ->
                    info("Unbinding device: $name")
                    device.unbind()
                }
                info("Unbinding voltage sensor")
                voltageSensor.unbind()

                info("Terminated")
                jumpQueue.offer(State.Terminated)
            }


    internal var state = State.Initializing
        private set

    protected val displayTask = RepeatingLinearStateMachine()
    protected val initTask = LinearStateMachine()
    protected abstract val initLoopMachine: StateMachine
    protected val startTask = LinearStateMachine()
    protected abstract val loopMachine: StateMachine
    protected val stopTask = LinearStateMachine()
    protected abstract val afterStopMachine: StateMachine
    protected val terminateTask = LinearStateMachine()

    enum class State {
        Initializing,
        InitLoop,
        Starting,
        Loop,
        Stopping,
        AfterStopLoop,
        Terminating,
        Terminated
    }

    fun requestOpModeTerminate() {
        jumpQueue.offer(State.Terminating)
    }


    final override fun init() {
        AsyncOpModeController.setup(this)
        displayTask.add { telemetry.addLine().addData("State", state) }
    }

    final override fun init_loop() {
        if (state == State.InitLoop) {
            runDevices()
            displayTask.runToFinish()
        }
        syncPeriod = (System.currentTimeMillis() - lastSyncPeriod).toInt()
        lastSyncPeriod = System.currentTimeMillis()
    }

    final override fun start() {
        jumpQueue.offer(State.Starting)
    }

    final override fun loop() {
        if (state == State.Loop) {
            runDevices()
            displayTask.runToFinish()
        }
        syncPeriod = (System.currentTimeMillis() - lastSyncPeriod).toInt()
        lastSyncPeriod = System.currentTimeMillis()
    }

    final override fun stop() {
        jumpQueue.offer(State.Stopping)
    }
}