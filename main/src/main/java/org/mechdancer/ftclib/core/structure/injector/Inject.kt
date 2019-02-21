package org.mechdancer.ftclib.core.structure.injector

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
/**
 * 注入子结构成员
 *
 * @param name 子结构名字 空代表使用定义的成员名，`#ignore#` 代表忽略名字匹配。
 * @param type 子结构类型 为 `Inject::class` 时代表使用定义的成员类型
 */
annotation class Inject(val name: String = "", val type: KClass<*> = Inject::class)