package org.artembogomolova.demo.webapp.test.context

import org.artembogomolova.demo.webapp.main.DemoWebappWithTlsApplication
import org.artembogomolova.demo.webapp.main.dao.repo.IUserRepository
import org.artembogomolova.demo.webapp.test.AbstractTest
import org.artembogomolova.demo.webapp.test.context.event.ContextStartedEventPublishingListener
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [DemoWebappWithTlsApplication::class])
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
@TestExecutionListeners(
    DependencyInjectionTestExecutionListener::class, ContextStartedEventPublishingListener::class
)
abstract class AbstractContextLoadTest protected constructor(protected val serverPort: Int) : AbstractTest() {

    @Autowired
    protected lateinit var userRepository: IUserRepository

    protected fun checkServerPortAssigned() {
        Assertions.assertNotEquals(0, serverPort, "Port is 0! This is wrong way!")
        log.info("application started at port {}", serverPort)
    }

    @AfterEach
    fun afterTests() {
        userRepository.deleteAll()
    }
}