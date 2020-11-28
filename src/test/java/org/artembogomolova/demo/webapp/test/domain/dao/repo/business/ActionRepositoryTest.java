package org.artembogomolova.demo.webapp.test.domain.dao.repo.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.dao.repo.business.IActionRepository;
import org.artembogomolova.demo.webapp.domain.business.Action;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.dao.repo.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Action entity repository test")
public class ActionRepositoryTest extends AbstractDaoTest<Action> {

  @Autowired
  private IActionRepository actionRepository;

  ActionRepositoryTest() {
    super(Action.class);
  }

  @Override
  protected List<Action> updateEntities(List<Action> savedCollection) {
    savedCollection.get(0)
        .setDiscountFixed(420.0f);
    return savedCollection;
  }

  @Override
  protected CrudRepository<Action, Long> getCrudRepository() {
    return actionRepository;
  }

  @Override
  protected List<Action> generateEntities() {
    List<Action> result = new ArrayList<>();
    /*add action for good with cat*/
    result.add(DomainTestUtil.buildGoodAction());
    /**/
    result.add(DomainTestUtil.buildCategoryAction());
    return result;
  }

  @Override
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraintColumns uniqueMultiColumnConstraintColumns) {
    Map<String, Object> result = new HashMap<>();
    result.put(Action.START_DATE_FIELD_NAME, Calendar.getInstance().getTime());
    return result;
  }

  @Override
  protected Action doPrepareDeniedTestEntity(UniqueMultiColumnConstraintColumns uniqueMultiColumnConstraintColumns,
      Map<String, Object> commonValues) {
    Action result = DomainTestUtil.buildCategoryAction();
    result.setCategory(DomainTestUtil.buildCategory2());
    result.setStartDate((Date) commonValues.get(Action.START_DATE_FIELD_NAME));
    return result;
  }

  @Override
  protected Action doDuplicateDeniedTestEntity(UniqueMultiColumnConstraintColumns columns, Map<String, Object> commonValues) {
    Action result = DomainTestUtil.buildCategoryAction();
    result.setStartDate((Date) commonValues.get(Action.START_DATE_FIELD_NAME));
    return result;
  }

}
