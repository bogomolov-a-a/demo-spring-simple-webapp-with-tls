package org.artembogomolova.demo.webapp.test.domain.entity.business;

import java.util.Calendar;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.business.Action;
import org.artembogomolova.demo.webapp.domain.business.Action_;
import org.artembogomolova.demo.webapp.domain.business.Category;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@Slf4j
@DisplayName("Entity test: Action")
class ActionEntityTest extends AbstractAccessorEntityTest<Action> {

  private static final String NAME_VALUE = "test action";
  private static final Date START_DATE_VALUE = Calendar.getInstance().getTime();
  private static final Date END_DATE_VALUE = Calendar.getInstance().getTime();
  private static final String DESCRIPTION_VALUE = "test action description";
  private static final Float DISCOUNT_FIXED_VALUE = 42.0f;
  private static final Float DISCOUNT_PERCENT_VALUE = 4.2f;
  private static final Long CATEGORY_ID_VALUE = 1L;
  private static final Category CATEGORY_VALUE = new Category();

  ActionEntityTest() {
    super(Action.class,
        Action::new,
        MockAction::new);
  }

  @Override
  protected Action buildStandardEntity() {
    Action result = new Action();
    result.setName(NAME_VALUE);
    result.setStartDate(START_DATE_VALUE);
    result.setEndDate(END_DATE_VALUE);
    result.setDescription(DESCRIPTION_VALUE);
    result.setDiscountFixed(DISCOUNT_FIXED_VALUE);
    result.setDiscountPercent(DISCOUNT_PERCENT_VALUE);
    result.setCategoryId(CATEGORY_ID_VALUE);
    result.setCategory(CATEGORY_VALUE);
    CATEGORY_VALUE.setId(CATEGORY_ID_VALUE);
    return result;
  }

  @Override
  protected void containFieldCorrectValuesTest(Action standardEntity) {
    Assertions.assertEquals(NAME_VALUE, standardEntity.getName());
    Assertions.assertEquals(START_DATE_VALUE, standardEntity.getStartDate());
    Assertions.assertEquals(END_DATE_VALUE, standardEntity.getEndDate());
    Assertions.assertEquals(DESCRIPTION_VALUE, standardEntity.getDescription());
    Assertions.assertEquals(DISCOUNT_FIXED_VALUE, standardEntity.getDiscountFixed());
    Assertions.assertEquals(DISCOUNT_PERCENT_VALUE, standardEntity.getDiscountPercent());
    Assertions.assertEquals(CATEGORY_ID_VALUE, standardEntity.getCategoryId());
    Assertions.assertEquals(CATEGORY_VALUE, standardEntity.getCategory());
    Assertions.assertEquals(CATEGORY_ID_VALUE, standardEntity.getCategory().getId());
  }

  @Override
  protected Action buildAnotherEntityForTest() {
    Action result = DomainTestUtil.buildCategoryAction();
    /*check null end date setter*/
    log.info("end date: {}", result.getEndDate());
    result.setEndDate(null);
    log.info("end date: {}", result.getEndDate());
    return result;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Action standardEntity, String constraintName, String columnName) {

    if (IdentifiedEntity.BASIC_CONSTRAINT_NAME.equals(constraintName)) {
      withoutBasicConstraintEqualTest(standardEntity, columnName);
    }
  }

  private void withoutBasicConstraintEqualTest(Action standardEntity, String columnName) {
    switch (columnName) {
      case Action_.NAME: {
        withoutColumnEqualTest(standardEntity, Action::getName, Action::setName);
        return;
      }
      case Action_.START_DATE: {
        withoutColumnEqualTest(standardEntity, Action::getStartDate, Action::setStartDate);
        return;
      }
      default: {
        return;
      }
    }
  }

  @EqualsAndHashCode(callSuper = false)
  private static class MockAction extends Action {

    MockAction(Action action) {
      super(buildAction(action));
    }

    private static Action buildAction(Action action) {
      action.setCategory(null);
      return action;
    }
  }
}
