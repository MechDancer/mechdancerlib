package org.mechdancer.ftclib.core.structure.monomeric.effector

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * ContinuousServo
 *
 * A kind of device realized power output using pwm.
 */
interface ContinuousServo : Structure {

    /**
     * Power
     *
     * Range: [-1, 1]
     */
    var power: Double

    /**
     * Whether to enable pwm output
     */
    var pwmOutput: Boolean

    /**
     * DSL config
     *
     * @param name name
     * @param enable whether to enable
     */
    class Config(
        name: String,
        enable: Boolean = false
    ) : DeviceConfig(name, enable)

}