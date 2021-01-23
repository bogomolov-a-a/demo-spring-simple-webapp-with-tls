package org.artembogomolova.demo.webapp.main.config

import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.artembogomolova.demo.webapp.main.controller.PublicController
import org.springframework.context.annotation.Configuration

@Configuration
@EnableWebMvc
@ComponentScan("org.artembogomolova.demo.webapp.main.controller", "org.artembogomolova.demo.webapp.main.filter")
class WebConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler(PublicController.TEMPLATE_RESOURCES_URL)
            .addResourceLocations("classpath:/templates/")
        registry.addResourceHandler(PublicController.CSS_RESOURCES_URL)
            .addResourceLocations("classpath:/css/")
        registry.addResourceHandler(PublicController.WEBJARS_RESOURCES_URL)
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }
}