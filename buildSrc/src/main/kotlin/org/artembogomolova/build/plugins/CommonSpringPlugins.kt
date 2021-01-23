package org.artembogomolova.build.plugins

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.tasks.TaskContainer
import org.springframework.boot.gradle.plugin.ResolveMainClassName
import org.springframework.boot.gradle.plugin.SpringBootPlugin

const val SPRING_BOOT_VERSION_PROPERTY_NAME = "springBootVersion"
const val DEFAULT_SPRING_BOOT_VERSION = "2.4.0"

/**
 *
 */
class CoreSpringBootPlugin : Plugin<Project> {
    private val springBootPluginApplier: SpringBootPluginApplier = SpringBootPluginApplier()
    private val springBootDependencyManagementPluginApplier: SpringBootDependencyManagementPluginApplier = SpringBootDependencyManagementPluginApplier()
    override fun apply(target: Project) {
        springBootPluginApplier.apply(target)
        springBootDependencyManagementPluginApplier.apply(target)
    }
}

internal class SpringBootPluginApplier : PluginApplier<SpringBootPlugin>(SpringBootPlugin::class.java) {

    companion object Companion {
        const val KOTLIN_ALLOPEN_SPRING_PROFILE_PLUGIN = "kotlin-spring"
        const val SPRING_BOOT_APPLICATION_CLASS_NAME = "org.springframework.boot.autoconfigure.SpringBootApplication"
    }

    override fun applyAdditionalPlugins(plugins: PluginContainer, properties: MutableMap<String, Any>) {
        plugins.apply(KOTLIN_ALLOPEN_SPRING_PROFILE_PLUGIN)
    }

    override fun configureTasks(target: TaskContainer, properties: MutableMap<String, Any>) {
        configureBootJarTask(target, properties)
    }

    private fun configureBootJarTask(target: TaskContainer, properties: MutableMap<String, Any>) {
        fun getConfiguredMainClassName(): String? {
            val classPathClassFounder = ClassPathClassFounder(properties[BUILD_DIR_PATH_PROPERTY_NAME] as String)
            var result: String? = null
            classPathClassFounder.getAllClassInfoWithClassAnnotationName(SPRING_BOOT_APPLICATION_CLASS_NAME) {
                result = it.name
            }
            return result
        }
        target.withType(ResolveMainClassName::class.java) { it ->
            with(it)
            {
                doFirst {
                    configuredMainClassName.set(getConfiguredMainClassName())
                }
            }

        }
    }
}

abstract class AbstractSpringBootModulePlugin : PluginApplier<CoreSpringBootPlugin>(CoreSpringBootPlugin::class.java)

/**
 *
 */
class SpringBootWebPlugin : AbstractSpringBootModulePlugin() {
    companion object Companion {
        const val WEB_ROOT_DEPENDENCY = "org.springframework.boot:spring-boot-starter-web"
        const val DEFAULT_SERVLET_CONTAINER_DEPENDENCY = "org.springframework.boot:spring-boot-starter-tomcat"
        const val SERVLET_API_DEPENDENCY = "javax.servlet:javax.servlet-api"
    }

    override fun configureDependencies(target: DependencyHandler, properties: MutableMap<String, Any>) {
        target.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, WEB_ROOT_DEPENDENCY)
        target.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, DEFAULT_SERVLET_CONTAINER_DEPENDENCY)
        target.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, SERVLET_API_DEPENDENCY)
    }
}

/**
 *
 */
class SpringBootSecurityPlugin : AbstractSpringBootModulePlugin() {
    companion object Companion {
        const val SECURITY_ROOT_DEPENDENCY = "org.springframework.boot:spring-boot-starter-security"
        const val SECURITY_CONFIG_DEPENDENCY = "org.springframework.security:spring-security-config"
        const val SESSION_DEPENDENCY = "org.springframework.session:spring-session-core"
    }

    override fun configureDependencies(target: DependencyHandler, properties: MutableMap<String, Any>) {
        target.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, SECURITY_ROOT_DEPENDENCY)
        target.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, SECURITY_CONFIG_DEPENDENCY)
        target.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, SESSION_DEPENDENCY)
    }
}

class SpringBootJpaPlugin : AbstractSpringBootModulePlugin() {
    companion object Companion {
        const val JPA_ROOT_DEPENDENCY = "org.springframework.boot:spring-boot-starter-data-jpa"
        const val DEFAULT_MIGRATION_TOOL_DEPENDENCY = "org.liquibase:liquibase-core"
        const val DEFAULT_ORM_DEPENDENCY = "org.hibernate:hibernate-core"
        const val DEFAULT_STATIC_MODEL_GEN_DEPENDENCY = "org.hibernate:hibernate-jpamodelgen"
    }

    override fun configureDependencies(target: DependencyHandler, properties: MutableMap<String, Any>) {
        /**/
        target.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, JPA_ROOT_DEPENDENCY)
        target.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, DEFAULT_MIGRATION_TOOL_DEPENDENCY)
        target.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, DEFAULT_ORM_DEPENDENCY)
        target.add(KAPT_CONFIGURATION_NAME, DEFAULT_STATIC_MODEL_GEN_DEPENDENCY)
    }
}

class SpringBootTestPlugin : AbstractSpringBootModulePlugin() {
    companion object Companion {
        const val TEST_ROOT_DEPENDENCY = "org.springframework.boot:spring-boot-starter-test"
        const val DEFAULT_TEST_ENGINE_DEPENDENCY = "org.junit.jupiter:junit-jupiter-engine"
    }

    override fun configureDependencies(target: DependencyHandler, properties: MutableMap<String, Any>) {
        target.add(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME, TEST_ROOT_DEPENDENCY)
        target.add(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME, DEFAULT_TEST_ENGINE_DEPENDENCY)
    }
}

internal class SpringBootDependencyManagementPluginApplier : PluginApplier<DependencyManagementPlugin>(DependencyManagementPlugin::class.java) {
    companion object Companion {
        const val EXTENSION_NAME = "dependencyManagement"
        const val SPRING_BOOT_BOM_DEPENDENCY_COORDINATE_FORMAT = "org.springframework.boot:spring-boot-dependencies:%s"
    }

    override fun configureExtensions(target: ExtensionContainer, properties: MutableMap<String, Any>) {
        val extension: DependencyManagementExtension = target.getByName(EXTENSION_NAME) as DependencyManagementExtension
        val springBootVersion = properties[SPRING_BOOT_VERSION_PROPERTY_NAME] ?: DEFAULT_SPRING_BOOT_VERSION
        extension.imports {
            it.mavenBom(SPRING_BOOT_BOM_DEPENDENCY_COORDINATE_FORMAT.format(springBootVersion))
        }
    }

    override fun isAllowReApplyPluginAfterConfigure(pluginClass: Class<DependencyManagementPlugin>): Boolean = true
}
