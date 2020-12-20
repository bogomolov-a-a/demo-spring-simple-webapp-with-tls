package org.artembogomolova.demo.webapp.test.domain.entity.business;

import lombok.EqualsAndHashCode;
import org.artembogomolova.demo.webapp.domain.business.Action;
import org.artembogomolova.demo.webapp.domain.business.ActionGood;
import org.artembogomolova.demo.webapp.domain.business.ActionGood_;
import org.artembogomolova.demo.webapp.domain.business.Good;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: ActionGood")
class ActionGoodEntityTest extends AbstractAccessorEntityTest<ActionGood> {

  private static final Float SHARE_PRICE_VALUE = 4.2f;
  private static final Float QUANTITY_VALUE = 42.0f;
  private static final Action ACTION_VALUE = new Action();
  private static final Good GOOD_VALUE = new Good();

  ActionGoodEntityTest() {
    super(ActionGood.class,
        ActionGood::new,
        MockActionGood::new);
  }

  @Override
  protected ActionGood buildStandardEntity() {
    ActionGood result = new ActionGood();
    result.setSharePrice(SHARE_PRICE_VALUE);
    result.setQuantity(QUANTITY_VALUE);
    result.setAction(ACTION_VALUE);
    result.setGood(GOOD_VALUE);
    return result;
  }

  @Override
  protected void containFieldCorrectValuesTest(ActionGood standardEntity) {
    Assertions.assertEquals(SHARE_PRICE_VALUE, standardEntity.getSharePrice());
    Assertions.assertEquals(QUANTITY_VALUE, standardEntity.getQuantity());
    Assertions.assertEquals(ACTION_VALUE, standardEntity.getAction());
    Assertions.assertEquals(GOOD_VALUE, standardEntity.getGood());
  }

  @Override
  protected ActionGood buildAnotherEntityForTest() {
    ActionGood result = buildStandardEntity();
    result.setGood(DomainTestUtil.buildTestGood());
    result.setAction(DomainTestUtil.buildCategoryAction());
    return result;
  }

  @Override
  protected boolean withoutBasicConstraint(ActionGood standardEntity, String columnName) {
    switch (columnName) {
      case ActionGood_.ACTION: {
        withoutColumnEqualTest(standardEntity, ActionGood::getAction, ActionGood::setAction);
        return true;
      }
      case ActionGood_.GOOD: {
        withoutColumnEqualTest(standardEntity, ActionGood::getGood, ActionGood::setGood);
        return true;
      }
      default: {
        return false;
      }
    }

  }

  @EqualsAndHashCode(callSuper = false)
  private static class MockActionGood extends ActionGood {

    MockActionGood(ActionGood actionGood) {
      super(buildCopyingActionGood(actionGood));
    }

    private static ActionGood buildCopyingActionGood(ActionGood actionGood) {
      actionGood.setAction(null);
      actionGood.setGood(null);
      return actionGood;
    }
  }
}
