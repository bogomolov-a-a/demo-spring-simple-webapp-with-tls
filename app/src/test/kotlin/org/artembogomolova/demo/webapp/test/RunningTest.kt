package org.artembogomolova.demo.webapp.test

import org.artembogomolova.demo.webapp.main.DemoWebappWithTlsApplication
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [DemoWebappWithTlsApplication::class])
@ActiveProfiles("test")
class RunningTest {
    @Test
    fun test() {
        println("test passed")
    }
}