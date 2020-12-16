package org.artembogomolova.demo.webapp.test.domain.entity.business;

import org.artembogomolova.demo.webapp.domain.business.OrderGood;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: OrderGood")
class OrderGoodEntityTest extends AbstractAccessorEntityTest<OrderGood> {

  OrderGoodEntityTest() {
    super(OrderGood.class,
        OrderGood::new,
        MockOrderGood::new);
  }

  @Override
  protected OrderGood buildStandardEntity() {
    return null;
  }

  @Override
  protected OrderGood buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(OrderGood standardEntity, String constraintName, String columnName) {

  }

  private static class MockOrderGood extends OrderGood {

    MockOrderGood(OrderGood orderGood) {
    }
  }
}
