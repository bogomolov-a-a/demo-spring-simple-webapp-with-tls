package org.artembogomolova.build.plugins

import org.artembogomolova.build.tasks.TaskRegistration
import org.gradle.api.Plugin
import org.gradle.api.Project

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

internal class RepositoryApplier : Plugin<Project> {
    override fun apply(target: Project) {
        target.repositories.add(target.repositories.mavenCentral())
        target.repositories.add(target.repositories.gradlePluginPortal())
    }

}