package org.artembogomolova.demo.webapp.test.context;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;

class DemoWebappWithTlsApplicationContextTest extends AbstractContextLoadTest {


  DemoWebappWithTlsApplicationContextTest(@LocalServerPort int serverPort) {
    super(serverPort);
  }

  @Test
  @DisplayName("Deploy application test without condition.")
  void contextLoads() {
    checkServerPortAssigned();
  }


}
