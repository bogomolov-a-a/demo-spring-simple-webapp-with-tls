package org.artembogomolova.build.plugins

import org.artembogomolova.build.utils.excludeGeneratedModelClasses
import org.artembogomolova.build.utils.findCoverageClasses
import org.enginehub.codecov.CodecovPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer
import org.sonarqube.gradle.SonarQubeExtension
import org.sonarqube.gradle.SonarQubePlugin
import org.sonarqube.gradle.SonarQubeTask

internal class ExternalToolIntegrationPlugin : Plugin<Project> {
    private val sonarApplier: SonarApplier = SonarApplier()

    //private val codeCovApplier: CodeCovApplier = CodeCovApplier()
    override fun apply(target: Project) {
        sonarApplier.apply(target)
        //codeCovApplier.apply(target)
    }
}

private class SonarApplier : PluginApplier<SonarQubePlugin>(SonarQubePlugin::class.java) {

    companion object {
        const val PROJECT_KEY_PROPERTY = "sonar.projectKey"
        const val LOGIN_PROPERTY = "sonar.login"
        const val HOST_URL_PROPERTY = "sonar.host.url"
        const val ORGANIZATION_PROPERTY = "sonar.organization"
        const val COVERAGE_EXCLUSIONS_PROPERTY = "sonar.coverage.exclusions"
    }

    override fun configureProperties(properties: MutableMap<String, Any>, target: Project) {
        super.configureProperties(properties, target)
        properties[PROJECT_KEY_PROPERTY] = target.rootProject.name
    }

    override fun configureTasks(target: TaskContainer, properties: MutableMap<String, Any>) {
        super.configureTasks(target, properties)
        configureSonarQubeTask(target, properties)
    }

    private fun configureSonarQubeTask(target: TaskContainer, properties: MutableMap<String, Any>) {
        val sonarqubeTask = target.getByName(SonarQubeExtension.SONARQUBE_TASK_NAME) as SonarQubeTask
        with(sonarqubeTask) {
            val sonarProperties = getProperties()
            sonarProperties[PROJECT_KEY_PROPERTY] = properties[PROJECT_KEY_PROPERTY] as String
            sonarProperties[LOGIN_PROPERTY] = properties[LOGIN_PROPERTY] as String
            sonarProperties[HOST_URL_PROPERTY] = properties[HOST_URL_PROPERTY] as String
            sonarProperties[ORGANIZATION_PROPERTY] = properties[ORGANIZATION_PROPERTY] as String
            doFirst {
                sonarProperties[COVERAGE_EXCLUSIONS_PROPERTY] = findCoverageClasses(properties, ::excludeGeneratedModelClasses).second.joinToString()
            }
        }
    }
}

private class CodeCovApplier : PluginApplier<CodecovPlugin>(CodecovPlugin::class.java)
