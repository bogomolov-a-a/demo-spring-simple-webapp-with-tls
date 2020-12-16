package org.artembogomolova.demo.webapp.test.domain.entity.auth;

import org.artembogomolova.demo.webapp.domain.auth.Role;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Role")
class RoleEntityTest extends AbstractAccessorEntityTest<Role> {

  RoleEntityTest() {
    super(Role.class,
        Role::new,
        MockRole::new);
  }

  @Override
  protected Role buildStandardEntity() {
    Role result = new Role();
    return result;
  }

  @Override
  protected Role buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Role standardEntity, String constraintName, String columnName) {

  }

  private static class MockRole extends Role {

    MockRole(Role role) {
      super(role);
    }
  }
}
