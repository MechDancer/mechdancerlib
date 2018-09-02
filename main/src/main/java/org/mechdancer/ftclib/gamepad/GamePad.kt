package org.mechdancer.ftclib.gamepad

import com.qualcomm.robotcore.hardware.Gamepad
import org.mechdancer.ftclib.gamepad.components.Button
import org.mechdancer.ftclib.gamepad.components.Stick
import org.mechdancer.ftclib.gamepad.components.Trigger


class GamePad {
	val leftBumper = Button()
	val rightBumper = Button()

	val a = Button()
	val b = Button()
	val x = Button()
	val y = Button()

	val up = Button()
	val down = Button()
	val left = Button()
	val right = Button()

	val leftStick = Stick()
	val rightStick = Stick()

	val leftTrigger = Trigger()
	val rightTrigger = Trigger()

	//===========================
	fun update(origin: Gamepad) {
		leftBumper.value = origin.left_bumper
		rightBumper.value = origin.right_bumper

		a.value = origin.a
		b.value = origin.b
		x.value = origin.x
		y.value = origin.y
		up.value = origin.dpad_up
		down.value = origin.dpad_down
		left.value = origin.dpad_left
		right.value = origin.dpad_right

		leftTrigger.value = origin.left_trigger.toDouble()
		rightTrigger.value = origin.right_trigger.toDouble()

		leftStick.value = doubleArrayOf(
				origin.left_stick_x.toDouble(),
				origin.left_stick_y.toDouble())
		rightStick.value = doubleArrayOf(
				origin.right_stick_x.toDouble(),
				origin.right_stick_y.toDouble())
	}
}
