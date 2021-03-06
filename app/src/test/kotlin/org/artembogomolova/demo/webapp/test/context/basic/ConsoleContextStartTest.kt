package org.artembogomolova.demo.webapp.test.context.basic

import org.artembogomolova.demo.webapp.main.DemoWebappWithTlsApplication
import org.artembogomolova.demo.webapp.test.context.AbstractContextLoadTestClass
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Run as console command")
internal class ConsoleContextStartTest : AbstractContextLoadTestClass() {
    @Test
    @DisplayName("Emulate run as console command without main method execution. Main method body must be duplicate this test")
    fun contextStarts() {
        val application: DemoWebappWithTlsApplication = DemoWebappWithTlsApplication.getInstance(arrayOf())
        Assertions.assertTrue(application.isRunning())
        log.info("context successful started.")
        application.stop()
        Assertions.assertFalse(application.isRunning())
        log.info("context successful stopped.")
    }
}