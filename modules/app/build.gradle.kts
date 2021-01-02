plugins {
    id("spring-boot-web-plugin")
    id("spring-boot-security-plugin")
    id("spring-boot-jpa-plugin")
    id("spring-boot-test-plugin")
    id("io.freefair.lombok") version ("5.3.0")
    id("com.github.spotbugs") version ("4.6.0")
}
dependencies {
    /*jdbc driver*/
    runtimeOnly("org.xerial:sqlite-jdbc")
    /*spring dev tools*/
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    /*use for any tests*/
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.apache.httpcomponents:httpclient")
}
spotbugs{
    excludeFilter.set(File("config/spotbugs/excludes.xml"))
}
