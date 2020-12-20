package org.artembogomolova.demo.webapp.test.domain.entity.business;

import org.artembogomolova.demo.webapp.domain.business.Order;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Order")
class OrderEntityTest extends AbstractAccessorEntityTest<Order> {

  OrderEntityTest() {
    super(Order.class,
        Order::new,
        MockOrder::new);
  }

  @Override
  protected Order buildStandardEntity() {
    return null;
  }

  @Override
  protected Order buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected boolean withoutBasicConstraint(Order standardEntity, String columnName) {
    return false;
  }

  private static class MockOrder extends Order {

    MockOrder(Order order) {
    }
  }
}
