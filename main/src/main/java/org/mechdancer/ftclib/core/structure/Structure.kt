package org.mechdancer.ftclib.core.structure

interface Structure{
	val name:String

	fun run()

	override fun toString():String
}

/** 结构 */
sealed class StructureSealed(override val name: String):Structure

/**
 * 单体结构
 */
abstract class MonomericStructure(name: String) : StructureSealed(name)

/**
 * 复合结构
 */
abstract class CompositeStructure(name: String) : StructureSealed(name) {
	/**
	 * 子结构
	 */
	abstract val subStructures: List<Structure>
}

/**
 * 其中接口标识参与 OpMode 标准状态流程，会被 OpMode 调用
 */
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

/**
 * 展平为列表
 */
internal fun CompositeStructure.flatten(): List<Structure> =
		subStructures.flatMap { (it as? CompositeStructure)?.flatten() ?: listOf(it) }
				.let { it + this }

/**
 * 找到所有符合类型参数的结构
 */
internal inline fun <reified T> CompositeStructure.takeAll(): List<T> =
		this.flatten().filter { it is T }.map { it as T }

/**
 * 转化为树状图
 * @param indent 缩进
 */
fun CompositeStructure.treeView(indent: Int = 0): String {
	val builder = StringBuilder()
	builder.append("$this\n")
	subStructures.dropLast(1).forEach {
		builder
				.append(" |".repeat(indent))
				.append(" ├")
				.append((it as? CompositeStructure)?.treeView(indent + 1))
	}
	subStructures.takeLast(1).forEach {
		builder
				.append("  ".repeat(indent))
				.append(" └")
				.append((it as? CompositeStructure)?.treeView(indent + 1))
	}
	return builder.toString()
}

/**
 * 构造匿名 Structure
 */
inline fun structure(name: String = "Unnamed", block: StructureBuilder.() -> Unit) =
		StructureBuilder(name).apply(block).build()

/**
 * 为 `run` 提供语法糖
 */
operator fun Structure.invoke() = run()
