package org.artembogomolova.demo.webapp.test.context.db;

import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.dao.repo.auth.IUserRepository;
import org.artembogomolova.demo.webapp.dao.service.auth.UserRepoService;
import org.artembogomolova.demo.webapp.domain.auth.User;
import org.artembogomolova.demo.webapp.event.ContextStartedEventListener;
import org.artembogomolova.demo.webapp.test.context.AbstractContextLoadTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextStartedEvent;

@Slf4j
class UserRepoServiceCorrectTest extends AbstractContextLoadTest {

  UserRepoServiceCorrectTest(@LocalServerPort int serverPort) {
    super(serverPort);
  }

  @Autowired
  private IUserRepository userRepository;
  @Autowired
  private UserRepoService userRepoService;

  @Autowired
  private ConfigurableApplicationContext applicationContext;
  @Autowired
  private ContextStartedEventListener contextStartedEventListener;

  @Test
  @DisplayName("Test check all predefined users removed from existing database. This is wrong way!")
  void checkAllUsersDeletedDatabaseCorrupted() {
    userRepository.deleteAll();
    log.info("All users deleted. Database must be corrupted, check it!");
    Assertions.assertTrue(userRepoService.corruptedDatabase());
    log.info("All users deleted. Database corrupted!");
  }

  @DisplayName("Test check " + UserRepoService.PREDEFINED_ADMIN_ACCOUNT_LOGIN + " removed from existing database. This is wrong way!")
  @Test
  void checkAdminUserDeletedDatabaseCorrupted() {
    User user = userRepository.findByLogin(UserRepoService.PREDEFINED_ADMIN_ACCOUNT_LOGIN);
    userRepository.delete(user);
    checkUserDeletedDatabaseCorrupted(user.getLogin());

  }

  @DisplayName("Test check " + UserRepoService.PREDEFINED_GUEST_ACCOUNT_LOGIN + " removed from existing database. This is wrong way!")
  @Test
  void checkGuestUserDeletedDatabaseCorrupted() {
    User user = userRepository.findByLogin(UserRepoService.PREDEFINED_GUEST_ACCOUNT_LOGIN);
    userRepository.delete(user);
    checkUserDeletedDatabaseCorrupted(user.getLogin());
  }

  private void checkUserDeletedDatabaseCorrupted(String login) {
    log.info("User {} deleted. Database must be corrupted, check it!", login);
    Assertions.assertTrue(userRepoService.corruptedDatabase());
    log.info("User {} deleted. Database corrupted!", login);
  }

  @DisplayName("Test check " + UserRepoService.PREDEFINED_GUEST_ACCOUNT_LOGIN
      + " removed from existing database. This is wrong way! Test in ContextStartedEvent as application reboot")
  @Test
  void checkUserDeletedListenerDatabaseCorrupted() {
    User user = userRepository.findByLogin(UserRepoService.PREDEFINED_GUEST_ACCOUNT_LOGIN);
    userRepository.delete(user);
    Assertions
        .assertThrows(ApplicationContextException.class, () -> contextStartedEventListener.onApplicationEvent(new ContextStartedEvent(applicationContext)));
  }

}
