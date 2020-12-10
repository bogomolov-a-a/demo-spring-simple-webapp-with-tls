package org.artembogomolova.demo.webapp.test.domain.entity.business;

import org.artembogomolova.demo.webapp.domain.business.ActionGood;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;

class ActionGoodEntityTest extends AbstractAccessorEntityTest<ActionGood> {

  ActionGoodEntityTest() {
    super(ActionGood.class);
  }

  @Override
  protected void availableConstraint(String constraintName) {

  }

  @Override
  protected ActionGood buildStandardEntityAndAccessorTest() {
    return null;
  }

  @Override
  protected ActionGood buildDuplicateEntity(ActionGood standardEntity) {
    return null;
  }

  @Override
  protected ActionGood buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(ActionGood standardEntity, String constraintName, String columnName) {

  }
}
