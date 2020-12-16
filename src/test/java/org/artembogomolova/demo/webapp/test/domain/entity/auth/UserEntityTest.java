package org.artembogomolova.demo.webapp.test.domain.entity.auth;

import org.artembogomolova.demo.webapp.domain.auth.User;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: User")
class UserEntityTest extends AbstractAccessorEntityTest<User> {

  UserEntityTest() {
    super(User.class,
        User::new,
        MockUser::new);
  }

  @Override
  protected User buildStandardEntity() {
    return null;
  }

  @Override
  protected User buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(User standardEntity, String constraintName, String columnName) {

  }

  private static class MockUser extends User {

    public MockUser(User user) {
    }
  }
}
