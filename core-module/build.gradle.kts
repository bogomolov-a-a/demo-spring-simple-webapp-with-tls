plugins {
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
}

dependencyManagement {
    imports {
        val springBootVersion: String by project
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-jaxb-annotations")
    implementation("org.projectlombok:lombok")
    testImplementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.xerial:sqlite-jdbc")
    implementation("org.hibernate:hibernate-core")
    annotationProcessor("org.hibernate:hibernate-core")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-tomcat")
    implementation("javax.servlet:javax.servlet-api")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.session:spring-session-core")

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

    runtimeOnly("org.springframework.boot:spring-boot-devtools")

}