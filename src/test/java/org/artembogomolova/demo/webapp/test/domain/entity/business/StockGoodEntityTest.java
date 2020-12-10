package org.artembogomolova.demo.webapp.test.domain.entity.business;

import org.artembogomolova.demo.webapp.domain.business.StockGood;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;

class StockGoodEntityTest extends AbstractAccessorEntityTest<StockGood> {

  StockGoodEntityTest() {
    super(StockGood.class);
  }

  @Override
  protected void availableConstraint(String constraintName) {

  }

  @Override
  protected StockGood buildStandardEntityAndAccessorTest() {
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
