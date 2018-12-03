package org.mechdancer.ftclib.core.structure.injector

import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.Structure

/**
 * 子结构注入器
 * 与 [Inject] 配合使用
 */
object StructureInjector {

    fun inject(structure: CompositeStructure) {
        val clazz = structure::class.java
        val properties = clazz.declaredFields.filter {
            it.isAnnotationPresent(Inject::class.java)
                    && Structure::class.java.isAssignableFrom(it.type)
        }.map { it to it.getAnnotation(Inject::class.java) }
        if (properties.isEmpty()) return
        properties.forEach { p ->
            p.first.isAccessible = true
            val ignoreName = p.second.name == "#ignore#"
            val expectName =
                    if (p.second.name == "") p.first.name
                    else p.second.name
            val expectType =
                    if (p.second.type == Inject::class) p.first.type
                    else p.second.type.java
            val result = structure.subStructures
                    .find { (it.name == expectName || ignoreName) && (expectType.isInstance(it)) }
                    ?.let {
                        if (it.javaClass.isAssignableFrom(p.first.type)
                                || p.first.type.isAssignableFrom(it.javaClass))
                            it
                        else
                            throw IllegalStateException("""
							注入类型错误
							预期: ${expectType.simpleName}
							实际: ${p.first.type.simpleName}
						""".trimIndent())
                    }
                    ?: throw IllegalStateException("未找到 [$expectName: ${expectType.name}]")
            p.first.set(structure, result)
        }
    }
}
