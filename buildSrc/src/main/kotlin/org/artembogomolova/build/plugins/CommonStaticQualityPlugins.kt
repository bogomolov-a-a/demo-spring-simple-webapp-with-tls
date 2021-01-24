package org.artembogomolova.build.plugins

import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstylePlugin
import org.gradle.api.plugins.quality.Pmd
import org.gradle.api.plugins.quality.PmdPlugin
import org.gradle.api.tasks.TaskContainer

internal class StaticAnalysisApplier : Plugin<Project> {
    private val checkstyleApplier: CheckstyleApplier = CheckstyleApplier()
    private val pmdApplier: PmdApplier = PmdApplier()
    private val detektApplier: DetektApplier = DetektApplier()
    override fun apply(target: Project) {
        checkstyleApplier.apply(target)
        pmdApplier.apply(target)
        detektApplier.apply(target)
    }
}

internal class PmdApplier : PluginApplier<PmdPlugin>(PmdPlugin::class.java) {
    override fun configureTasks(target: TaskContainer, properties: MutableMap<String, Any>) {
        super.configureTasks(target, properties)
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
        super.configureTasks(target, properties)
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

internal class DetektApplier : PluginApplier<DetektPlugin>(DetektPlugin::class.java) {
    override fun configureTasks(target: TaskContainer, properties: MutableMap<String, Any>) {
        super.configureTasks(target, properties)
        target.withType(Detekt::class.java){
            with(it)
            {
                config.from.add(project.rootProject.rootDir.absolutePath+"/config/detekt/detekt.yml")
                reports{

                }
            }
        }
    }
}