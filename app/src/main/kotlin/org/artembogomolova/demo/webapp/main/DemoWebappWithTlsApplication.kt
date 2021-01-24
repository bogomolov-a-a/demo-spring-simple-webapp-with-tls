package org.artembogomolova.demo.webapp.main

import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.ConfigurableApplicationContext


@SpringBootApplication(
    exclude = [ /*Exclude. Cause - conflicts with custom configuration*/
        SecurityAutoConfiguration::class],
)
class DemoWebappWithTlsApplication : SpringBootServletInitializer() {
    companion object {
        @JvmStatic
        lateinit var INSTANCE: DemoWebappWithTlsApplication

        fun getInstance(args: Array<String>): DemoWebappWithTlsApplication? {
            main(args)
            return INSTANCE
        }

        @JvmStatic
        fun main(args: Array<String>) {
            INSTANCE = DemoWebappWithTlsApplication()
            startContext(args)
        }

        @JvmStatic
        private fun startContext(args: Array<String>) {
            val application = SpringApplication(DemoWebappWithTlsApplication::class.java)
            /*for start context*/
            application.setBannerMode(Banner.Mode.OFF)
            INSTANCE.applicationContext = application.run(*args)
            INSTANCE.applicationContext.start()
        }

    }

    private lateinit var applicationContext: ConfigurableApplicationContext

    fun stop() {
        applicationContext.close()
    }

    fun isRunning(): Boolean {
        return applicationContext.isRunning
    }
}