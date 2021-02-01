package org.artembogomolova.demo.webapp.test.context.spring.launch.basic

import org.artembogomolova.demo.webapp.test.context.AbstractSpringBootContextLoadClass
import org.artembogomolova.demo.webapp.test.config.RerunApplicationContextTestDatasourceConfiguration
import org.artembogomolova.demo.webapp.test.context.util.event.DoubleTimeContextStartedEventPublisherListener
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestExecutionListeners

@Import(RerunApplicationContextTestDatasourceConfiguration::class)
@TestExecutionListeners(DoubleTimeContextStartedEventPublisherListener::class)
@DisplayName("Two time deploy test. Test with reboot application.")
internal class RerunApplicationContextTest(@LocalServerPort val port: Int) : AbstractSpringBootContextLoadClass(port) {

    @Test
    @DisplayName("Validate condition branch with application reboot on exists database.")
    fun contextLoads() {
        checkServerPortAssigned()
    }
}