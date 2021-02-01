package org.artembogomolova.demo.webapp.main.filter

import javax.servlet.http.HttpServletRequest
import org.artembogomolova.demo.webapp.main.dao.service.UserRepoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.stereotype.Component

@Component
class AnonymousAuthenticationFilterImpl @Autowired constructor(
    private val userRepoService: UserRepoService
) : AnonymousAuthenticationFilter("anonymous") {

    override fun createAuthentication(request: HttpServletRequest): Authentication {
        return userRepoService.guestUserToken
    }
}