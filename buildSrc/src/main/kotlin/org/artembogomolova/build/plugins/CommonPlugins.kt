package org.artembogomolova.build.plugins

import io.github.classgraph.ClassGraph
import io.github.classgraph.ClassInfo
import org.artembogomolova.build.tasks.TaskRegistration
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstylePlugin
import org.gradle.api.plugins.quality.Pmd
import org.gradle.api.plugins.quality.PmdPlugin
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jacoco.core.analysis.ICounter
import org.jacoco.core.analysis.ICoverageNode
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.math.BigDecimal

const val DEFAULT_KOTLIN_KOTLIN_OPTION_VALUE = "1.4.20"
const val KAPT_CONFIGURATION_NAME = "kapt"
const val BUILD_DIR_PATH_PROPERTY_NAME = "buildDirPath"

class CommonBuildPlugin : Plugin<Project> {

    private val kotlinPluginApplier: KotlinPluginApplier = KotlinPluginApplier()
    private val repositoryApplier: Plugin<Project> = RepositoryApplier()
    private val staticAnalysisApplier: StaticAnalysisApplier = StaticAnalysisApplier()
    private val jacocoApplier: JacocoPluginApplier = JacocoPluginApplier()
    private val taskRegistrations: TaskRegistration = TaskRegistration()

    override fun apply(target: Project) {
        taskRegistrations.registerTasks(target)
        kotlinPluginApplier.apply(target)
        repositoryApplier.apply(target)
        staticAnalysisApplier.apply(target)
        jacocoApplier.apply(target)
    }
}

abstract class PluginApplier<T : Plugin<out Any>>(private val pluginClass: Class<T>) : Plugin<Project> {
    final override fun apply(target: Project) {
        println("try add plugin with class ${pluginClass.name} for project '${target.name}'")
        applyPlugin(target)
        println("try configure properties for plugin with class ${pluginClass.name}")
        val properties = target.properties as MutableMap<String, Any>
        configureProperties(properties, target)
        println("all properties for plugin with class ${pluginClass.name} configured!")
        println("try configure additional plugins for plugin with class ${pluginClass.name}")
        applyAdditionalPlugins(target.plugins, properties)
        println("all additional plugins for plugin with class ${pluginClass.name} configured!")
        println("try configure dependencies for plugin with class ${pluginClass.name}")
        configureDependencies(target.dependencies, properties)
        println("all dependencies for plugin with class ${pluginClass.name} configured!")
        println("try configure extensions for plugin with class ${pluginClass.name}")
        configureExtensions(target.extensions, properties)
        println("all extensions for plugin with class ${pluginClass.name} configured!")
        println("try configure tasks for plugin with class ${pluginClass.name}")
        configureTasks(target.tasks, properties)
        println("all tasks for plugin with class ${pluginClass.name} configured!")
        println("plugin with class ${pluginClass.name} for project '${target.name}' added!")
        applyPluginAfterConfigure(target)
    }

    protected open fun configureTasks(target: TaskContainer, properties: MutableMap<String, Any>) {
        println("plugin has no tasks to configure for project ")
    }

    protected open fun configureExtensions(target: ExtensionContainer, properties: MutableMap<String, Any>) {
        println("plugin has no extensions to configure for project ")
    }

    protected open fun configureDependencies(target: DependencyHandler, properties: MutableMap<String, Any>) {
        println("plugin has no dependencies to configure for project")
    }

    protected open fun configureProperties(properties: MutableMap<String, Any>, target: Project) {
        println("plugin has no properties to configure for project")
    }

    protected open fun applyAdditionalPlugins(plugins: PluginContainer, properties: MutableMap<String, Any>) {
        println("plugin has no another plugin to configure for project")
    }

    private fun applyPlugin(target: Project) = if (isAllowApplied(target, pluginClass)) {
        target.plugins.apply(pluginClass)
    } else {
        println("plugin already applied")
    }

