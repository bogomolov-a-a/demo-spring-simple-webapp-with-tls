package org.artembogomolova.demo.webapp;

import org.artembogomolova.demo.webapp.DemoWebappWithTlsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {DemoWebappWithTlsApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles(value = {"test"})
public class DemoWebappWithTlsApplicationTests {

  @LocalServerPort
  private int serverPort;

  @Test
  void contextLoads() {
  }

}
