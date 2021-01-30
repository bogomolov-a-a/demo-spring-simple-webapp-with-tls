package org.artembogomolova.demo.webapp.main.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
@ComponentScan("org.artembogomolova.demo.webapp.main.controller", "org.artembogomolova.demo.webapp.main.filter")
class WebConfig : WebMvcConfigurer