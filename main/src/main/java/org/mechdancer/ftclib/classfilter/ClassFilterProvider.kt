package org.mechdancer.ftclib.classfilter

import org.firstinspires.ftc.robotcore.internal.opmode.ClassFilter

object ClassFilterProvider : ClassFilterAdapter() {
    private val filters = mutableSetOf<ClassFilter>()

    private val classes = mutableListOf<Class<*>>()

    override fun filterClass(clazz: Class<*>) {
        if (clazz == javaClass) return
        if (clazz.isAnnotationPresent(FilteringClass::class.java) &&
            ClassFilterAdapter::class.java.isAssignableFrom(clazz))
            filters.add(clazz.newInstance() as ClassFilter)
        else classes.add(clazz)
    }

    override fun filterAllClassesComplete() {
        filters.forEach { filter ->
            filter.filterAllClassesStart()
            classes.forEach {
                filter.filterClass(it)
            }
            filter.filterAllClassesComplete()
        }
    }
}