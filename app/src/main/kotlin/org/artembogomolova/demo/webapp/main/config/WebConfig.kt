package org.artembogomolova.demo.webapp.main.config

import org.artembogomolova.demo.webapp.main.controller.PublicController
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

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