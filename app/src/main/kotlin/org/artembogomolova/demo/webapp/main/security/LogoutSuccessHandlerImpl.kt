package org.artembogomolova.demo.webapp.main.security

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Service
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class LogoutSuccessHandlerImpl : LogoutSuccessHandler {
    @Throws(IOException::class, ServletException::class)
    override fun onLogoutSuccess(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, authentication: Authentication) {
        println("successful logout ${authentication.name}")
    }
}