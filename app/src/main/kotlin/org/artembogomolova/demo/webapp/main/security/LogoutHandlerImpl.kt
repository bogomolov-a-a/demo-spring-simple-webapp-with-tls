package org.artembogomolova.demo.webapp.main.security

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class LogoutHandlerImpl : LogoutHandler {
    override fun logout(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, authentication: Authentication) {
        println("trying logout ${authentication.name}")
    }
}