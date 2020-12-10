package org.artembogomolova.demo.webapp.test.domain.entity.auth;

import org.artembogomolova.demo.webapp.domain.auth.Role;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;

class RoleEntityTest extends AbstractAccessorEntityTest<Role> {

  RoleEntityTest() {
    super(Role.class);
  }

  @Override
  protected void availableConstraint(String constraintName) {

  }

  @Override
  protected Role buildStandardEntityAndAccessorTest() {
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
}
