package org.artembogomolova.demo.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = {
    /*Exclude. Cause - conflicts with custom configuration*/
    SecurityAutoConfiguration.class})
public class DemoWebappWithTlsApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(DemoWebappWithTlsApplication.class, args);
  }

}
