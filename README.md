# MechDancer Lib

由 Kotlin 语言编写的 FTC 机器人程序库



对于 FTC 比赛而言，FIRST 官方提供的库虽然易用（脱离了嵌入式开发范畴），但有些过于简单。在比赛程序的编写上，往往不能优雅的解决一些常用的需求。为了解决该问题，我们换了一种思路，重新定义了从单个设备到机器人整体。我们尽力保证库的独立性，使其不仅应用于 FTC 赛事机器人程序编写。不过为了在 FTC 程序编写中本库拆封即用，我们依赖了 RobotCore 中 `HardwareDevice` 和 `OpMode`。

## Structure

在库中，我们抽象出了 *结构* 这个概念。结构分为两种 —— 单体结构、复合结构。

### 单体结构

一个单体结构十分简单，它的定义如下：

```kotlin
interface Structure {
	val name: String

	fun run()
	override fun toString(): String
}
```

它包含了名字，和一个 `run()` 方法，以及 `toString()`。拥有这些特性的事物都可被看作一个单体结构，例如一个电机。

### 复合结构

复合结构与单体结构的不同之处在于复合结构可以拥有属于它的子结构：

```kotlin
interface CompositeStructure : Structure {
	val subStructures: List<Structure>
}
```

相比于单体结构，它多出了 `subStructures`。一个麦克纳姆底盘就是一个复合结构 —— 它的子结构为四个电机。

## 设备

FIRST 提供的设备封装已经十分完善，我们对其进一步封装，使其适配了库中的其他部分。如上文所述一切设备皆为 Structure。`PackingDevice` 类似一个 Wrapper，持有着真实设备的引用。该引用会在 OpMode 调用 `init()` 时绑定。同时，它将设备的 IO 操作分离，定义出子类 `Sensor`（只有 I）、`Device`（只有 O）。每一个设备的 `run()`（也就是 IO 操作）都是异步执行的，在我们的可控范围内（忽略底层通讯带来的同步问题）尽量保证程序的高效运行。除此之外，我们使用了 `PropertyBuffer` 保证只有设备状态真正需要改变时（而不是在循环中为某个重复设定一个相同的功率）才会向 FIRST 的设备引用发送指令。

### Device

#### 电机

根据电机行为定义：

```kotlin
interface Motor : Structure {
	var power: Double

	var direction: DcMotorSimple.Direction
}
```

一个电机技能实现以上两种行为 —— 赋值功率和设定方向。

#### 舵机

```kotlin
interface Servo : Structure {
	var position: Double
}
```

对于舵机也是同样的道理，上层用户只需关心它的位置，也就是转到的角度。

#### 连续舵机

```kotlin
interface ContinuousServo : Structure {
   var power: Double

   class Config(
         name: String,
         enable: Boolean = false
   ) : DeviceConfig(name, enable)
}
```

与电机相比，连续舵机只是少了方向。

### Sensor

#### Encoder

编码器的行为定义：

```kotlin
interface Encoder : Structure {
	fun getPosition(): Double

	fun getSpeed(): Double

	fun reset(off: Double)
}
```

在 FIRST 提供的设备中，并没有单独分离编码器这一概念，而是将其归于 `DCMotor` 之中。我们认为这是设计的不合理之处，编码器可能单独出现，与电机无关。我们将编码器单独归于传感器，但为了正常使用，它内部的设备引用依然是 FIRST 的电机。

#### Rev Color Sensor

```kotlin
interface RevColorSensor : Structure {
	fun getColorData(): ColorData

	fun enableLed(boolean: Boolean)
}
```

十分简单，不再赘述。

## Robot

若把整个机器人结构作为一棵树，`Robot` 就是其根部。它将所有子结构包含在其中，叶子就是最底层的设备。库中 `Robot` 是这样定义的：

```kotlin
abstract class Robot(name: String, vararg subStructs: Structure)
	: AbstractStructure(name, {
	subStructs.forEach { subStructure(it) }
}) {
	abstract val chassis: Chassis
}
```

在这里我们仅仅关注它唯一的抽象成员 `chassis`。这要求了一个机器人必须要有一个底盘（尽管有些特例）。我们一起设想一下，这是一台具有一个单电机机械臂、一个麦克纳姆底盘的简单机器人。机械臂是一个复合结构，它包含一个子结构 —— 那个电机。麦克纳姆底盘也是同样的道理。Robot 对于其子结构是上层的。这意味着上层不应该直接控制电机等设备，而是告诉自己的子结构此时应处于何种状态以及如何运行。举个例子：机械臂结构有三种状态 —— 下降、平行、上升。Robot 仅仅告诉它当前状态应该是什么，控制那个电机转到哪种角度则有机械臂结构负责。

