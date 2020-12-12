package org.artembogomolova.demo.webapp.test.domain.entity.business;

import org.artembogomolova.demo.webapp.domain.business.OrderPosition;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: OrderPosition")
class OrderPositionEntityTest extends AbstractAccessorEntityTest<OrderPosition> {

  OrderPositionEntityTest() {
    super(OrderPosition.class);
  }

  @Override
  protected OrderPosition buildStandardEntity() {
    return null;
  }

  @Override
  protected OrderPosition buildDuplicateEntity(OrderPosition standardEntity) {
    return null;
  }

  @Override
  protected OrderPosition buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(OrderPosition standardEntity, String constraintName, String columnName) {

  }
}
