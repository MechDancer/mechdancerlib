package org.mechdancer.ftclib.core.structure.injector

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
/**
 * Inject substructure to a member
 *
 * [name] is the name of substructure, if it is `""`, injector will use member's name,
 * if it is `#ignore#`, injector will ignore name matching.
 * [type] is the type of substructure, if it is `Inject::class`, injector will use member's type.
 */
annotation class Inject(val name: String = "", val type: KClass<*> = Inject::class)