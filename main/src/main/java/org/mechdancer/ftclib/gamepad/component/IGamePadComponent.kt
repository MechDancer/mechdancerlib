package org.mechdancer.ftclib.gamepad.component

/**
 * Component of gamepad
 *
 * See:
 * [Button],
 * [Stick],
 * [Trigger]
 */
interface IGamePadComponent<T> {

    /**
     * Raw value
     */
    var raw: T

}