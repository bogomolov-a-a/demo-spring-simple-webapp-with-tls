package org.artembogomolova.build.utils

import io.github.classgraph.ClassGraph
import io.github.classgraph.ClassInfo
import java.io.File

class ClassPathClassFounder(buildDir: String) {
    companion object {
        val MAIN_CLASSES_SET: String = "%s/classes/kotlin/main" + File.pathSeparator + "%s/classes/java/main" + File.pathSeparator
    }

    private val searchingPath = MAIN_CLASSES_SET.format(buildDir, buildDir)

    private val classFounder = ClassGraph().overrideClasspath(searchingPath).verbose()
        .enableAllInfo()

    fun getAllClassInfo(action: (classInfo: ClassInfo) -> Unit) {
        classFounder.scan().use { scanResult ->
            scanResult.allClasses.forEach { classInfo -> action(classInfo) }
        }
    }

}