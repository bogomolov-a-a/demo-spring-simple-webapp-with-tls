package org.artembogomolova.demo.webapp.test.domain.entity.business;

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
}
