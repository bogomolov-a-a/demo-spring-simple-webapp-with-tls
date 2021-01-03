package org.artembogomolova.build.plugins

import io.github.classgraph.ClassGraph
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstylePlugin
import org.gradle.api.plugins.quality.Pmd
import org.gradle.api.plugins.quality.PmdPlugin
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jacoco.core.analysis.ICounter
import org.jacoco.core.analysis.ICoverageNode
import java.io.File
import java.math.BigDecimal

class CommonJavaPlugin : Plugin<Project> {
    companion object CommonJavaPluginObject {
        const val JDK_VERSION: String = "11"
    }

    private val repositoryApplier: Plugin<Project> = RepositoryApplier()
    private val javaPluginApplier: JavaPluginApplier = JavaPluginApplier()
    private val staticAnalysisApplier: StaticAnalysisApplier = StaticAnalysisApplier()

    private val jacocoApplier: JacocoPluginApplier = JacocoPluginApplier()
    override fun apply(target: Project) {
        repositoryApplier.apply(target)
        javaPluginApplier.apply(target)
        staticAnalysisApplier.apply(target)
        jacocoApplier.apply(target)

    }
}


internal abstract class PluginApplier<T : Plugin<out Any>>(private val pluginClass: Class<T>) : Plugin<Project> {
    final override fun apply(target: Project) {
        target.plugins.apply(pluginClass)
        configureTasks(target)
    }

    protected open fun configureTasks(target: Project) {
        //default no tasks
    }
}

internal class JavaPluginApplier :PluginApplier<JavaPlugin>(JavaPlugin::class.java){
    override fun configureTasks(target: Project) {
        configureJavaCompileTask(target)
    }

    private fun configureJavaCompileTask(target: Project) {
        target.tasks.withType(JavaCompile::class.java) {
            sourceCompatibility = CommonJavaPlugin.JDK_VERSION
            targetCompatibility = CommonJavaPlugin.JDK_VERSION
        }
    }

}

internal class StaticAnalysisApplier : Plugin<Project> {
    private val checkstyleApplier: CheckstyleApplier = CheckstyleApplier()
    private val pmdApplier: PmdApplier = PmdApplier()
    override fun apply(target: Project) {
        checkstyleApplier.apply(target)
        pmdApplier.apply(target)
    }
}

internal class PmdApplier : PluginApplier<PmdPlugin>(PmdPlugin::class.java) {
    override fun configureTasks(target: Project) {
        configurePmdTask(target)
    }

    private fun configurePmdTask(target: Project) {
        target.tasks.withType(Pmd::class.java) {
            isConsoleOutput = true
        }
    }

}

internal class CheckstyleApplier : PluginApplier<CheckstylePlugin>(CheckstylePlugin::class.java) {

    override fun configureTasks(target: Project) {
        configureCheckStyleTask(target)
    }

    private fun configureCheckStyleTask(target: Project) {
        target.tasks.withType(Checkstyle::class.java) {
            isIgnoreFailures = false
            isShowViolations = true
        }
    }

}

internal class RepositoryApplier : Plugin<Project> {
    override fun apply(target: Project) {
        target.repositories.add(target.repositories.mavenCentral())
    }

}

internal class JacocoPluginApplier : PluginApplier<JacocoPlugin>(JacocoPlugin::class.java) {
    companion object Companion {
        val TEST_CLASS_NAME_PATTERNS = listOf("*Test")
        const val CLASS_DUMP_DIR_RELATIVE_PATH = "/jacoco/classpathdumps"
        const val JACOCO_XML_REPORT_FILE_PATH = "/reports/jacoco/jacoco.xml"
        const val COVERAGE_RATIO_MINIMUM = 0.7
        const val JAVA_MAIN_CLASSES_SET = "/classes/java/main"
    }

    override fun configureTasks(target: Project) {
        configureJacocoTestIntegrationTask(target)
        configureJacocoReportTask(target)
        configureJacocoVerificationTask(target)
    }

    private fun configureJacocoTestIntegrationTask(target: Project) {
        fun isAvailableToExclude(clazzInfo: io.github.classgraph.ClassInfo): Boolean = clazzInfo.name.endsWith("_")
        fun findCoverageClasses(target: Project): Pair<List<String>, List<String>> {
            val includes = ArrayList<String>()
            val excludes = ArrayList<String>()
            val result = Pair<List<String>, List<String>>(includes, excludes)

            val searchingPath = target.buildDir.absolutePath + JAVA_MAIN_CLASSES_SET
            println("try to search includes and excludes in '$searchingPath' for project '${target.name}' ")
            ClassGraph()
                .overrideClasspath(searchingPath)
                .verbose()
                .enableAllInfo()
                .scan().use { scanResult ->
                    scanResult.allClasses.forEach { clazzInfo ->
                        if (isAvailableToExclude(clazzInfo!!)) {
                            excludes.add(clazzInfo.name)
                        } else {
                            includes.add(clazzInfo.name)
                        }
                    }
                }
            println("found includes: ${result.first}")
            println("found excludes: ${result.second}")
            return result
        }


        target.tasks.withType(Test::class.java) {
            useJUnitPlatform()
            failFast = true
            ignoreFailures = false
            setTestNameIncludePatterns(TEST_CLASS_NAME_PATTERNS)
            doFirst {
                extensions.configure(JacocoTaskExtension::class.java) {
                    this.classDumpDir = File("${target.buildDir}${CLASS_DUMP_DIR_RELATIVE_PATH}")
                    val coverageClasses = findCoverageClasses(target)
                    this.includes = coverageClasses.first
                    this.excludes = coverageClasses.second
                }
            }
        }

    }

    private fun configureJacocoVerificationTask(target: Project) {
        target.tasks.withType(JacocoCoverageVerification::class.java) {
            violationRules {
                println("start creating rules")
                rule {
                    this.limit {
                        counter = ICoverageNode.CounterEntity.METHOD.name
                        value = ICounter.CounterValue.COVEREDRATIO.name
                        minimum = BigDecimal.valueOf(COVERAGE_RATIO_MINIMUM)
                    }
                }
                rule {
                    this.limit {
                        counter = ICoverageNode.CounterEntity.CLASS.name
                        value = ICounter.CounterValue.COVEREDRATIO.name
                        minimum = BigDecimal.valueOf(COVERAGE_RATIO_MINIMUM)
                    }
                }
            }
        }

    }

    private fun configureJacocoReportTask(target: Project) {
        target.tasks.withType(JacocoReport::class.java) {
            reports {
                /*only XML reports*/
                csv.isEnabled = false
                html.isEnabled = false
                xml.isEnabled = true
                xml.destination = File("${target.buildDir.absolutePath}${JACOCO_XML_REPORT_FILE_PATH}")
            }
        }
    }

}