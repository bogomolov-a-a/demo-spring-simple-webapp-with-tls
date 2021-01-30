package org.artembogomolova.demo.webapp.test.context.basic

import org.artembogomolova.demo.webapp.test.context.AbstractContextLoadTest
import org.artembogomolova.demo.webapp.test.context.event.DoubleTimeContextStartedEventPublisherListener
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.TestExecutionListeners

@TestExecutionListeners(DoubleTimeContextStartedEventPublisherListener::class)
@DisplayName("Two time deploy test. Test with reboot application.")
internal class RerunApplicationContextTest protected constructor(@LocalServerPort serverPort: Int) : AbstractContextLoadTest(serverPort) {
    @Test
    @DisplayName("Validate condition branch with application reboot on exists database.")
    fun contextLoads() {
        checkServerPortAssigned()
    }
}