package org.artembogomolova.demo.webapp.dao.service.auth;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.dao.repo.auth.IUserRepository;
import org.artembogomolova.demo.webapp.dao.repo.auth.IRoleRepository;
import org.artembogomolova.demo.webapp.domain.auth.Authority;
import org.artembogomolova.demo.webapp.domain.auth.PredefinedUserRole;
import org.artembogomolova.demo.webapp.domain.auth.Role;
import org.artembogomolova.demo.webapp.domain.auth.User;
import org.artembogomolova.demo.webapp.domain.core.Person;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional()
public class UserRepoService {

  public static final String PREDEFINED_ADMIN_ACCOUNT_LOGIN = "admin";
  public static final String PREDEFINED_GUEST_ACCOUNT_LOGIN = "guest";
  private static final String PREDEFINED_ADMIN_ACCOUNT_COUNTRY = "Russia";
  private static final String PREDEFINED_ADMIN_ACCOUNT_POSTAL_CODE = "190000";
  private static final String PREDEFINED_ADMIN_ACCOUNT_CITY_NAME = "Saint Petersburg";
  private static final String CHANGE_IT = "changeit";
  private static final String PREDEFINED_GUEST_PHONE = "+7-000-000-00-00";
  private static final String PREDEFINED_ADMIN_PHONE = "+7-812-812-00-00";
  private static final String PREDEFINED_GUEST_EMAIL = "guest@localhost";
  private static final String PREDEFINED_ADMIN_EMAIL = "admin@localhost";
  private final IUserRepository userRepository;
  private final IRoleRepository userRoleRepository;

  public void createPredefinedSuperUser(PasswordEncoder passwordEncoder) {
    User result = new User();
    result.setLogin(PREDEFINED_ADMIN_ACCOUNT_LOGIN);
    result.setPassword(passwordEncoder.encode(CHANGE_IT));
    Person person = createPredefinedSuperUserPerson();
    log.info("created person: {}", person.toString());
    result.setPerson(person);
    person.setUser(result);
    Role role = userRoleRepository.findByName(PredefinedUserRole.ADMIN.name());
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
    result.setPhone(PREDEFINED_ADMIN_PHONE);
    result.setEmail(PREDEFINED_ADMIN_EMAIL);
    result.setEstateAddress(buildSuperUserAddress());
    return result;
  }

  private PhysicalAddress buildSuperUserAddress() {
    PhysicalAddress result = new PhysicalAddress();
    result.setCountry(PREDEFINED_ADMIN_ACCOUNT_COUNTRY);
    result.setPostalCode(PREDEFINED_ADMIN_ACCOUNT_POSTAL_CODE);
    result.setCity(PREDEFINED_ADMIN_ACCOUNT_CITY_NAME);
    result.setHouse(CHANGE_IT);
    return result;
  }

  public void createPredefinedGuestUser(PasswordEncoder passwordEncoder) {
    User result = new User();
    result.setLogin(PREDEFINED_GUEST_ACCOUNT_LOGIN);
    result.setPassword(passwordEncoder.encode(PREDEFINED_GUEST_ACCOUNT_LOGIN));
    Person person = createPredefinedGuestPerson();
    log.info("created person: {}", person.toString());
    result.setPerson(person);
    person.setUser(result);
    Role role = userRoleRepository.findByName(PredefinedUserRole.GUEST.name());
    result.setRole(role);
    role.getUsers().add(result);
    result.setClientCertificateData(PREDEFINED_GUEST_ACCOUNT_LOGIN);
    log.info("super user role: {}", role.toString());
    role.getAuthorities().stream()
        .sorted(Comparator.comparing(Authority::getName))
        .forEach(x -> log.info("authority enabled: {}", x));
    userRepository.save(result);
  }

  private Person createPredefinedGuestPerson() {
    Person result = new Person();
    result.setBirthDate(new Date(LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond()));
    result.setName(PREDEFINED_GUEST_ACCOUNT_LOGIN);
    result.setSurname(PREDEFINED_GUEST_ACCOUNT_LOGIN);
    result.setPatronymic(PREDEFINED_GUEST_ACCOUNT_LOGIN);
    result.setPhone(PREDEFINED_GUEST_PHONE);
    result.setEmail(PREDEFINED_GUEST_EMAIL);
    result.setEstateAddress(buildGuestAddress());
    return result;
  }

  private PhysicalAddress buildGuestAddress() {
    PhysicalAddress result = new PhysicalAddress();
    result.setCountry(PREDEFINED_GUEST_ACCOUNT_LOGIN);
    result.setPostalCode(PREDEFINED_GUEST_ACCOUNT_LOGIN);
    result.setCity(PREDEFINED_GUEST_ACCOUNT_LOGIN);
    result.setHouse(PREDEFINED_GUEST_ACCOUNT_LOGIN);
    return result;
  }

  public boolean isFirstStart() {
    return userRoleRepository.count() == 0;
  }

  public Authentication getGuestUserToken() {
    User user = userRepository.findByLogin(PREDEFINED_GUEST_ACCOUNT_LOGIN);
    String userLogin = user.getLogin();
    return new AnonymousAuthenticationToken(userLogin, userLogin, user.getRole().getAuthorities());
  }

  public boolean corruptedDatabase() {
    return userRoleRepository.count() > 0 &&
        (userRepository.findByLogin(PREDEFINED_ADMIN_ACCOUNT_LOGIN) == null ||
            userRepository.findByLogin(PREDEFINED_GUEST_ACCOUNT_LOGIN) == null);
  }
}
