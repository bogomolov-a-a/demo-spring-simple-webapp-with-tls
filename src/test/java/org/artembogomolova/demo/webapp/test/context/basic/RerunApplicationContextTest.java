package org.artembogomolova.demo.webapp.test.context.basic;

import org.artembogomolova.demo.webapp.test.context.AbstractContextLoadTest;
import org.artembogomolova.demo.webapp.test.util.event.DoubleTimeContextStartedEventPublisherListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestExecutionListeners;

@TestExecutionListeners({DoubleTimeContextStartedEventPublisherListener.class})
class RerunApplicationContextTest extends AbstractContextLoadTest {

  protected RerunApplicationContextTest(@LocalServerPort int serverPort) {
    super(serverPort);
  }

  @Test
  @DisplayName("Validate condition branch with application reboot on exists database.")
  void contextLoads() {
    checkServerPortAssigned();
  }
}
