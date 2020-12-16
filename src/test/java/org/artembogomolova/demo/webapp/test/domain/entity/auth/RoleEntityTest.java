package org.artembogomolova.demo.webapp.test.domain.entity.auth;

import lombok.EqualsAndHashCode;
import org.artembogomolova.demo.webapp.domain.auth.Role;
import org.artembogomolova.demo.webapp.domain.auth.Role_;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Role")
class RoleEntityTest extends AbstractAccessorEntityTest<Role> {

  private static final String NAME_VALUE = "test role";
  private static final String DESCRIPTION_VALUE = "test role description";
  private static final String NAME_ANOTHER_VALUE = "test another role";

  RoleEntityTest() {
    super(Role.class,
        Role::new,
        MockRole::new);
  }

  @Override
  protected Role buildStandardEntity() {
    Role result = new Role();
    result.setName(NAME_VALUE);
    result.setDescription(DESCRIPTION_VALUE);
    return result;
  }

  @Override
  protected void containFieldCorrectValuesTest(Role standardEntity) {
    Assertions.assertEquals(NAME_VALUE, standardEntity.getName());
    Assertions.assertEquals(DESCRIPTION_VALUE, standardEntity.getDescription());
    Assertions.assertTrue(standardEntity.getAuthorities().isEmpty());
    Assertions.assertTrue(standardEntity.getUsers().isEmpty());
  }

  @Override
  protected Role buildAnotherEntityForTest() {
    Role result = new Role();
    result.setName(NAME_ANOTHER_VALUE);
    result.setDescription(DESCRIPTION_VALUE);
    return result;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Role standardEntity, String constraintName, String columnName) {
    if (Role_.NAME.equals(columnName)) {
      withoutColumnEqualTest(standardEntity, Role::getName, Role::setName);
    }

  }

  @EqualsAndHashCode(callSuper = false)
  private static class MockRole extends Role {

    MockRole(Role role) {
      super(role);
    }
  }
}
