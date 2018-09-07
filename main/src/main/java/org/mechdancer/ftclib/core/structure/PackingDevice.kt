package org.mechdancer.ftclib.core.structure

import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.HardwareMap
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 设备
 * @param name 名字
 * @param enable 使能
 */
sealed class PackingDevice<in T : HardwareDevice>
(name: String, val enable: Boolean) : MonomericStructure(name) {

	/**
	 * 真名
	 */
	private val id get() = "$prefix$name"

	/**
	 * 对真实设备的引用
	 */
	private var device: T? = null

	/**
	 * 绑定设备
	 */
	internal fun bind(hardwareMap: HardwareMap) {
		@Suppress("UNCHECKED_CAST")
		if (enable) device = hardwareMap[id] as T
	}

	/**
	 * 解除设备绑定
	 */
	internal fun unbind()  { device = null }

	/**
	 * 操作是否有效
	 */
	val available get() = enable && device != null

	/**
	 * 操作是否完成
	 */
	internal val ready get() = _ready.get()

	/**
	 * 操作是否完成
	 */
	private var _ready = AtomicBoolean(true)

	/**
	 * 最后更新时间
	 */
	var lastUpdateTime = System.currentTimeMillis()
		private set

	/**
	 * 配置操作
	 */
	protected open fun T.config() = Unit

	/**
	 * 输入操作（传感器）
	 */
	protected abstract fun T.input()

	/**
	 * 输出操作（动力设备、灯）
	 */
	protected abstract fun T.output()

	/**
	 * 重置操作（重置设备）
	 */
	protected abstract fun T.reset()

	/**
	 * 有重置操作待执行
	 */
	private var resetRequest = AtomicBoolean(false)

	/**
	 * 重置操作（清除数据）
	 */
	protected abstract fun resetData()

	/**
	 * 重置操作
	 */
	fun reset() {
		if (resetRequest.compareAndSet(false, true))
			resetData()
	}

	/**
	 * 执行所有传递指令的操作
	 */
	final override fun run() {
		//设备不可用，跳出
		if (!available) return
		//等待设备上一轮传递完成
		while (!_ready.compareAndSet(true, false))
			Thread.yield()
		//记录更新时间
		lastUpdateTime = System.currentTimeMillis()
		logger.info("$name: updating")
		//在新线程里执行
		threadPool.execute {
			if (resetRequest.compareAndSet(true, false)) {
				//               //==请求重置，则：
				device?.reset()  //  重置
				device?.input()  //  读取数据
			} else {             //==否则
				device?.config() //  修改配置
				device?.input()  //  读取
				device?.output() //  输出
			}                    //==
			_ready.set(true)     //  置状态：传递完成
		}
	}

	/**
	 * （需要由外部设置的）设备属性
	 * @param tag 属性名字，写日志用
	 * @param origin 初始值
	 * @param setter 发送指令的方法
	 * @param isValid 判断数据是否合理
	 */
	protected inner class PropertyBuffer<U>(
			private val tag: String,
			origin: U,
			private val setter: T.(U) -> Unit,
			private val isValid: (U) -> Boolean = { true }) : ReadWriteProperty<Any?, U> {
		override fun getValue(thisRef: Any?, property: KProperty<*>): U = value

		override fun setValue(thisRef: Any?, property: KProperty<*>, value: U) {
			this.value = value
		}

		private var changed = AtomicBoolean(false)

		fun sendTo(device: T) {
			if (changed.compareAndSet(true, false))
				setter(device, value)
		}

		var value = origin
			set(newValue) {
				if (!isValid(newValue)) {     //新值不合理
					logger.warning("$name/$tag: property value is not valid($newValue)")
					return
				}
				if (field == newValue) return //与之前完全相同
				field = newValue              //更新
				changed.set(true)             //记录
			}
	}

	companion object {
		/**
		 * 设备数量
		 */
		var count = 0
			set(value) {
				field = value
				threadPool.shutdown()
				if (value > 0)
					threadPool = Executors.newFixedThreadPool(value)
			}
		/**
		 * 执行用线程池
		 */
		private var threadPool = Executors.newFixedThreadPool(1)

		/**
		 * 日志
		 */
		private val logger = Logger.getLogger("PackingDevice")

		/**
		 * 名字前缀，与使用此设备的机器人一致
		 */
		internal var prefix = "$"

		/**
		 * 输出抑制级别
		 */
		var logLevel: Level
			get() = logger.level
			set(value) {
				logger.level = value
			}
	}
}

/**
 * 输出设备
 */
abstract class Device<in T : HardwareDevice>
(name: String, enable: Boolean)
	: PackingDevice<T>(name, enable) {
	final override fun T.input() = Unit
	final override fun T.reset() = output()
}

/**
 * 传感器
 */
abstract class Sensor<in T : HardwareDevice>
(name: String, enable: Boolean)
	: PackingDevice<T>(name, enable) {
	final override fun T.output() = Unit
}