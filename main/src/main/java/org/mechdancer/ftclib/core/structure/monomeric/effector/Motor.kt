package org.mechdancer.ftclib.core.structure.monomeric.effector

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.internal.impl.DeviceConfig

/**
 * Motor
 *
 * A simple device realized power output.
 */
interface Motor : Structure {

    /**
     * Power
     *
     * Range: [-1, 1]
     */
    var power: Double

    /**
     * Logical direction.
     *
     * See [Direction].
     */
    var direction: Direction


    /**
     * Locks this motor
     *
     * Implemented through [com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior].
     */
    var lock: Boolean


    /**
     * DSL config
     *
     * @param name name
     * @param enable whether to enable
     * @param direction direction
     */
    class Config(
        name: String,
        enable: Boolean = false,
        var direction: Direction = Direction.FORWARD
    ) : DeviceConfig(name, enable)

    /**
     * Motor directions
     *
     * This makes it easy to have drive train motors on two sides of a robot.
     */
    enum class Direction(internal val sign: Int) {
        /**
         * Anti-clockwise
         */
        FORWARD(+1),
        /**
         * Clockwise
         */
        REVERSE(-1);

        /**
         * Revers current direction
         */
        fun reverse(): Direction = when (this) {
            FORWARD -> REVERSE
            REVERSE -> FORWARD
        }
    }

}