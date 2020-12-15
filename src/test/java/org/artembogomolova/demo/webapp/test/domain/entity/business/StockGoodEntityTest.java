package org.artembogomolova.demo.webapp.test.domain.entity.business;

import java.util.function.Function;
import org.artembogomolova.demo.webapp.domain.business.StockGood;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: StockGood")
class StockGoodEntityTest extends AbstractAccessorEntityTest<StockGood> {

  StockGoodEntityTest() {
    super(StockGood.class);
  }


  @Override
  protected StockGood buildStandardEntity() {
    return null;
  }

  @Override
  protected StockGood buildDuplicateEntity(StockGood standardEntity) {
    return null;
  }

  @Override
  protected StockGood buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(StockGood standardEntity, String constraintName, String columnName) {

  }

  @Override
  protected Function<StockGood, ? extends StockGood> getMockDescendantClassConstructor() {
    return MockStockGood::new;
  }

  private static class MockStockGood extends StockGood {

    MockStockGood(StockGood stockGood) {
    }
  }
}
