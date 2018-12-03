package org.mechdancer.ftclib.core.structure.composite.chassis

import org.mechdancer.ftclib.core.structure.monomeric.effector.Motor
import kotlin.math.cos
import kotlin.math.sin

abstract class Omnidirectinal
(motorsConfig: Array<Pair<String, Motor.Direction>>, enable: Boolean)
    : Chassis(motorsConfig, enable) {
    data class Descartes(var x: Double,
                         var y: Double,
                         var w: Double) {
        operator fun times(other: Descartes) =
                Descartes(x * other.x, y * other.y, w * other.w)
    }

    data class Polar(var rho: Double = .0,
                     var theta: Double = .0,
                     var omiga: Double = .0) {
        val block: Descartes.() -> Unit = {
            x = rho * cos(theta)
            y = rho * sin(theta)
            w = omiga
        }
    }

    data class TankMode(var left: Double = .0,
                        var right: Double = .0,
                        var horizon: Double = .0) {
        val block: Descartes.() -> Unit = {
            x = left + right
            y = horizon
            w = left - right
        }
    }

    //方向、权重
    private val descartes = Descartes(.0, .0, .0)
    private val weights = Descartes(1.0, 1.0, 1.0)

    //笛卡尔模式
    fun descartes(block: Descartes.() -> Unit) =
            descartes.run(block)

    //极坐标模式
    fun polar(block: Polar.() -> Unit) =
            descartes.run(Polar().apply(block).block)

    //坦克模式
    fun tank(block: TankMode.() -> Unit) =
            descartes.run(TankMode().apply(block).block)

    /**
     * DSL：设定权重
     */
    fun weights(block: Descartes.() -> Unit) =
            weights.run(block)

    /**
     * 高级控制模式（接管powers）
     */
    var advancedControlMode = false

    /**
     * 将方向分配到各电机
     */
    protected abstract fun Descartes.transform(): DoubleArray

    override fun run() {
        if (!advancedControlMode)
            powers = (weights * descartes).transform()
        super.run()
    }
}