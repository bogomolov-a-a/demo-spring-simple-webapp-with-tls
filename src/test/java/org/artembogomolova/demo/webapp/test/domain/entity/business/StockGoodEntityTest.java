package org.artembogomolova.demo.webapp.test.domain.entity.business;

import org.artembogomolova.demo.webapp.domain.business.StockGood;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: StockGood")
class StockGoodEntityTest extends AbstractAccessorEntityTest<StockGood> {

  StockGoodEntityTest() {
    super(StockGood.class,
        StockGood::new,
        MockStockGood::new);
  }


  @Override
  protected StockGood buildStandardEntity() {
    return null;
  }

  @Override
  protected StockGood buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected boolean withoutBasicConstraint(StockGood standardEntity, String columnName) {
    return false;
  }

  private static class MockStockGood extends StockGood {

    MockStockGood(StockGood stockGood) {
    }
  }
}