    protected open fun isAllowApplied(target: Project, pluginClass: Class<T>): Boolean = !target.plugins.hasPlugin(pluginClass)

    private fun applyPluginAfterConfigure(target: Project) = if (isAllowReApplyPluginAfterConfigure(pluginClass)) {
        applyPlugin(target)
    } else {
        println("plugin can't re apply after configure")
    }

    protected open fun isAllowReApplyPluginAfterConfigure(pluginClass: Class<T>): Boolean = false
}

internal class KotlinPluginApplier : PluginApplier<KotlinPluginWrapper>(KotlinPluginWrapper::class.java) {
    companion object Companion {
        const val JVM_TARGET_VERSION_PROPERTY_NAME = "jvmTargetVersion"
        const val DEFAULT_JVM_TARGET_KOTLIN_OPTION_VALUE = "11"
        const val KOTLIN_VERSION_PROPERTY_NAME = "kotlinVersion"
        const val KOTLIN_ANNOTATION_PROCESSOR_PLUGIN = "kotlin-kapt"
        const val KOTLIN_REFLECT_DEPENDENCY_NOTATION = "org.jetbrains.kotlin:kotlin-reflect:"
    }

    override fun applyAdditionalPlugins(plugins: PluginContainer, properties: MutableMap<String, Any>) {
        plugins.apply(KOTLIN_ANNOTATION_PROCESSOR_PLUGIN)
    }

    override fun configureProperties(properties: MutableMap<String, Any>, target: Project) {
        val buildDirPath: String = target.buildDir.absolutePath.toString()
        properties[BUILD_DIR_PATH_PROPERTY_NAME] = buildDirPath
    }

