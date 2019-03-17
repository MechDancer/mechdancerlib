package org.mechdancer.ftclib.algorithm

import java.util.*
import kotlin.collections.HashMap

//Execution
const val FINISH = true
const val CONTINUE = false

//Transfer
const val NEXT = true
const val REPEAT = false

//Definition
typealias StateMember<T> = (() -> T)

typealias StateMachine = StateMember<Boolean>


typealias Shell<TIn, TOut> = ((() -> TIn) -> TOut)

/**
 * Joins [this] and [other] to a new [StateMember]
 */
infix fun <T> StateMember<*>.join(other: StateMember<T>): StateMember<T> = { this(); other() }

/**
 * *Or* operation of state machines, transfer either [this] or [other] finished
 */
operator fun StateMachine.plus(other: StateMachine): StateMachine = {
    val a = this()
    val b = other()
    a || b
}

/**
 * *And* operation of state machines, transfer both [this] and [other] finished
 */
operator fun StateMachine.times(other: StateMachine): StateMachine = {
    val a = this()
    val b = other()
    a && b
}

/**
 * Runs [this] state member
 */
fun <T> StateMember<T>.run(): T? = invoke()

/**
 * Runs [this] state machine to finished
 */
fun StateMachine.runToFinish() {
    while (run() == CONTINUE);
}

/**
 * Step state machine
 *
 * Each state returns which state [StepStateMachine] should transfer to.
 * State members should return themselves if which need to repeat.
 *
 * @param current the origin of step state members
 */
class StepStateMachine(private var current: StateMember<StateMember<*>?>?) : StateMachine {
    override fun invoke(): Boolean {
        @Suppress("UNCHECKED_CAST")
        current = current?.invoke() as StateMember<StateMember<*>?>?
        return if (current == null) FINISH else CONTINUE
    }
}

/**
 * Delay
 *
 * Driven by a [StepStateMachine].
 * An empty state member will keep spinning if it's not time yet.
 */
class Delay(delay: Long) : StateMachine {
    private val timer = MyTimer(delay)
    private var waiting: StateMember<StateMember<*>?> = { null }

    init {
        waiting = { if (timer.isFinished) null else waiting }
    }

    private val driver = StepStateMachine { timer.start(); waiting }

    override fun invoke() = driver()
}

/**
 * Linear state machine
 *
 * State members add to a queue sequentially, discarded when it returns [NEXT],
 * which means transfer to the next state.
 */
open class LinearStateMachine : StateMachine {
    private val states: Queue<StateMember<Boolean>> = LinkedList<StateMember<Boolean>>()

    /**
     * Adds a [state] to [states]
     */
    fun add(state: StateMember<Boolean>): LinearStateMachine =
        apply { states.offer(state) }

    override fun invoke(): Boolean {
        val current = states.peek()
        if (current != null && run(current) == NEXT) states.poll()
        return if (states.peek() == null) NEXT else REPEAT
    }

    /**
     * Jumps [step]s
     *
     * @return if jump is success
     */
    fun jump(step: Int): Boolean {
        for (i in 0 until step) if (states.poll() == null) return FINISH
        return CONTINUE
    }
}

/**
 * Linear state machine with watch dog
 *
 * The only difference between is that each state member has a time limit,
 * which will be forced to transfer when it's timeout.
 */
open class LinearStateMachineWithWatchDog : StateMachine {
    private val driver = LinearStateMachine()

    /**
     * Adds a [state] to queue with [timeout]
     */
    fun add(state: StateMember<Boolean>, timeout: Long): LinearStateMachineWithWatchDog =
        apply {
            driver.add(state + Delay(timeout))
        }

    override fun invoke() = driver()
}

/**
 * Repeatable linear state machine
 *
 * State members in [RepeatingLinearStateMachine] will no be discarded after
 * finishing execution.
 */
open class RepeatingLinearStateMachine : StateMachine {
    private val states: MutableList<StateMember<Unit>> = mutableListOf()
    private var index = 0

    /**
     * Adds a state to [states]
     */
    fun add(state: StateMember<Unit>): RepeatingLinearStateMachine =
        apply { states.add(state) }

    override fun invoke(): Boolean {
        if (states.isEmpty()) return FINISH
        states[index++]()
        index %= states.size
        return if (0 == index) FINISH else CONTINUE
    }

    /**
     * Resets to the first state member
     */
    fun reset() = apply { index = 0 }
}

/**
 * Stellate state state machine
 *
 * State member [core] returns key [T] represented next state,
 * which is added.
 */
class StellateStateMachine<T>(private val core: StateMember<T>) : StateMachine {
    private val states = HashMap<T, StateMember<Unit>>()

    /**
     * Adds a [state] with a specific [key] [T]
     */
    fun add(key: T, state: StateMember<Unit>): StellateStateMachine<T> =
        apply { states[key] = state }

    override fun invoke(): Boolean {
        val currentKey = run(core)
        if (!states.containsKey(currentKey)) return FINISH
        states[currentKey]?.invoke()
        return CONTINUE
    }
}


class StandardStateMachine<T>(private var currentKey: T?) : StateMachine {
    private val states = HashMap<T, StateMember<T>>()

    fun add(key: T, state: StateMember<T>): StandardStateMachine<T> =
        apply { states[key] = state }

    override fun invoke(): Boolean {
        if (!states.containsKey(currentKey)) return FINISH
        currentKey = states[currentKey]?.invoke()
        return CONTINUE
    }
}

/**
 * Named state machine
 *
 * Each state has a specific name, but transfer according to sequentially index.
 */
@Deprecated("Doesn't seem to make sense.", replaceWith = ReplaceWith("LinearSateMachine()"))
class NamedStateMachine : StateMachine {
    private var index = 0

    private val states = HashMap<String, StateMember<Boolean>>()
    private val names = mutableListOf<String>()

    /**
     * Adds a [state] with a [name]
     */
    fun add(name: String, state: StateMember<Boolean>): NamedStateMachine =
        apply {
            names.add(name)
            states[name] = state
        }

    override fun invoke(): Boolean {
        val current = states[names.getOrNull(index)]
        if (current?.run() == true)
            index++
        return if (names.getOrNull(index) == null) NEXT else REPEAT
    }
}

/**
 * Simple timer
 *
 * Provides a timing ability simply.
 */
class MyTimer(private val delay: Long) {
    private var origin: Long = 0


    /**
     * If time is up
     */
    val isFinished: Boolean
        get() = System.currentTimeMillis() - origin > delay

    /**
     * Time to wen timer started
     */
    val value: Long
        get() = System.currentTimeMillis() - origin


    /**
     * Sets to [origin] and starts timer
     */
    fun start(origin: Long = System.currentTimeMillis()) {
        this.origin = origin
    }
}
