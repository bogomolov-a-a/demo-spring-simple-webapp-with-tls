package org.artembogomolova.demo.webapp;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {
    /*Exclude. Cause - conflicts with custom configuration*/
    SecurityAutoConfiguration.class})
public class DemoWebappWithTlsApplication extends SpringBootServletInitializer {

  static ConfigurableApplicationContext context;

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(DemoWebappWithTlsApplication.class);
    /*for start context*/
    application.setBannerMode(Mode.OFF);
    context = application.run(args);
    context.start();
  }

}
