package org.artembogomolova.demo.webapp.test.domain.entity.auth;

import java.util.List;
import lombok.EqualsAndHashCode;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.auth.Role;
import org.artembogomolova.demo.webapp.domain.auth.User;
import org.artembogomolova.demo.webapp.domain.auth.User_;
import org.artembogomolova.demo.webapp.domain.core.Person;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: User")
class UserEntityTest extends AbstractAccessorEntityTest<User> {

  private static final String LOGIN_VALUE = "test login";
  private static final String PASSWORD_VALUE = "password";
  private static final String CERTIFICATE_DATA_VALUE = "test certificate data";
  private static final String AVATAR_VALUE = "avatar data";
  private static final Boolean ACTIVE_VALUE = true;
  private static final Person PERSON_VALUE = new Person();
  private static final Role ROLE_VALUE = new Role();
  private static final String LOGIN_ANOTHER_VALUE = "test login 2";

  UserEntityTest() {
    super(User.class,
        User::new,
        MockUser::new);
  }

  @Override
  protected User buildStandardEntity() {
    User result = new User();
    result.setLogin(LOGIN_VALUE);
    result.setPassword(PASSWORD_VALUE);
    result.setClientCertificateData(CERTIFICATE_DATA_VALUE);
    result.setAvatar(AVATAR_VALUE);
    result.setActive(ACTIVE_VALUE);
    result.setPerson(PERSON_VALUE);
    result.setRole(ROLE_VALUE);
    return result;
  }

  @Override
  protected void containFieldCorrectValuesTest(User standardEntity) {
    Assertions.assertEquals(LOGIN_VALUE, standardEntity.getLogin());
    Assertions.assertEquals(PASSWORD_VALUE, standardEntity.getPassword());
    Assertions.assertEquals(CERTIFICATE_DATA_VALUE, standardEntity.getClientCertificateData());
    Assertions.assertEquals(AVATAR_VALUE, standardEntity.getAvatar());
    Assertions.assertEquals(ACTIVE_VALUE, standardEntity.getActive());
    Assertions.assertEquals(PERSON_VALUE, standardEntity.getPerson());
    Assertions.assertEquals(ROLE_VALUE, standardEntity.getRole());
    Assertions.assertTrue(standardEntity.getBlockAuthorities().isEmpty());

  }

  @Override
  protected User buildAnotherEntityForTest() {
    User result = buildStandardEntity();
    result.setLogin(LOGIN_ANOTHER_VALUE);
    return result;
  }

  @Override
  protected boolean withoutBasicConstraint(User standardEntity, String columnName) {
    if (User_.LOGIN.equals(columnName)) {
      withoutColumnEqualTest(standardEntity, User::getLogin, User::setLogin);
      return true;
    }
    return false;
  }

  protected boolean withoutAlternateConstraints(User standardEntity, String constraintName, String columnName) {
    if (User.CERTIFICATE_DATA_CONSTRAINT_NAME.equals(constraintName)) {
      withoutColumnEqualTest(standardEntity, User::getClientCertificateData, User::setClientCertificateData);
      return true;
    }
    return false;
  }

  @Override
  protected List<String> getAvailableConstraintNames() {
    return List.of(IdentifiedEntity.BASIC_CONSTRAINT_NAME,
        User.CERTIFICATE_DATA_CONSTRAINT_NAME);
  }

  @EqualsAndHashCode(callSuper = false)
  private static class MockUser extends User {

    public MockUser(User user) {
      super(buildCopyingUser(user));
    }

    private static User buildCopyingUser(User user) {
      user.setPerson(null);
      return user;
    }
  }
}
