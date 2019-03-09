package org.mechdancer.ftclib.core.structure


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
 * Too tree view
 * @param indent indent to recursive
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
 * Operator for [run]
 */
operator fun Structure.invoke() = run()
