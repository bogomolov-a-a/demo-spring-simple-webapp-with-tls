allprojects {
    val projectGroup: String by project
    val projectVersion: String by project
    group = projectGroup
    version = projectVersion
}

tasks.withType<Wrapper>().configureEach {
    val gradleWrapperVersion: String by project
    gradleVersion = gradleWrapperVersion
    distributionType = Wrapper.DistributionType.BIN
}

subprojects {
    apply<IdeaPlugin>()
    apply<BasePlugin>()
    apply<JavaPlugin>()
    repositories {
        mavenCentral()
        maven(url = "https://repo.spring.io/snapshot")
        maven(url = "https://repo.spring.io/milestone")
    }
    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            showExceptions = true
            showStandardStreams = true
            showCauses = true
            showStackTraces = true
        }
    }
}

defaultTasks("clean", "build")