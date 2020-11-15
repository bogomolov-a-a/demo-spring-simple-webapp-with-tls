package org.artembogomolova.demo.webapp.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.dao.service.RoleRepoService;
import org.artembogomolova.demo.webapp.dao.service.UserRepoService;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ContextStartedEventListener {

  private final UserRepoService userRepoService;
  private final RoleRepoService roleRepoService;
  private final PasswordEncoder passwordEncoder;

  @EventListener()
  public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
    log.info("application successful started");
    if (!userRepoService.isFirstStart()) {
      return;
    }
    log.info("first start, start initializing authorities, roles, and create super user for log into system");
    roleRepoService.fillAuthoritiesAndRoles();
    log.info("authorities, roles initialized");
    userRepoService.createPredefinedSuperUser(passwordEncoder);
    log.info(" super user for log into system created");
  }

}
