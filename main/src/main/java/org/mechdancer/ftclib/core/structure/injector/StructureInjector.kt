package org.mechdancer.ftclib.core.structure.injector

import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.flatten

object StructureInjector {

	fun inject(structure: CompositeStructure) {
		val properties = structure.javaClass.declaredFields.filter {
			it.isAnnotationPresent(Inject::class.java)
					&& Structure::class.java.isAssignableFrom(it.type)
		}.map { it to it.getAnnotation(Inject::class.java) }

		val flatten = structure.flatten()
		println(flatten.joinToString())
		properties.forEach { p ->
			p.first.isAccessible = true
			val expectName = if (p.second.name == "") p.first.name else p.second.name
			val expectType = if (p.second.type == Inject::class)
				p.first.type else p.second.type.java
			p.first.set(structure, flatten.find {
				(it.name == expectName) && (expectType.isInstance(it))
			}?.let {
				if (!(it.javaClass.isAssignableFrom(p.first.type) || p.first.type.isAssignableFrom(it.javaClass)))
					throw IllegalStateException("注入类型错误 期望: ${expectType.simpleName} " +
							"实际: ${p.first.type.simpleName}")
				it
			}
					?: throw IllegalStateException("未找到 [$expectName: ${expectType.name}]"))
		}
	}

}