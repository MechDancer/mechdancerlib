package org.mechdancer.ftclib.core.structure.injector

import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.Structure
import kotlin.reflect.KProperty
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.jvmErasure

inline operator fun <reified T : Structure> CompositeStructure.getValue(thisRef: Any?, property: KProperty<*>): T {
    val name = property.findAnnotation<Inject>()?.name ?: property.name
    return subStructures.mapNotNull { it as? T }.find { it.name == name }
        ?: throw IllegalStateException("Unable to find [$name: ${property.returnType.jvmErasure.simpleName}]")
}