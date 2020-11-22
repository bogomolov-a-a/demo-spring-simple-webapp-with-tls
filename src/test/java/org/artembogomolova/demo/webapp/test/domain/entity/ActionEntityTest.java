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
    Action action1 = new Action();
    updateNameAndDescription(action1);
    updateDiscountInfo(action1);
    updateDateInfo(action1);
    updateLinks(action1);
    Action action2 = DomainTestUtil.buildCategoryAction();
    log.info("action1: " + action1.toString());
    log.info("action2: " + action2.toString());
    Assertions.assertEquals(action1, action1);
    Assertions.assertNotEquals(action1, action2);
    Assertions.assertNotEquals(action2, action1);
    Assertions.assertNotEquals(action1, new Object());
    Assertions.assertNotEquals(action1, null);
    Assertions.assertNotEquals(action1.hashCode(), action2.hashCode());
    Assertions.assertEquals(action1.hashCode(), action1.hashCode());
    Action action3 = new Action();
    action3.setName(action1.getName());
    Assertions.assertEquals(action1, action3);
    Assertions.assertEquals(action1.hashCode(), action3.hashCode());
    Action action4 = new Action();
    Assertions.assertNotEquals(action1, action4);
    Assertions.assertNotEquals(action4, action1);
    Assertions.assertNotEquals(action1.hashCode(), action4.hashCode());
    action4.setName(null);
    action1.setName(null);
    Assertions.assertEquals(action1, action4);
  }

  private void updateLinks(Action action1) {
    if (action1.getGood() == null) {
      action1.setGood(new Good());
    }
    if (action1.getCategory() == null) {
      action1.setCategory(new Category());
    }
  }

  private void updateDateInfo(Action action1) {
    if (action1.getEndDate() == null) {
      action1.setEndDate(Calendar.getInstance().getTime());
      log.info("now set endDate is " + action1.getEndDate().toString());
    }
    if (action1.getStartDate() == null) {
      action1.setStartDate(Calendar.getInstance().getTime());
      log.info("now set startDate is " + action1.getStartDate().toString());
    }
  }

  private void updateDiscountInfo(Action action1) {
    if (action1.getDiscountFixed() == null) {
      action1.setDiscountFixed(420.0f);
    }
    if (action1.getDiscountPercent() == null) {
      action1.setDiscountFixed(42.0f);
    }
  }

  private void updateNameAndDescription(Action action1) {
    if (action1.getName() == null) {
      action1.setName("dummy name");
    }
    if (action1.getDescription() == null) {
      action1.setDescription("dummy description");
    }
  }
}
