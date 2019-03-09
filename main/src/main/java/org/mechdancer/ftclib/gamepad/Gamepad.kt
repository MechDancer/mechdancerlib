package org.mechdancer.ftclib.gamepad

import com.qualcomm.robotcore.hardware.Gamepad
import org.mechdancer.ftclib.gamepad.component.Button
import org.mechdancer.ftclib.gamepad.component.Stick
import org.mechdancer.ftclib.gamepad.component.Trigger

/**
 * Gamepad
 */
class Gamepad {

    //==============
    // Top area
    //==============
    /** Left bumper */
    val leftBumper = Button()
    /** Right bumper */
    val rightBumper = Button()

    //==============
    // Button area
    //==============
    /** Button a */
    val a = Button()
    /** Button b */
    val b = Button()
    /** Button x */
    val x = Button()
    /** Button y */
    val y = Button()

    //==============
    // Arrow area
    //==============
    /** Arrow up */
    val up = Button()
    /** Arrow down */
    val down = Button()
    /** Arrow left */
    val left = Button()
    /** Arrow right */
    val right = Button()

    //==============
    // Stick area
    //==============
    /** Left stick */
    val leftStick = Stick()
    /** Right stick */
    val rightStick = Stick()

    //==============
    // Trigger area
    //==============
    /** Left trigger */
    val leftTrigger = Trigger()
    /** Right trigger */
    val rightTrigger = Trigger()

    internal fun update(origin: Gamepad) {
        leftBumper.raw = origin.left_bumper
        rightBumper.raw = origin.right_bumper

        a.raw = origin.a
        b.raw = origin.b
        x.raw = origin.x
        y.raw = origin.y
        up.raw = origin.dpad_up
        down.raw = origin.dpad_down
        left.raw = origin.dpad_left
        right.raw = origin.dpad_right

        leftTrigger.raw = origin.left_trigger.toDouble()
        rightTrigger.raw = origin.right_trigger.toDouble()

        leftStick.raw = doubleArrayOf(
            origin.left_stick_x.toDouble(),
            origin.left_stick_y.toDouble())
        rightStick.raw = doubleArrayOf(
            origin.right_stick_x.toDouble(),
            origin.right_stick_y.toDouble())
    }
}
