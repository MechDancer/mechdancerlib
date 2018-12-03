package org.mechdancer.ftclib.gamepad.component

/**
 * 手柄组件
 *
 * [Button]
 * [Stick]
 * [Trigger]
 */
interface IGamePadComponent<T> {
    /**
     * 值
     */
    var raw: T
}