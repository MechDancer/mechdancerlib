package org.mechdancer.ftclib.core.structure.injector

import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.Structure

/**
 * Structure injector
 *
 * See [Inject].
 */
object StructureInjector {

    /**
     * Uses [structure]'s substructures to inject [structure].
     */
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
							Inject type error:
							Expected: ${expectType.simpleName}
							Actual: ${p.first.type.simpleName}
						""".trimIndent())
                }
                ?: throw IllegalStateException("Unable to find [$expectName: ${expectType.name}]")
            p.first.set(structure, result)
        }
    }
}
