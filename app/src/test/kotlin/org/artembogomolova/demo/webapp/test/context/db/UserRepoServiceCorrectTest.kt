package org.artembogomolova.demo.webapp.test.context.db

import org.artembogomolova.demo.webapp.main.dao.service.UserRepoService
import org.artembogomolova.demo.webapp.main.domain.auth.User
import org.artembogomolova.demo.webapp.main.event.ContextStartedEventListener
import org.artembogomolova.demo.webapp.test.context.AbstractContextLoadTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContextException
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextStartedEvent

@DisplayName("Validation for database corruption condition, full configuration loaded.")
internal class UserRepoServiceCorrectTest constructor(
    @LocalServerPort serverPort: Int,

    ) : AbstractContextLoadTest(serverPort) {
    @Autowired
    private lateinit var userRepoService: UserRepoService

    @Autowired
    private lateinit var applicationContext: ConfigurableApplicationContext

    @Autowired
    private lateinit var contextStartedEventListener: ContextStartedEventListener

    @Test
    @DisplayName("Test check all predefined users removed from existing database. This is wrong way!")
    fun checkAllUsersDeletedDatabaseCorrupted() {
        userRepository.deleteAll()
        log.info("All users deleted. Database must be corrupted, check it!")
        Assertions.assertTrue(userRepoService.corruptedDatabase())
        log.info("All users deleted. Database corrupted!")
    }

    @DisplayName("Test check '${UserRepoService.PREDEFINED_ADMIN_ACCOUNT_LOGIN}'  removed from existing database. This is wrong way!")
    @Test
    fun checkAdminUserDeletedDatabaseCorrupted() {
        val user: User = userRepository.findByLogin(UserRepoService.PREDEFINED_ADMIN_ACCOUNT_LOGIN)!!
        userRepository.delete(user)
        checkUserDeletedDatabaseCorrupted(user.login!!)
    }

    @DisplayName("Test check '${UserRepoService.PREDEFINED_GUEST_ACCOUNT_LOGIN}' removed from existing database. This is wrong way!")
    @Test
    fun checkGuestUserDeletedDatabaseCorrupted() {
        val user: User = userRepository.findByLogin(UserRepoService.PREDEFINED_GUEST_ACCOUNT_LOGIN)!!
        userRepository.delete(user)
        checkUserDeletedDatabaseCorrupted(user.login!!)
    }

    private fun checkUserDeletedDatabaseCorrupted(login: String) {
        log.info("User {} deleted. Database must be corrupted, check it!", login)
        Assertions.assertTrue(userRepoService.corruptedDatabase())
        log.info("User {} deleted. Database corrupted!", login)
    }

    @DisplayName(
        "Test check '${UserRepoService.PREDEFINED_GUEST_ACCOUNT_LOGIN}' removed from existing database. " +
                "This is wrong way! Test in ContextStartedEvent as application reboot"
    )
    @Test
    fun checkUserDeletedListenerDatabaseCorrupted() {
        val user: User = userRepository.findByLogin(UserRepoService.PREDEFINED_GUEST_ACCOUNT_LOGIN)!!
        userRepository.delete(user)
        Assertions
            .assertThrows(
                ApplicationContextException::class.java
            ) { contextStartedEventListener.onApplicationEvent(ContextStartedEvent(applicationContext)) }
    }
}