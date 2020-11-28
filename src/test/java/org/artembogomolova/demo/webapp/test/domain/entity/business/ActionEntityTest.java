package org.artembogomolova.demo.webapp.test.domain.entity.business;

import java.util.Calendar;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.domain.business.Action;
import org.artembogomolova.demo.webapp.domain.business.Category;
import org.artembogomolova.demo.webapp.domain.business.Good;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@Slf4j
@DisplayName("Entity test: Action")

public class ActionEntityTest extends AbstractAccessorEntityTest<Action> {

  protected ActionEntityTest() {
    super(Action.class);
  }


  @Override
  protected Action buildStandardEntityAndAccessorTest() {
    Action result = new Action();
    updateNameAndDescription(result);
    updateDiscountInfo(result);
    updateDateInfo(result);
    updateLinks(result);
    return result;
  }

  @Override
  protected Action buildDuplicateEntity(Action standardEntity) {
    return new Action(standardEntity);
  }

  private void updateLinks(Action standard) {
    standard.getGoods().add(new Good());
    if (standard.getCategory() == null) {
      standard.setCategory(new Category());
      standard.setCategoryId(1l);
    }
  }

  private void updateDateInfo(Action standard) {
    if (standard.getEndDate() == null) {
      standard.setEndDate(Calendar.getInstance().getTime());
      log.info("now set endDate is " + standard.getEndDate().toString());
      standard.setEndDate(null);
    }
    if (standard.getStartDate() == null) {
      standard.setStartDate(Calendar.getInstance().getTime());
      log.info("now set startDate is " + standard.getStartDate().toString());
    }
  }

  private void updateDiscountInfo(Action standard) {
    if (standard.getDiscountFixed() == null) {
      standard.setDiscountFixed(420.0f);
    }
    if (standard.getDiscountPercent() == null) {
      standard.setDiscountFixed(42.0f);
    }
  }

  private void updateNameAndDescription(Action standard) {
    if (standard.getName() == null) {
      standard.setName("dummy name");
    }
    if (standard.getDescription() == null) {
      standard.setDescription("dummy description");
    }
  }

  @Override
  protected Action buildAnotherEntityForTest() {
    return DomainTestUtil.buildCategoryAction();
  }

  @Override
  protected void availableConstraint(String constraintName) {
    if (!Action.BASIC_CONSTRAINT_NAME.equals(constraintName)) {
      throw new IllegalArgumentException("constraintName must be only '" + Action.BASIC_CONSTRAINT_NAME + "'");
    }
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Action standardEntity, String constraintName, String columnName) {

    switch (columnName) {
      case Action.NAME_FIELD_NAME: {
        assertWithOutNameEquals(standardEntity);
        return;
      }
      case Action.START_DATE_FIELD_NAME: {
        assertWithOutStartDate(standardEntity);
        return;
      }
    }
  }

  private void assertWithOutNameEquals(Action standard) {
    Action action = new Action();
    Assertions.assertNotEquals(standard, action);
    Assertions.assertNotEquals(action, standard);
    Assertions.assertNotEquals(standard.hashCode(), action.hashCode());
    action.setName(null);
    standard.setName(null);
    action.setStartDate(standard.getStartDate());
    Assertions.assertEquals(standard, action);
  }

  private void assertWithOutStartDate(Action standard) {
    Action action = new Action();
    Assertions.assertNotEquals(standard, action);
    Assertions.assertNotEquals(action, standard);
    Assertions.assertNotEquals(standard.hashCode(), action.hashCode());
    action.setName(standard.getName());
    action.setStartDate(standard.getStartDate());
    Assertions.assertEquals(standard, action);
    action.setStartDate(null);
    Assertions.assertNotEquals(standard, action);
    action.setStartDate(standard.getStartDate());
    standard.setStartDate(null);
    Assertions.assertNotEquals(standard, action);
    action.setStartDate(null);
    Assertions.assertEquals(standard, action);
  }
}
