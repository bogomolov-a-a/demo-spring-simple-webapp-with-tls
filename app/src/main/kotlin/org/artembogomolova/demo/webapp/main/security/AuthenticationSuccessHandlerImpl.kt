package org.artembogomolova.demo.webapp.main.security

import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.io.IOException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class AuthenticationSuccessHandlerImpl : AuthenticationSuccessHandler {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, authentication: Authentication) {
        println("authenticated successful! ${authentication.name}")
    }
}