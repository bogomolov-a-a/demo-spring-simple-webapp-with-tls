package org.artembogomolova.demo.webapp.test.domain.entity.auth;

import java.util.function.Function;
import org.artembogomolova.demo.webapp.domain.auth.Role;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Role")
class RoleEntityTest extends AbstractAccessorEntityTest<Role> {

  RoleEntityTest() {
    super(Role.class);
  }

  @Override
  protected Role buildStandardEntity() {
    return null;
  }

  @Override
  protected Role buildDuplicateEntity(Role standardEntity) {
    return null;
  }

  @Override
  protected Role buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Role standardEntity, String constraintName, String columnName) {

  }

  @Override
  protected Function<Role, ? extends Role> getFakeDescendantClassConstructor() {
    return FakeRole::new;
  }

  private static class FakeRole extends Role {

    FakeRole(Role role) {
    }
  }
}
