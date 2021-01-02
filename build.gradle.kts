defaultTasks("clean", "build")
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