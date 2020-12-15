package org.artembogomolova.demo.webapp.test.context.basic;

import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.DemoWebappWithTlsApplication;
import org.artembogomolova.demo.webapp.test.context.AbstractContextLoadTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@DisplayName("Run as console command")
class ConsoleContextStartTest extends AbstractContextLoadTest {

  ConsoleContextStartTest(@LocalServerPort int serverPort ) {
    super(serverPort);
  }

  @Test
  @DisplayName("Emulate run as console command without main method execution. Main method body must be duplicate this test")
  void contextStarts() {
    ConfigurableApplicationContext context = DemoWebappWithTlsApplication.startContext(new String[0]);
    log.info("context successful started.");
    context.stop();
    log.info("context successful stopped.");
  }

}
