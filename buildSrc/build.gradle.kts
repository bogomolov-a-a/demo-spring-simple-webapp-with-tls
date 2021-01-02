plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}
repositories {
    mavenCentral()
    gradlePluginPortal()
}
gradlePlugin {
    plugins.register("common-java-plugin") {
        id = "common-java-plugin"
        implementationClass = "org.artembogomolova.build.plugins.CommonJavaPlugin"
    }

    plugins.register("spring-boot-web-plugin") {
        id = "spring-boot-web-plugin"
        implementationClass = "org.artembogomolova.build.plugins.SpringBootWebPlugin"
    }
    plugins.register("spring-boot-security-plugin") {
        id = "spring-boot-security-plugin"
        implementationClass = "org.artembogomolova.build.plugins.SpringBootSecurityPlugin"
    }
    plugins.register("spring-boot-jpa-plugin") {
        id = "spring-boot-jpa-plugin"
        implementationClass = "org.artembogomolova.build.plugins.SpringBootJpaPlugin"
    }
    plugins.register("spring-boot-test-plugin") {
        id = "spring-boot-test-plugin"
        implementationClass = "org.artembogomolova.build.plugins.SpringBootTestPlugin"
    }

}
dependencies{
    implementation("io.spring.gradle:dependency-management-plugin:1.0.9.RELEASE")
}
