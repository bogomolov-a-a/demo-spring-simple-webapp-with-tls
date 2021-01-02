package org.artembogomolova.build.plugins

import groovy.lang.Closure
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import java.util.function.Consumer

/**
 *
 */
class CoreSpringBootPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply("io.spring.dependency-management")
        val extension: DependencyManagementExtension = target.extensions.getByName("dependencyManagement") as DependencyManagementExtension
        val springBootVersion = target.properties["springBootVersion"];
        extension.imports { this.mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion") }
        target.plugins.apply("io.spring.dependency-management")
    }

}

abstract class AbstractSpringBootModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        if (!target.plugins.hasPlugin(CoreSpringBootPlugin::class.java)) {
            target.plugins.apply(CoreSpringBootPlugin::class.java)
        }
    }
}

/**
 *
 */
class SpringBootWebPlugin : AbstractSpringBootModulePlugin() {
    override fun apply(target: Project) {
        super.apply(target)
        target.dependencies.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, "org.springframework.boot:spring-boot-starter-web")
        target.dependencies.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, "org.springframework.boot:spring-boot-starter-tomcat")
        target.dependencies.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, "javax.servlet:javax.servlet-api")
    }
}

/**
 *
 */
class SpringBootSecurityPlugin : AbstractSpringBootModulePlugin() {
    override fun apply(target: Project) {
        super.apply(target)
        target.dependencies.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, "org.springframework.boot:spring-boot-starter-security")
        target.dependencies.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, "org.springframework.security:spring-security-config")
        target.dependencies.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, "org.springframework.session:spring-session-core")
    }
}

class SpringBootJpaPlugin: AbstractSpringBootModulePlugin(){
    override fun apply(target: Project) {
        super.apply(target)
        target.dependencies.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME,"org.springframework.boot:spring-boot-starter-data-jpa")
        target.dependencies.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME,"org.liquibase:liquibase-core")
        target.dependencies.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME,"org.hibernate:hibernate-core")
        target.dependencies.add(JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME,"org.hibernate:hibernate-jpamodelgen")
    }
}
class SpringBootTestPlugin: AbstractSpringBootModulePlugin()
{
    override fun apply(target: Project) {
        target.dependencies.add(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME,"org.springframework.boot:spring-boot-starter-test")
        target.dependencies.add(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME,"org.junit.jupiter:junit-jupiter-engine")
    }
}
