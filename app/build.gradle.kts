plugins.apply(org.artembogomolova.build.plugins.SpringBootWebPlugin::class.java)
plugins.apply(org.artembogomolova.build.plugins.SpringBootSecurityPlugin::class.java)
plugins.apply(org.artembogomolova.build.plugins.SpringBootJpaPlugin::class.java)
plugins.apply(org.artembogomolova.build.plugins.SpringBootTestPlugin::class.java)

dependencies {
    /*jdbc driver*/
    runtimeOnly("org.xerial:sqlite-jdbc")
    /*spring dev tools*/
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    /*use for any tests*/
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.apache.httpcomponents:httpclient")
}
/*spotbugs {
    excludeFilter.set(File("config/spotbugs/excludes.xml"))
}*/
repositories {
    mavenCentral()
}