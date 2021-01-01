package org.artembogomolova.demo.webapp.config;

import lombok.RequiredArgsConstructor;
import org.artembogomolova.demo.webapp.controller.PublicController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
@ComponentScan({"org.artembogomolova.demo.webapp.controller", "org.artembogomolova.demo.webapp.filter"})
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(PublicController.TEMPLATE_RESOURCES_URL)
        .addResourceLocations("classpath:/templates/");
    registry.addResourceHandler(PublicController.CSS_RESOURCES_URL)
        .addResourceLocations("classpath:/css/");
    registry.addResourceHandler(PublicController.WEBJARS_RESOURCES_URL)
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

}
