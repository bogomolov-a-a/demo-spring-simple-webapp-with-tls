package org.artembogomolova.demo.webapp.test.domain.entity.auth;

import org.artembogomolova.demo.webapp.domain.auth.Authority;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;

class AuthorityEntityTest extends AbstractAccessorEntityTest<Authority> {

  AuthorityEntityTest() {
    super(Authority.class);
  }

  @Override
  protected void availableConstraint(String constraintName) {

  }

  @Override
  protected Authority buildStandardEntityAndAccessorTest() {
    return null;
  }

  @Override
  protected Authority buildDuplicateEntity(Authority standardEntity) {
    return null;
  }

  @Override
  protected Authority buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Authority standardEntity, String constraintName, String columnName) {

  }
}
