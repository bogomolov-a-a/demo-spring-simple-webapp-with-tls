package org.artembogomolova.demo.webapp.test.context.basic;

import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.DemoWebappWithTlsApplication;
import org.artembogomolova.demo.webapp.test.context.AbstractContextLoadTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;

@Slf4j
@DisplayName("Run as console command")
class ConsoleContextStartTest extends AbstractContextLoadTest {

  ConsoleContextStartTest(@LocalServerPort int serverPort) {
    super(serverPort);
  }

  @Test
  @DisplayName("Emulate run as console command without main method execution. Main method body must be duplicate this test")
  void contextStarts() {
    DemoWebappWithTlsApplication application = DemoWebappWithTlsApplication.getInstance(new String[0]);
    /*only for test, not use application == this call result ->*/
    DemoWebappWithTlsApplication.getInstance(new String[0]);
    Assertions.assertTrue(application.isRunning());
    log.info("context successful started.");
    application.stop();
    Assertions.assertFalse( application.isRunning());
    log.info("context successful stopped.");
  }

}
