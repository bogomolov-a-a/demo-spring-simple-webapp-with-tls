package org.artembogomolova.demo.webapp.test.domain.entity.business;

import java.util.function.Function;
import org.artembogomolova.demo.webapp.domain.business.Good;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Good")
class GoodEntityTest extends AbstractAccessorEntityTest<Good> {

  GoodEntityTest() {
    super(Good.class);
  }

  @Override
  protected Good buildStandardEntity() {
    Good result = new Good();
    updateNameAndDescription(result);
    // updateQuantityAndPrice(result);
    updateImageFilePath(result);
    updateCategory(result);
    //updateProducer(result);
    return result;
  }

  /*private void updateProducer(StockGood result) {
    // if(result.get)
  }*/

  private void updateCategory(Good result) {
    if (result.getCategory() == null) {
      result.setCategory(DomainTestUtil.buildCategory1());
    }
  }

  private void updateImageFilePath(Good result) {
    if (result.getImgFilePath() == null) {
      result.setImgFilePath("image.png");
    }

  }

  /*private void updateQuantityAndPrice(Good result) {
    if (result.getQuantity() == null) {
      result.setQuantity(42.0f);
    }
    if (result.getPrice() == null) {
      result.setPrice(4.2f);
    }
  }*/

  private void updateNameAndDescription(Good result) {
    if (result.getName() == null) {
      result.setName("standard good");
    }
    if (result.getDescription() == null) {
      result.setDescription("standard good description");
    }
  }

  @Override
  protected Good buildDuplicateEntity(Good standardEntity) {
    return new Good(standardEntity);
  }

  @Override
  protected Good buildAnotherEntityForTest() {
    return DomainTestUtil.buildTestGood();
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Good standardEntity, String constraintName, String columnName) {

  }

  @Override
  protected Function<Good, ? extends Good> getMockDescendantClassConstructor() {
    return MockGood::new;
  }

  private static class MockGood extends Good{

    MockGood(Good good) {
    }
  }
}
