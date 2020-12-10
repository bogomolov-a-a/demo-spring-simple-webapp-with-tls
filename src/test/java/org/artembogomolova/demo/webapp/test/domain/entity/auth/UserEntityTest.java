package org.artembogomolova.demo.webapp.test.domain.entity.auth;

import org.artembogomolova.demo.webapp.domain.auth.User;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;

class UserEntityTest extends AbstractAccessorEntityTest<User> {

  UserEntityTest() {
    super(User.class);
  }

  @Override
  protected void availableConstraint(String constraintName) {

  }

  @Override
  protected User buildStandardEntityAndAccessorTest() {
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
}
