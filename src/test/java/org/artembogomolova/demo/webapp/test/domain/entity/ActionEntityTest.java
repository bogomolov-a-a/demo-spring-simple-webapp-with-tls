package org.artembogomolova.demo.webapp.test.domain.entity;

import java.util.Calendar;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.domain.business.Action;
import org.artembogomolova.demo.webapp.domain.business.Category;
import org.artembogomolova.demo.webapp.domain.business.Good;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class ActionEntityTest {

  @Test
  void testActionAccessors() {
    assertCategoryActionEquals(buildStandardAction());
    assertFullEquals(buildStandardAction());
    assertWithOutNameEquals(buildStandardAction());
    assertWithOutCategoryId(buildStandardAction());
    assertWithOutGoodId(buildStandardAction());
    assertWithOutStartDate(buildStandardAction());
  }


  private Action buildStandardAction() {
    Action result = new Action();
    updateNameAndDescription(result);
    updateDiscountInfo(result);
    updateDateInfo(result);
    updateLinks(result);
    return result;
  }

  private void updateLinks(Action standard) {
    if (standard.getGood() == null) {
      standard.setGood(new Good());
      standard.setGoodId(1l);
    }
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

  private void assertWithOutStartDate(Action standard) {
    Action action = new Action();
    Assertions.assertNotEquals(standard, action);
    Assertions.assertNotEquals(action, standard);
    Assertions.assertNotEquals(standard.hashCode(), action.hashCode());
    action.setName(standard.getName());
    action.setCategoryId(standard.getCategoryId());
    action.setGoodId(standard.getGoodId());
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

  private void assertWithOutGoodId(Action standard) {
    Action action = new Action();
    Assertions.assertNotEquals(standard, action);
    Assertions.assertNotEquals(action, standard);
    Assertions.assertNotEquals(standard.hashCode(), action.hashCode());
    action.setName(standard.getName());
    action.setCategoryId(standard.getCategoryId());
    action.setStartDate(standard.getStartDate());
    action.setGoodId(standard.getGoodId());
    Assertions.assertEquals(standard, action);
    action.setGoodId(null);
    Assertions.assertNotEquals(standard, action);
    action.setGoodId(standard.getGoodId());
    standard.setGoodId(null);
    Assertions.assertNotEquals(standard, action);
    action.setGoodId(null);
    Assertions.assertEquals(standard, action);
  }

  private void assertWithOutCategoryId(Action standard) {
    Action action = new Action();
    Assertions.assertNotEquals(standard, action);
    Assertions.assertNotEquals(action, standard);
    Assertions.assertNotEquals(standard.hashCode(), action.hashCode());
    action.setName(standard.getName());
    action.setCategoryId(standard.getCategoryId());
    action.setGoodId(standard.getGoodId());
    action.setStartDate(standard.getStartDate());
    Assertions.assertEquals(standard, action);
    action.setCategoryId(null);
    Assertions.assertNotEquals(standard, action);
    action.setCategoryId(standard.getCategoryId());
    standard.setCategoryId(null);
    Assertions.assertNotEquals(standard, action);
    action.setCategoryId(null);
    Assertions.assertEquals(standard, action);
  }

  private void assertWithOutNameEquals(Action standard) {
    Action action = new Action();
    Assertions.assertNotEquals(standard, action);
    Assertions.assertNotEquals(action, standard);
    Assertions.assertNotEquals(standard.hashCode(), action.hashCode());
    action.setName(null);
    standard.setName(null);
    action.setCategoryId(standard.getCategoryId());
    action.setGoodId(standard.getGoodId());
    action.setStartDate(standard.getStartDate());
    Assertions.assertEquals(standard, action);
  }

  private void assertFullEquals(Action standard) {
    Action action = new Action();
    action.setName(standard.getName());
    action.setCategoryId(standard.getCategoryId());
    action.setGoodId(standard.getGoodId());
    action.setStartDate(standard.getStartDate());
    Assertions.assertEquals(standard, action);
    Assertions.assertEquals(standard.hashCode(), action.hashCode());
  }

  private void assertCategoryActionEquals(Action standard) {
    Action action = DomainTestUtil.buildCategoryAction();
    evalToString(standard, action);
    Assertions.assertEquals(standard, standard);
    Assertions.assertNotEquals(standard, action);
    Assertions.assertNotEquals(action, standard);
    Assertions.assertNotEquals(standard, new Object());
    Assertions.assertNotEquals(standard, null);
    Assertions.assertNotEquals(standard.hashCode(), action.hashCode());
    Assertions.assertEquals(standard.hashCode(), standard.hashCode());
  }

  private void evalToString(Action standard, Action customAction) {
    log.info("action1: " + standard.toString());
    log.info("action2: " + customAction.toString());
  }

}
