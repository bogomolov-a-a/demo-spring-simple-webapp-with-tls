package org.artembogomolova.demo.webapp.dao.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.artembogomolova.demo.webapp.dao.repo.IUserRoleRepository;
import org.artembogomolova.demo.webapp.domain.auth.PredefinedUserRole;
import org.artembogomolova.demo.webapp.domain.auth.Role;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleRepoService {

  private final IUserRoleRepository userRoleRepository;

  public void fillAuthoritiesAndRoles() {
    List<Role> roleList = Arrays.stream(PredefinedUserRole.values()).map(Role::new).collect(Collectors.toList());
    userRoleRepository.saveAll(roleList);
  }
}
