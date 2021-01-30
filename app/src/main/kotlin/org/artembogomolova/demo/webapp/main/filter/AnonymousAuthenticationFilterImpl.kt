package org.artembogomolova.demo.webapp.main.filter

import org.artembogomolova.demo.webapp.main.dao.service.UserRepoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class AnonymousAuthenticationFilterImpl : AnonymousAuthenticationFilter("anonymous") {
    @Autowired
    lateinit var userRepoService: UserRepoService
    override fun createAuthentication(request: HttpServletRequest): Authentication {
        return userRepoService.guestUserToken
    }
}