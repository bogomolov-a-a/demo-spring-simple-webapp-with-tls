package org.artembogomolova.demo.webapp.main.config.web

import org.artembogomolova.demo.webapp.main.controller.PublicController
import org.artembogomolova.demo.webapp.main.domain.auth.PredefinedUserRole
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter

@Configuration
@EnableWebSecurity
@ComponentScan(
    value = ["org.artembogomolova.demo.webapp.main.security",
        "org.artembogomolova.demo.webapp.main.controller",
        "org.artembogomolova.demo.webapp.main.filter"]
)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var anonymousAuthenticationFilterImpl: AnonymousAuthenticationFilter

    @get:Bean
    val passwordEncoder: PasswordEncoder
        get() = BCryptPasswordEncoder()

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        configureLoginProcess(http)
        configureLogoutProcess(http)
        configurePublicResources(http)
    }

    @Throws(Exception::class)
    private fun configurePublicResources(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers(*PublicController.unsecuredResources)
            .hasAnyAuthority(*PredefinedUserRole.GUEST.privilegesAsArray)
        /*For guest main page*/
        http.addFilterAt(anonymousAuthenticationFilterImpl, AnonymousAuthenticationFilter::class.java)
    }

    @Throws(Exception::class)
    private fun configureLogoutProcess(http: HttpSecurity) {
        http.logout()
            .logoutUrl(LOGOUT_URL)
            //.logoutSuccessUrl(PublicController.LOGIN_URL)
            .invalidateHttpSession(true)

    }

    /**
     * login page with failure callback
     */
    @Throws(Exception::class)
    private fun configureLoginProcess(http: HttpSecurity) {
        http.formLogin()
    }

    companion object {
        private const val LOGOUT_URL = "/logout"
    }
}