import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version ("1.4.20")
    java
}
repositories {
    mavenCentral()
    gradlePluginPortal()
}
plugins.apply(org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper::class.java)
dependencies {
    /*spring*/
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.4.0")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.9.RELEASE")
    implementation("io.github.classgraph:classgraph:4.8.98")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.4.20")
    implementation("org.jetbrains.kotlin:kotlin-noarg:1.4.20")
    /*quality*/
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.15.0")
    implementation("gradle.plugin.com.github.spotbugs.snom:spotbugs-gradle-plugin:4.6.0")
    /*test reports*/
    implementation("org.jacoco:org.jacoco.core:0.8.6")
    /*sonar*/
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.1")
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}