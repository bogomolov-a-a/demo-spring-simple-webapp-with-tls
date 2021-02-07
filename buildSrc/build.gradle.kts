import java.net.URI
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version ("1.4.20")
    java
}
val kotlinVersion = "1.4.20"
val springBootVersion = "2.4.0"
val springDependencyManagementVersion = "1.0.9.RELEASE"
val classGraphVersion = "4.8.98"
val detektPluginVersion = "1.15.0"
val spotbugsPluginVersion = "4.6.0"
val jacocoVersion = "0.8.6"
val sonarPluginVersion = "3.1"
val javaVersion = "11"
repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
        url = URI("https://maven.enginehub.org/repo")
    }
}
plugins.apply(org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper::class.java)
dependencies {
    /*spring*/
    implementation("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    implementation("io.spring.gradle:dependency-management-plugin:$springDependencyManagementVersion")
    implementation("io.github.classgraph:classgraph:$classGraphVersion")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-noarg:$kotlinVersion")
    /*quality*/
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektPluginVersion")
    implementation("gradle.plugin.com.github.spotbugs.snom:spotbugs-gradle-plugin:$spotbugsPluginVersion")
    /*test reports*/
    implementation("org.jacoco:org.jacoco.core:$jacocoVersion")
    /*sonar*/
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:$sonarPluginVersion")
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = javaVersion
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = javaVersion
}