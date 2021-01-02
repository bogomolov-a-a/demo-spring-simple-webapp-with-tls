plugins {
    id("spring-boot-web-plugin")
    id("spring-boot-security-plugin")
    id("spring-boot-jpa-plugin")
    id("spring-boot-test-plugin")
    id("io.freefair.lombok") version("5.3.0")
}
dependencies {
    runtimeOnly("org.xerial:sqlite-jdbc")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.apache.httpcomponents:httpclient")
}