    override fun configureDependencies(target: DependencyHandler, properties: MutableMap<String, Any>) {
        val kotlinReflectDependencyNotation =
            KOTLIN_REFLECT_DEPENDENCY_NOTATION + (properties[KOTLIN_VERSION_PROPERTY_NAME] ?: DEFAULT_KOTLIN_KOTLIN_OPTION_VALUE)
        target.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, kotlinReflectDependencyNotation)
        target.add(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME, kotlinReflectDependencyNotation)
    }

    override fun configureTasks(target: TaskContainer, properties: MutableMap<String, Any>) {
        configureKotlinCompileTask(target, properties)
    }

    private fun configureKotlinCompileTask(target: TaskContainer, properties: MutableMap<String, Any>) {
        target.withType(KotlinCompile::class.java) {
            with(it) {
                kotlinOptions {
                    jvmTarget = properties[JVM_TARGET_VERSION_PROPERTY_NAME] as String? ?: DEFAULT_JVM_TARGET_KOTLIN_OPTION_VALUE// default 11
                    allWarningsAsErrors = true
                }

            }
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
    override fun configureTasks(target: TaskContainer, properties: MutableMap<String, Any>) {
        configurePmdTask(target, properties)
    }

    private fun configurePmdTask(target: TaskContainer, properties: MutableMap<String, Any>) {
        target.withType(Pmd::class.java) {
            with(it) {
                isConsoleOutput = true
            }
        }
    }

}

internal class CheckstyleApplier : PluginApplier<CheckstylePlugin>(CheckstylePlugin::class.java) {

    override fun configureTasks(target: TaskContainer, properties: MutableMap<String, Any>) {
        configureCheckStyleTask(target, properties)
    }

    private fun configureCheckStyleTask(target: TaskContainer, properties: MutableMap<String, Any>) {
        target.withType(Checkstyle::class.java) {
            with(it) {
                isIgnoreFailures = false
                isShowViolations = true
            }
        }
    }

}

internal class RepositoryApplier : Plugin<Project> {
    override fun apply(target: Project) {
        target.repositories.add(target.repositories.mavenCentral())
        target.repositories.add(target.repositories.gradlePluginPortal())
    }

}

internal class JacocoPluginApplier : PluginApplier<JacocoPlugin>(JacocoPlugin::class.java) {
    companion object Companion {
        val TEST_CLASS_NAME_PATTERNS = listOf("*Test")
        const val CLASS_DUMP_DIR_RELATIVE_PATH = "%s/jacoco/classpathdumps"
        const val JACOCO_XML_REPORT_FILE_PATH = "%s/reports/jacoco/jacoco.xml"
        const val JACOCO_XML_REPORT_FILE_PROPERTY_NAME = "jacocoXmlReportFileName"
        const val CLASS_DUMP_DIR_PROPERTY_NAME = "classDumpDir"
        const val COVERAGE_RATIO_MINIMUM = 0.010

    }

    override fun configureProperties(properties: MutableMap<String, Any>, target: Project) {
        properties[CLASS_DUMP_DIR_PROPERTY_NAME] = CLASS_DUMP_DIR_RELATIVE_PATH.format(properties[BUILD_DIR_PATH_PROPERTY_NAME])
        properties[JACOCO_XML_REPORT_FILE_PROPERTY_NAME] = JACOCO_XML_REPORT_FILE_PATH.format(properties[BUILD_DIR_PATH_PROPERTY_NAME])
    }

    override fun configureTasks(target: TaskContainer, properties: MutableMap<String, Any>) {
        configureJacocoTestIntegrationTask(target, properties)
        configureJacocoReportTask(target, properties)
        configureJacocoVerificationTask(target, properties)
    }

    private fun configureJacocoTestIntegrationTask(target: TaskContainer, properties: MutableMap<String, Any>) {
        fun isAvailableToExclude(clazzInfo: ClassInfo): Boolean = clazzInfo.name.endsWith("_")
        fun findCoverageClasses(): Pair<List<String>, List<String>> {
            val includes = ArrayList<String>()
            val excludes = ArrayList<String>()
            val result = Pair<List<String>, List<String>>(includes, excludes)
            val classPathClassFounder = ClassPathClassFounder(properties[BUILD_DIR_PATH_PROPERTY_NAME] as String)
            println("try to search includes and excludes in '$classPathClassFounder.searchingPath' for project ")
            classPathClassFounder.getAllClassInfo { clazzInfo ->
                if (isAvailableToExclude(clazzInfo!!)) {
                    excludes.add(clazzInfo.name)
                } else {
                    includes.add(clazzInfo.name)
                }
            }
            println("found includes: ${result.first}")
            println("found excludes: ${result.second}")
            return result
        }

        target.withType(Test::class.java) {
            with(it) {
                useJUnitPlatform()
                failFast = true
                ignoreFailures = false
                setTestNameIncludePatterns(TEST_CLASS_NAME_PATTERNS)
                doFirst {
                    extensions.configure(JacocoTaskExtension::class.java) { extension ->
                        with(extension)
                        {
                            this.classDumpDir = File(properties[CLASS_DUMP_DIR_PROPERTY_NAME] as String)
                            val coverageClasses = findCoverageClasses()
                            this.includes = coverageClasses.first
                            this.excludes = coverageClasses.second
                        }
                    }
                }
            }
        }

    }

    private fun configureJacocoVerificationTask(target: TaskContainer, properties: MutableMap<String, Any>) {
        target.withType(JacocoCoverageVerification::class.java) {
            with(it)
            {
                with(violationRules) {
                    println("start creating rules")
                    rule { rule ->
                        with(rule)
                        {
                            limit { limit ->
                                with(limit)
                                {
                                    println("creating ${ICoverageNode.CounterEntity.METHOD.name} with ${ICounter.CounterValue.COVEREDRATIO.name} equals $COVERAGE_RATIO_MINIMUM")
                                    counter = ICoverageNode.CounterEntity.METHOD.name
                                    value = ICounter.CounterValue.COVEREDRATIO.name
                                    minimum = BigDecimal.valueOf(COVERAGE_RATIO_MINIMUM)
                                }
                            }
                        }
                    }
                    rule { rule ->
                        with(rule)
                        {
                            limit { limit ->
                                with(limit) {
                                    println("creating ${ICoverageNode.CounterEntity.CLASS.name} with ${ICounter.CounterValue.COVEREDRATIO.name} equals $COVERAGE_RATIO_MINIMUM")
                                    counter = ICoverageNode.CounterEntity.CLASS.name
                                    value = ICounter.CounterValue.COVEREDRATIO.name
                                    minimum = BigDecimal.valueOf(COVERAGE_RATIO_MINIMUM)
                                }
                            }
                        }
                    }
                    rule { rule ->
                        with(rule)
                        {
                            limit { limit ->
                                with(limit) {
                                    println("creating ${ICoverageNode.CounterEntity.LINE.name} with ${ICounter.CounterValue.COVEREDRATIO.name} equals $COVERAGE_RATIO_MINIMUM")
                                    counter = ICoverageNode.CounterEntity.LINE.name
                                    value = ICounter.CounterValue.COVEREDRATIO.name
                                    minimum = BigDecimal.valueOf(COVERAGE_RATIO_MINIMUM)
                                }
                            }
                        }
                    }
                    rule { rule ->
                        with(rule)
                        {
                            limit { limit ->
                                with(limit) {
                                    println("creating ${ICoverageNode.CounterEntity.BRANCH.name} with ${ICounter.CounterValue.COVEREDRATIO.name} equals $COVERAGE_RATIO_MINIMUM")
                                    counter = ICoverageNode.CounterEntity.BRANCH.name
                                    value = ICounter.CounterValue.COVEREDRATIO.name
                                    minimum = BigDecimal.valueOf(COVERAGE_RATIO_MINIMUM)
                                }
                            }
                        }
                    }
                    rule { rule ->
                        with(rule)
                        {
                            limit { limit ->
                                with(limit) {
                                    println("creating ${ICoverageNode.CounterEntity.INSTRUCTION.name} with ${ICounter.CounterValue.COVEREDRATIO.name} equals $COVERAGE_RATIO_MINIMUM")
                                    counter = ICoverageNode.CounterEntity.INSTRUCTION.name
                                    value = ICounter.CounterValue.COVEREDRATIO.name
                                    minimum = BigDecimal.valueOf(COVERAGE_RATIO_MINIMUM)
                                }
                            }
                        }
                    }
                }
                println("test coverage verification rules created")
            }
        }
    }

    private fun configureJacocoReportTask(target: TaskContainer, properties: MutableMap<String, Any>) {
        target.withType(JacocoReport::class.java) {
            with(it)
            {
                with(reports)
                {
                    /*only XML reports*/
                    csv.isEnabled = false
                    html.isEnabled = false
                    xml.isEnabled = true
                    xml.destination = File(properties[JACOCO_XML_REPORT_FILE_PROPERTY_NAME] as String)
                }

            }
        }
    }
}

class ClassPathClassFounder(buildDir: String) {
    companion object {
        val MAIN_CLASSES_SET: String = "%s/classes/kotlin/main" + File.pathSeparator + "%s/classes/java/main" + File.pathSeparator
    }

    private val searchingPath = MAIN_CLASSES_SET.format(buildDir)

    private val classFounder = ClassGraph().overrideClasspath(searchingPath).verbose()
        .enableAllInfo()

    fun getAllClassInfo(action: (classInfo: ClassInfo) -> Unit) {
        classFounder.scan().use { scanResult ->
            scanResult.allClasses.forEach { classInfo -> action(classInfo) }
        }
    }

    fun getAllClassInfoWithClassAnnotationName(annotationClassName: String, action: (classInfo: ClassInfo) -> Unit) {
        classFounder.scan().use { scanResult ->
            scanResult.getClassesWithAnnotation(annotationClassName).forEach { classInfo -> action(classInfo) }
        }
    }
}
