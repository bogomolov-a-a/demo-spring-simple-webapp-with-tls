package org.artembogomolova.demo.webapp.test.domain.db.dao.business;

import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.domain.business.OrderGood;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Dao test: OrderGood")
class OrderGoodDaoTest extends AbstractDaoTest<OrderGood> {

  OrderGoodDaoTest() {
    super(OrderGood.class, OrderGood::new);
  }

  @Override
  protected List<OrderGood> updateEntities(List<OrderGood> savedCollection) {
    return null;
  }

  @Override
  protected CrudRepository<OrderGood, Long> getCrudRepository() {
    return null;
  }

  @Override
  protected List<OrderGood> generateEntities() {
    return null;
  }

  @Override
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected OrderGood doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint, Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected OrderGood doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
    return null;
  }
}
