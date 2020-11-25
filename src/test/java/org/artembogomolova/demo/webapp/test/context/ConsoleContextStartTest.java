package org.artembogomolova.demo.webapp.test.context;

import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.DemoWebappWithTlsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
class ConsoleContextStartTest {

  @Test
  void contextStarts() {
    ConfigurableApplicationContext context = DemoWebappWithTlsApplication.startContext(new String[0]);
    log.info("context successful started.");
    context.stop();
    log.info("context successful stopped.");
  }

}
