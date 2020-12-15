package org.artembogomolova.demo.webapp.test.context;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.DemoWebappWithTlsApplication;
import org.artembogomolova.demo.webapp.dao.repo.auth.IUserRepository;
import org.artembogomolova.demo.webapp.test.AbstractTest;
import org.artembogomolova.demo.webapp.test.util.event.ContextStartedEventPublishingListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {DemoWebappWithTlsApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles(value = {"test"})
@Slf4j
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, ContextStartedEventPublishingListener.class})
public abstract class AbstractContextLoadTest extends AbstractTest {

  @Getter
  private int serverPort;
  @Autowired
  private IUserRepository userRepository;

  protected AbstractContextLoadTest(int serverPort) {
    this.serverPort = serverPort;
  }

  public void checkServerPortAssigned() {
    Assertions.assertNotEquals(0, serverPort, "Port is 0! This is wrong way!");
    log.info("application started at port {}", serverPort);
  }

  @AfterEach
  void afterTests() {
    userRepository.deleteAll();
  }
}
