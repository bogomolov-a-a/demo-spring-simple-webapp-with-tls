package org.artembogomolova.demo.webapp.dao.service.auth;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.artembogomolova.demo.webapp.dao.repo.auth.IAuthorityRepository;
import org.artembogomolova.demo.webapp.dao.repo.auth.IRoleRepository;
import org.artembogomolova.demo.webapp.domain.auth.Authority;
import org.artembogomolova.demo.webapp.domain.auth.PredefinedUserRole;
import org.artembogomolova.demo.webapp.domain.auth.Role;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleRepoService {

  private final IRoleRepository userRoleRepository;
  private final IAuthorityRepository authorityRepository;

  public void fillAuthoritiesAndRoles() {
    List<Role> roleList = Arrays.stream(PredefinedUserRole.values()).map(Role::new).collect(Collectors.toList());
    roleList.forEach(this::saveRole);
  }

  private void saveRole(Role role) {
    List<Authority> authorities = role.getAuthorities();
    List<Authority> realAuthorities = role.getAuthorities().stream().map(this::toSavedAuthority).collect(Collectors.toList());
    authorities.clear();
    authorities.addAll(realAuthorities);
    userRoleRepository.save(role);
  }

  private Authority toSavedAuthority(Authority authority) {
    Authority savedAuthority = authorityRepository.findByName(authority.getName());
    return savedAuthority != null ? savedAuthority : new Authority(authority);
  }
}
