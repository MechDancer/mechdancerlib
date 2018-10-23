package org.mechdancer.ftclib.gamepad

import com.qualcomm.robotcore.hardware.Gamepad
import org.mechdancer.ftclib.gamepad.component.Button
import org.mechdancer.ftclib.gamepad.component.Stick
import org.mechdancer.ftclib.gamepad.component.Trigger

/**
 * 手柄
 */
class Gamepad {

	//==============
	// 上方区域
	//==============
	/**
	 * 左上条
	 */
	val leftBumper = Button()
	/**
	 * 右上条
	 */
	val rightBumper = Button()

	//==============
	// 按钮区域
	//==============
	val a = Button()
	val b = Button()
	val x = Button()
	val y = Button()

	//==============
	// 方向键区域
	//==============
	/**
	 * 上按钮
	 */
	val up = Button()
	/**
	 * 下按钮
	 */
	val down = Button()
	/**
	 * 左按钮
	 */
	val left = Button()
	/**
	 * 右按钮
	 */
	val right = Button()

	//==============
	// 摇杆区域
	//==============
	/**
	 * 左摇杆
	 */
	val leftStick = Stick()
	/**
	 * 右摇杆
	 */
	val rightStick = Stick()

	//==============
	// 扳机区域
	//==============
	/**
	 * 左扳机
	 */
	val leftTrigger = Trigger()
	/**
	 * 右扳机
	 */
	val rightTrigger = Trigger()

	//===========================
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
