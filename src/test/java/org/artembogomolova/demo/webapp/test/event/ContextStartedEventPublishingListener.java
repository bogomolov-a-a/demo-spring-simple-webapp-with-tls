package org.artembogomolova.demo.webapp.test.event;

import org.artembogomolova.demo.webapp.dao.repo.IAuthorityRepository;
import org.artembogomolova.demo.webapp.dao.repo.IUserRepository;
import org.artembogomolova.demo.webapp.dao.repo.IUserRoleRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.event.EventPublishingTestExecutionListener;

public class ContextStartedEventPublishingListener extends EventPublishingTestExecutionListener {

  @Override
  public void beforeTestMethod(TestContext testContext) {
    super.beforeTestExecution(testContext);
    publishStartedEvent(testContext);
  }

  protected void publishStartedEvent(TestContext testContext) {
    ApplicationContext applicationContext = testContext.getApplicationContext();
    applicationContext.publishEvent(new ContextStartedEvent(applicationContext));
  }

  @Override
  public void afterTestMethod(TestContext testContext) {
    ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) testContext.getApplicationContext();
    IUserRepository userRepository = applicationContext.getBeanFactory().getBean(IUserRepository.class);
    IUserRoleRepository userRoleRepository = applicationContext.getBeanFactory().getBean(IUserRoleRepository.class);
    IAuthorityRepository authorityRepository = applicationContext.getBeanFactory().getBean(IAuthorityRepository.class);
    userRepository.deleteAll();
    userRoleRepository.deleteAll();
    authorityRepository.deleteAll();
  }
}