## 一些定义好的结构

### Chassis

```kotlin
interface Chassis : CompositeStructure {
	var powers: DoubleArray

	var maxPower: Double
}
```

底盘仅仅包含以上两种行为，这有些过于抽象。我们给出了最简单的底盘实现 `ChassisImpl`。同时，我们定义了全向底盘 `Omnidirectinal` 它拥有抽象函数 `Descartes.transform()`，将最终结果映射为电机的功率并输出。在库中目前它仅有一个实现 —— `Mecanum` 麦克纳姆底盘。

### Motor With Encoder

如前文所述，我们将电机和编码器根据行为拆分成两个部分。虽然这是符合逻辑的行为，不过在某些情况下带来了许多不便。电机 x 编码器 —— `MotorWithEncoder` 就这样诞生了。它是一个复合结构，子结构为一个电机和一个编码器。在设计之初，我们思考了很久这个类是否应该多态。换句话说 `MotorWithEncoder` 是否可以强转为 `Motor` 或 `Encoder`？（也就是具有以上二者的行为）听起来这是正确的，但这的确是个特例。思考一下，仅有一个电机的机械臂是否可以强转为电机？`MotorWithEncoder` 是二者纯粹的组合，恰恰完全拥有了其二者的行为，使多态变得合理。但这实际上是错误的。不过为了约定行为，我们还是让 `MotorWithEncoder` 实现了 `Motor` 和 `Encoder` 结构，将其代理给它的两个子结构。**~~真香~~** 是时候看一下 `MotorWithEncoder` 的行为定义了：

```kotlin
interface MotorWithEncoder : Motor, Encoder {
   var mode: Mode

   var targetSpeed: Double

   var targetPosition: Double


   enum class Mode { SPEED_CLOSE_LOOP, OPEN_LOOP, POSITION_CLOSE_LOOP, LOCK, STOP }

   class Config(name: String, enable: Boolean = false,
                var radians: Double = .0,
                var pidPosition: PID = PID(1.0, .0, .0, .0, .0),
                var pidSpeed: PID = PID(1.0, .0, .0, .0, .0)) : DeviceConfig(name, enable)

}
```

它除了具有 Motor 和 Encoder 的全部功能外，二者的奇妙组合带来了一些新的特性 —— 闭环。电机和编码器的组合使一个电机在运行中具有了反馈。这样可以实现闭位置环、闭速度环，以及十分特殊的一个状态 —— 锁定。

## 手柄

为了给手柄增加一些的功能，我们重新实现了手柄结构。很奇怪，手柄与机器人身上的传感器没有什么本质区别，但 FIRST 还是将其完全分开。我们为按钮添加了三种判断状态 —— `bePressed()` 当前按钮是不是处于按下状态、`isPressing()` 按钮是否处于由未按下到按下，并且现在还在按下、`isReleasing()` 始终没有按下。除按钮外，我们还对摇杆实现了手感系数（非线性映射）。~~不过没什么卵用~~

## OpMode

我们定义了抽象类 `BaseOpMode`，继承 FIRST 提供的 `OpMode` 作为程序入口。在其中，我们将 `HardwareMap` 提供的设备绑定到我们定义的设备中，并对一些设备和结构调用了 `init()`,`run()` 等函数。一个 OpMode 接收一个 Robot 实例，用于控制定义好的机器人。

### OpModeFlow

```kotlin
object OpModeFlow {
	/**
	 *参与 OpMode 的 `init()`
	 */
	interface Initialisable {
		fun init()
	}

	/**
	 *参与 OpMode 的 `loop()`
	 */
	interface AutoCallable {
		fun run()
	}

	/**
	 *参与 OpMode 的 `stop()`
	 */
	interface Stoppable {
		fun stop()
	}

}
```

Structure 的 `run()` 理应由自己的父结构调用，直至最上层的 Robot 由 OpMode 调用。在大部分情况下，从最底层的设备到上层的机器人结构，如果一层一层向上传递难免过于繁琐，并且可能会出现错误。我们定义了这三个接口，作为一种标记（类似 `Serializable`）。实现这三个接口的 Structure 会直接参与到 OpMode 相应的生命周期之中，直接被 OpMode 调用。