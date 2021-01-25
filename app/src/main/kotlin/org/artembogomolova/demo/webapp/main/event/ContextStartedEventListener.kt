package org.artembogomolova.demo.webapp.main.event

import org.artembogomolova.demo.webapp.main.dao.service.RoleRepoService
import org.artembogomolova.demo.webapp.main.dao.service.UserRepoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContextException
import org.springframework.context.event.ContextStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class ContextStartedEventListener {
    @Autowired
    lateinit var userRepoService: UserRepoService

    @Autowired
    lateinit var roleRepoService: RoleRepoService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @EventListener
    fun onApplicationEvent(contextStartedEvent: ContextStartedEvent?) {
        println("application successful started")
        if (userRepoService.corruptedDatabase()) {
            throw ApplicationContextException("Database corrupted! Predefined admin and guest user deleted with unauthorized access!!!");
        }
        if (!userRepoService.isFirstStart) {
            return
        }
        println("first start, start initializing authorities, roles, and create super user for log into system")
        roleRepoService.fillAuthoritiesAndRoles()
        println("authorities, roles initialized")
        userRepoService.createPredefinedSuperUser(passwordEncoder)
        println(" super user for log into system created")
        userRepoService.createPredefinedGuestUser(passwordEncoder)
        println(" guest user for log into system created")
    }
}