package org.artembogomolova.demo.webapp.test.domain.db.dao.business;

import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.domain.business.OrderPosition;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Dao test: OrderPosition")
class OrderPositionDaoTest extends AbstractDaoTest<OrderPosition> {

  OrderPositionDaoTest() {
    super(OrderPosition.class, OrderPosition::new);
  }

  @Override
  protected List<OrderPosition> updateEntities(List<OrderPosition> savedCollection) {
    return null;
  }

  @Override
  protected CrudRepository<OrderPosition, Long> getCrudRepository() {
    return null;
  }

  @Override
  protected List<OrderPosition> generateEntities() {
    return null;
  }

  @Override
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected OrderPosition doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint, Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected OrderPosition doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
    return null;
  }
}
