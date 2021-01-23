package org.artembogomolova.demo.webapp.main

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication(exclude = [ /*Exclude. Cause - conflicts with custom configuration*/SecurityAutoConfiguration::class],
)
class DemoWebappWithTlsApplication : SpringBootServletInitializer() {
    fun main(args: Array<String>) {
        runApplication<DemoWebappWithTlsApplication>(*args)
    }
}