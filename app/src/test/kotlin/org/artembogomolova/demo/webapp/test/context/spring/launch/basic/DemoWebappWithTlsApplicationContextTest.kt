package org.artembogomolova.demo.webapp.test.context.spring.launch.basic

import org.artembogomolova.demo.webapp.test.context.AbstractSpringBootContextLoadClass
import org.artembogomolova.demo.webapp.test.config.TestWithoutConditionDatasourceConfiguration
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Import

@Import(TestWithoutConditionDatasourceConfiguration::class)
@DisplayName("Basically test for application with all configuration")
internal class DemoWebappWithTlsApplicationContextTest(@LocalServerPort serverPort: Int) : AbstractSpringBootContextLoadClass(serverPort) {

    @Test
    @DisplayName("Deploy application test without condition.")
    fun contextLoads() {
        checkServerPortAssigned()
    }
}