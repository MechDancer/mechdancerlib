package org.mechdancer.ftclib.core.structure

/**
 * 结构
 */
interface Structure {
	val name: String
	val subStructures: List<Structure>

	operator fun invoke()
	override fun toString(): String
}

/**
 * 展平为列表
 */
internal fun Structure.flatten(): List<Structure> =
		subStructures.flatMap { it.flatten() }.let { it + this }

/**
 * 带父展开
 */
internal fun Structure.flattenWithParent(): Map<Structure, Structure> =
		TODO()

/**
 * 找到所有符合类型参数的结构
 */
internal inline fun <reified T> Structure.takeAll(): List<T> =
		this.flatten().filter { it is T }.map { it as T }

/**
 * 转化为树状图
 * @param indent 缩进
 */
fun Structure.treeView(indent: Int = 0): String {
	val builder = StringBuilder()
	builder.append("$this\n")
	subStructures.dropLast(1).forEach {
		builder
				.append(" |".repeat(indent))
				.append(" ├")
				.append(it.treeView(indent + 1))
	}
	subStructures.takeLast(1).forEach {
		builder
				.append("  ".repeat(indent))
				.append(" └")
				.append(it.treeView(indent + 1))
	}
	return builder.toString()
}

/**
 * 构造匿名Structure
 */
fun structure(name: String = "Unnamed", block: StructureBuilder.() -> Unit) =
		StructureBuilder(name).apply(block).build()

