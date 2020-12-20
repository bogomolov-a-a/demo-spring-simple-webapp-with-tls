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

  private static DemoWebappWithTlsApplication INSTANCE;
  protected ConfigurableApplicationContext applicationContext;

  public static DemoWebappWithTlsApplication getInstance(String[] args) {
    if (INSTANCE != null) {
      return INSTANCE;
    }
    INSTANCE = new DemoWebappWithTlsApplication();
    main(args);
    return INSTANCE;
  }

  public static void main(String[] args) {
    startContext(args);
  }

  private static void startContext(String[] args) {

    SpringApplication application = new SpringApplication(DemoWebappWithTlsApplication.class);
    /*for start context*/
    application.setBannerMode(Mode.OFF);
    ConfigurableApplicationContext context = application.run(args);
    context.start();
    INSTANCE.applicationContext = context;
  }

  public void stop() {
    applicationContext.close();
  }

  public boolean isRunning() {
    return applicationContext.isRunning();
  }
}
