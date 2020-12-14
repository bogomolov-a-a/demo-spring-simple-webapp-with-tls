package org.artembogomolova.demo.webapp.test.domain.entity.auth;

import java.util.function.Function;
import org.artembogomolova.demo.webapp.domain.auth.User;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: User")
class UserEntityTest extends AbstractAccessorEntityTest<User> {

  UserEntityTest() {
    super(User.class);
  }

  @Override
  protected User buildStandardEntity() {
    return null;
  }

  @Override
  protected User buildDuplicateEntity(User standardEntity) {
    return null;
  }

  @Override
  protected User buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(User standardEntity, String constraintName, String columnName) {

  }

  @Override
  protected Function<User, ? extends User> getFakeDescendantClassConstructor() {
    return FakeUser::new;
  }

  private static class FakeUser extends User{

    public FakeUser(User user) {
    }
  }
}
