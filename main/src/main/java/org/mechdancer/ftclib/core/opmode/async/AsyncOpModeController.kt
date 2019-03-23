package org.mechdancer.ftclib.core.opmode.async

import org.mechdancer.ftclib.algorithm.FINISH
import org.mechdancer.ftclib.algorithm.run
import org.mechdancer.ftclib.util.SmartLogger
import org.mechdancer.ftclib.util.info
import org.mechdancer.ftclib.util.name
import kotlin.concurrent.thread

internal object AsyncOpModeController : SmartLogger {

    @Volatile
    private var current: BaseOpModeAsync<*>? = null

    @Volatile
    private var next: BaseOpModeAsync<*>? = null

    init {
        thread {
            while (true) {
                if (current != null && current!!.core.run() == FINISH) {
                    info("Finish running current: ${current!!.name}")
                    current = null
                }
                if (current == null && next != null) {
                    info("Swap next: ${next!!.name} to current")
                    current = next
                    next = null
                }
            }
        }
    }

    fun setup(next: BaseOpModeAsync<*>) {
        synchronized(this) {
            if (current != null) {
                info("Current op mode: ${current!!.name} is active, try to terminate")
                current!!.requestOpModeTerminate()
            }
            this.next = next
            info("Setup ${next.name}")
        }
    }
}