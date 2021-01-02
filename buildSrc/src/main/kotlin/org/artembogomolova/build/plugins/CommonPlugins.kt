package org.artembogomolova.build.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstylePlugin
import org.gradle.api.plugins.quality.Pmd
import org.gradle.api.plugins.quality.PmdPlugin
import org.gradle.api.tasks.compile.JavaCompile

class CommonJavaPlugin : Plugin<Project> {
    companion object CommonJavaPluginObject {
        val JDK_VERSION: String get() = "11"
    }

    override fun apply(target: Project) {
        applyJavaPlugin(target)
        applyStaticAnalysisPlugins(target)
        addRepositories(target)
    }

    private fun addRepositories(target: Project) {
        target.repositories.add(target.repositories.mavenCentral());
    }

    private fun applyStaticAnalysisPlugins(target: Project) {
        applyCheckStylePlugin(target)
        applyPmdPlugin(target)
    }

    private fun applyPmdPlugin(target: Project) {
        target.plugins.apply(PmdPlugin::class.java)
        target.tasks.withType(Pmd::class.java) {
            isConsoleOutput = true
        }
    }

    private fun applyCheckStylePlugin(target: Project) {
        target.plugins.apply(CheckstylePlugin::class.java)
        target.tasks.withType(Checkstyle::class.java) {
            isIgnoreFailures = false
            isShowViolations = true
        }
    }


    private fun applyJavaPlugin(target: Project) {
        target.plugins.apply(JavaPlugin::class.java)
        target.tasks.withType(JavaCompile::class.java) {
            sourceCompatibility = JDK_VERSION
            targetCompatibility = JDK_VERSION
        }
    }
}