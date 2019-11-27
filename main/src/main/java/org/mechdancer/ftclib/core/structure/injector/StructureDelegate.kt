package org.mechdancer.ftclib.core.structure.injector

import org.mechdancer.ftclib.core.structure.CompositeStructure
import org.mechdancer.ftclib.core.structure.Structure
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.jvm.jvmName


inline fun <reified T : Structure> CompositeStructure.delegate(childName: String? = null) =
    object : ReadOnlyProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            val name = childName ?: property.name
            return subStructures.mapNotNull { it as? T }.find { it.name == name }
                ?: throw IllegalStateException("Unable to find [$name: ${property.returnType.jvmErasure.jvmName}]")
        }
    }