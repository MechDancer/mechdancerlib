package org.mechdancer.ftclib.core.structure.injector

import org.mechdancer.ftclib.core.structure.Structure
import org.mechdancer.ftclib.core.structure.flatten

object StructureInjector {

	fun inject(structure: Structure) {
		val properties = structure.javaClass.fields.filter {
			it.isAnnotationPresent(Inject::class.java) &&
					Structure::class.java.isAssignableFrom(it.type)
		}.map { it to it.getAnnotation(Inject::class.java) }

		val flatten = structure.flatten()

		properties.forEach { p ->
			val expectName = if (p.second.name == "") p.first.name else p.second.name
			val expectType = p.second.type
			p.first.set(structure, flatten.find {
				(it.name == expectName)
						&& ((expectType == Inject::class) || expectType.isInstance(it))
			}?.let {
				if (!it.javaClass.isAssignableFrom(p.first.type))
					throw IllegalStateException("注入类型错误 期望: ${expectType.simpleName} " +
							"实际: ${p.first.type.simpleName}")
				it
			}
					?: throw IllegalStateException(
							"未找到${if (expectType.simpleName == "Inject") p.first.type.simpleName
							else expectType.simpleName}: $expectName"))
		}
	}

}