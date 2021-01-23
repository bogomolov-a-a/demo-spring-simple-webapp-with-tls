allprojects {
    val projectGroup: String by project
    val projectVersion: String by project
    group = projectGroup
    version = projectVersion
}
subprojects {
    plugins.apply(org.artembogomolova.build.plugins.CommonBuildPlugin::class.java)
}
tasks.withType<Wrapper>().configureEach {
    val gradleWrapperVersion: String by project
    gradleVersion = gradleWrapperVersion
    distributionType = Wrapper.DistributionType.BIN
}
repositories {
    mavenCentral()
}