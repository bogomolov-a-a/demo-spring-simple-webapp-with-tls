package org.artembogomolova.demo.webapp.dao.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.dao.repo.IAuthorityRepository;
import org.artembogomolova.demo.webapp.dao.repo.IUserRepository;
import org.artembogomolova.demo.webapp.dao.repo.IUserRoleRepository;
import org.artembogomolova.demo.webapp.domain.auth.Authority;
import org.artembogomolova.demo.webapp.domain.auth.PredefinedUserRole;
import org.artembogomolova.demo.webapp.domain.auth.Role;
import org.artembogomolova.demo.webapp.domain.auth.User;
import org.artembogomolova.demo.webapp.domain.core.Person;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRepoService {

  private static final String PREDEFINED_ADMIN_ACCOUNT_LOGIN = "admin";
  private static final String PREDEFINED_ADMIN_ACCOUNT_COUNTRY = "Russia";
  private static final String PREDEFINED_ADMIN_ACCOUNT_POSTAL_CODE = "190000";
  private static final String PREDEFINED_ADMIN_ACCOUNT_CITY_NAME = "Saint Petersburg";
  private static final String CHANGE_IT = "changeit";

  private final IUserRepository userRepository;
  private final IUserRoleRepository userRoleRepository;
  private final IAuthorityRepository authorityRepository;

  @Transactional()
  public void createPredefinedSuperUser(PasswordEncoder passwordEncoder) {
    User result = new User();
    result.setLogin(PREDEFINED_ADMIN_ACCOUNT_LOGIN);
    result.setPassword(passwordEncoder.encode(CHANGE_IT));
    Person person = createPredefinedSuperUserPerson();
    log.info("created person: {}", person.toString());
    result.setPerson(person);
    person.setUser(result);
    Role role = userRoleRepository.findByName(PredefinedUserRole.ROLE_ADMIN.name());
    result.setRole(role);
    role.getUsers().add(result);
    result.setClientCertificateData(CHANGE_IT);
    log.info("super user role: {}", role.toString());
    role.getAuthorities().stream()
        .sorted(Comparator.comparing(Authority::getName))
        .forEach(x -> log.info("authority enabled: {}", x));
    userRepository.save(result);
  }

  private Person createPredefinedSuperUserPerson() {
    Person result = new Person();
    result.setBirthDate(new Date(LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond()));
    result.setName(CHANGE_IT);
    result.setSurname(CHANGE_IT);
    result.setPatronymic(CHANGE_IT);
    result.setPhone(CHANGE_IT);
    result.setEstateAddress(buildAddress());
    return result;
  }

  private PhysicalAddress buildAddress() {
    PhysicalAddress result = new PhysicalAddress();
    result.setCountry(PREDEFINED_ADMIN_ACCOUNT_COUNTRY);
    result.setPostalCode(PREDEFINED_ADMIN_ACCOUNT_POSTAL_CODE);
    result.setCity(PREDEFINED_ADMIN_ACCOUNT_CITY_NAME);
    result.setHouse(CHANGE_IT);
    return result;
  }

  public boolean isFirstStart() {
    return userRepository.count() == 0 &&
        userRoleRepository.count() == 0 &&
        authorityRepository.count() == 0;
  }
}