package org.artembogomolova.demo.webapp.test.domain.entity.business;

import org.artembogomolova.demo.webapp.domain.business.Order;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Order")
class OrderEntityTest extends AbstractAccessorEntityTest<Order> {

  OrderEntityTest() {
    super(Order.class);
  }

  @Override
  protected void availableConstraint(String constraintName) {

  }

  @Override
  protected Order buildStandardEntityAndAccessorTest() {
    return null;
  }

  @Override
  protected Order buildDuplicateEntity(Order standardEntity) {
    return null;
  }

  @Override
  protected Order buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Order standardEntity, String constraintName, String columnName) {

  }
}
