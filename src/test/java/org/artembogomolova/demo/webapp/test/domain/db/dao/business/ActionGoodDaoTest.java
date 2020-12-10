package org.artembogomolova.demo.webapp.test.domain.db.dao.business;

import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.domain.business.ActionGood;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Dao test: ActionGood")
class ActionGoodDaoTest extends AbstractDaoTest<ActionGood> {

  ActionGoodDaoTest() {
    super(ActionGood.class);
  }

  @Override
  protected List<ActionGood> updateEntities(List<ActionGood> savedCollection) {
    return null;
  }

  @Override
  protected CrudRepository<ActionGood, Long> getCrudRepository() {
    return null;
  }

  @Override
  protected List<ActionGood> generateEntities() {
    return null;
  }

  @Override
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected ActionGood doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint, Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected ActionGood doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
    return null;
  }
}
