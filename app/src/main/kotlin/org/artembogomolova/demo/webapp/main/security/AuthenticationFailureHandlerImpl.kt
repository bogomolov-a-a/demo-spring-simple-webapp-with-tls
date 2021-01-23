package org.artembogomolova.demo.webapp.main.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Service
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class AuthenticationFailureHandlerImpl : AuthenticationFailureHandler {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, e: AuthenticationException) {
        throw UnsupportedOperationException("not implemented yet")
    }
}