package org.mechdancer.ftclib.core.structure.monomeric.effector

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * Servo
 *
 * A simple device realized position output using pwm.
 */
interface Servo : Structure {
    /**
     * Position
     *
     * Range: [origin, ending]
     */
    var position: Double

    /**
     * Whether to enable pwm output
     */
    var pwmOutput: Boolean

    /**
     * DSL config
     *
     * @param name name
     * @param enable whether to enable
     * @param origin initial position (rad)
     * @param ending ending position (rad)
     */
    class Config(
        name: String,
        enable: Boolean = false,
        var origin: Double = .0,
        var ending: Double = .0
    ) : DeviceConfig(name, enable)
}