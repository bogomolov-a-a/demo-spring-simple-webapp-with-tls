package org.artembogomolova.demo.webapp;

import org.artembogomolova.demo.webapp.dao.repo.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;

public class ApplicationIntegrationConsoleTest {

  @Test
  public void applicationRun() {
    DemoWebappWithTlsApplication.main(new String[]{});
    ConfigurableApplicationContext context = DemoWebappWithTlsApplication.context;
    IUserRepository userRepository = context.getBeanFactory().getBean(IUserRepository.class);
    userRepository.deleteAll();
    context.stop();
    DemoWebappWithTlsApplication.context = null;
    /*second time */
    DemoWebappWithTlsApplication.main(new String[]{});
    DemoWebappWithTlsApplication.context.stop();
    DemoWebappWithTlsApplication.context = null;
  }

}
