package org.artembogomolova.demo.webapp.test.domain.entity.business;

import org.artembogomolova.demo.webapp.domain.business.OrderGood;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;

class OrderGoodEntityTest extends AbstractAccessorEntityTest<OrderGood> {

  OrderGoodEntityTest() {
    super(OrderGood.class);
  }

  @Override
  protected void availableConstraint(String constraintName) {

  }

  @Override
  protected OrderGood buildStandardEntityAndAccessorTest() {
    return null;
  }

  @Override
  protected OrderGood buildDuplicateEntity(OrderGood standardEntity) {
    return null;
  }

  @Override
  protected OrderGood buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(OrderGood standardEntity, String constraintName, String columnName) {

  }
}
