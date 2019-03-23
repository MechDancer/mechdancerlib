package org.mechdancer.ftclib.core.opmode.async

import org.mechdancer.ftclib.algorithm.FINISH
import org.mechdancer.ftclib.algorithm.run
import kotlin.concurrent.thread

internal object AsyncOpModeController {

    @Volatile
    private var current: BaseOpModeAsync<*>? = null

    @Volatile
    private var next: BaseOpModeAsync<*>? = null

    init {
        thread {
            while (true) {
                if (current != null && current!!.core.run() == FINISH)
                    current = null
                if (current == null && next != null) {
                    current = next
                    next = null
                }
            }
        }
    }

    fun setup(next: BaseOpModeAsync<*>) {
        synchronized(this) {
            if (current != null)
                current!!.requestOpModeTerminate()
            this.next = next
        }
    }
}