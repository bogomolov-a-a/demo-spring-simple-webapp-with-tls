plugins {
    id("spring-boot-web-plugin")
    id("spring-boot-security-plugin")
    id("spring-boot-jpa-plugin")
    id("io.freefair.lombok") version("5.3.0")
}

/*dependencyManagement {
    imports {
        val springBootVersion: String by project
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
}*/
dependencies {
    runtimeOnly("org.xerial:sqlite-jdbc")
}
/*    implementation("com.fasterxml.jackson.module:jackson-module-jaxb-annotations")







    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    //implementation("org.webjars:bootstrap")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit.vintage")
        exclude("junit")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.apache.httpcomponents:httpclient")

    runtimeOnly("org.springframework.boot:spring-boot-devtools")*/
