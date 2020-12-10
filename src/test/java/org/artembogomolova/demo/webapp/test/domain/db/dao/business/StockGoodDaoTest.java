package org.artembogomolova.demo.webapp.test.domain.db.dao.business;

import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.domain.business.StockGood;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Dao test: StockGood")
class StockGoodDaoTest extends AbstractDaoTest<StockGood> {

  StockGoodDaoTest() {
    super(StockGood.class);
  }

  @Override
  protected List<StockGood> updateEntities(List<StockGood> savedCollection) {
    return null;
  }

  @Override
  protected CrudRepository<StockGood, Long> getCrudRepository() {
    return null;
  }

  @Override
  protected List<StockGood> generateEntities() {
    return null;
  }

  @Override
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected StockGood doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint, Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected StockGood doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
    return null;
  }
}
