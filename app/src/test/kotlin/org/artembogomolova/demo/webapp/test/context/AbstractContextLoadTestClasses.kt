package org.artembogomolova.demo.webapp.test.context

import org.artembogomolova.demo.webapp.main.DemoWebappWithTlsApplication
import org.artembogomolova.demo.webapp.main.dao.repo.IUserRepository
import org.artembogomolova.demo.webapp.test.AbstractTest
import org.artembogomolova.demo.webapp.test.context.util.event.ContextStartedEventPublishingListener
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener

abstract class AbstractContextLoadTestClass protected constructor() : AbstractTest()

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [DemoWebappWithTlsApplication::class])
@TestExecutionListeners(
    DependencyInjectionTestExecutionListener::class, ContextStartedEventPublishingListener::class
)
@AutoConfigureMockMvc
@ActiveProfiles(value = ["test"])
@DirtiesContext
abstract class AbstractSpringBootContextLoadClass(protected val serverPort: Int) : AbstractContextLoadTestClass() {
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