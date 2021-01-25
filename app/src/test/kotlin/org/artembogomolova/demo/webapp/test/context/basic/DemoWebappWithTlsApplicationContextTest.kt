package org.artembogomolova.demo.webapp.test.context.basic

import org.artembogomolova.demo.webapp.test.context.AbstractContextLoadTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.web.server.LocalServerPort

@DisplayName("Basically test for application with all configuration")
internal class DemoWebappWithTlsApplicationContextTest(@LocalServerPort serverPort: Int) : AbstractContextLoadTest(serverPort) {
    @Test
    @DisplayName("Deploy application test without condition.")
    fun contextLoads() {
        checkServerPortAssigned()
    }
}