package org.mechdancer.ftclib.classfilter

import org.firstinspires.ftc.robotcore.internal.opmode.ClassFilter

abstract class ClassFilterAdapter : ClassFilter {
    override fun filterAllClassesComplete() {

    }

    override fun filterAllClassesStart() {

    }


    override fun filterOnBotJavaClassesStart() {

    }

    override fun filterOnBotJavaClassesComplete() {

    }

    override fun filterOnBotJavaClass(clazz: Class<*>?) {
    }
}