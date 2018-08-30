package org.mechdancer.ftclib.core.structure.injector

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Inject(val name: String = "", val type: KClass<*> = Inject::class)