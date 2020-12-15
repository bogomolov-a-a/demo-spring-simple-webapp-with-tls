package org.artembogomolova.demo.webapp.test.domain.entity.business;

import java.util.function.Function;
import org.artembogomolova.demo.webapp.domain.business.ActionGood;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: ActionGood")
class ActionGoodEntityTest extends AbstractAccessorEntityTest<ActionGood> {

  ActionGoodEntityTest() {
    super(ActionGood.class);
  }


  @Override
  protected ActionGood buildStandardEntity() {
    return null;
  }

  @Override
  protected ActionGood buildDuplicateEntity(ActionGood standardEntity) {
    return null;
  }

  @Override
  protected ActionGood buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(ActionGood standardEntity, String constraintName, String columnName) {

  }

  @Override
  protected Function<ActionGood, ? extends ActionGood> getMockDescendantClassConstructor() {
    return MockActionGood::new;
  }

  private static class MockActionGood extends ActionGood {

    MockActionGood(ActionGood actionGood) {
    }
  }
}
