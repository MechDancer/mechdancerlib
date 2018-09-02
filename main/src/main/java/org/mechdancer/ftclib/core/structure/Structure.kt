package org.mechdancer.ftclib.core.structure

/** 结构 */
interface Structure {
	val name: String

	fun run()
	override fun toString(): String
}

/** 复合结构 */
interface CompositeStructure : Structure {
	val subStructures: List<Structure>
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
internal fun CompositeStructure.flatten(): List<CompositeStructure> =
	subStructures.flatMap { (it as? CompositeStructure)?.flatten() ?: listOf() }
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
 * 构造匿名Structure
 */
inline fun structure(name: String = "Unnamed", block: StructureBuilder.() -> Unit) =
	StructureBuilder(name).apply(block).build()

/**
 * 为 `run` 提供语法糖
 */
operator fun Structure.invoke() = run()
