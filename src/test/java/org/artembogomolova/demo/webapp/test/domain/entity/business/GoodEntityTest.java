package org.artembogomolova.demo.webapp.test.domain.entity.business;

import lombok.EqualsAndHashCode;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.business.Category;
import org.artembogomolova.demo.webapp.domain.business.Good;
import org.artembogomolova.demo.webapp.domain.business.Good_;
import org.artembogomolova.demo.webapp.domain.business.Producer;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Good")
class GoodEntityTest extends AbstractAccessorEntityTest<Good> {

  private static final String NAME_VALUE = "test good";
  private static final String DESCRIPTION_VALUE = "test good description";
  private static final String IMG_FILE_PATH_VALUE = "test file path";
  private static final Producer PRODUCER_VALUE = new Producer();
  private static final Category CATEGORY_VALUE = new Category();

  GoodEntityTest() {
    super(Good.class,
        Good::new,
        MockGood::new);
  }

  @Override
  protected Good buildStandardEntity() {
    Good result = new Good();
    result.setName(NAME_VALUE);
    result.setDescription(DESCRIPTION_VALUE);
    result.setImgFilePath(IMG_FILE_PATH_VALUE);
    result.setProducer(PRODUCER_VALUE);
    result.setCategory(CATEGORY_VALUE);
    return result;
  }

  @Override
  protected void containFieldCorrectValuesTest(Good standardEntity) {
    Assertions.assertEquals(NAME_VALUE, standardEntity.getName());
    Assertions.assertEquals(DESCRIPTION_VALUE, standardEntity.getDescription());
    Assertions.assertEquals(IMG_FILE_PATH_VALUE, standardEntity.getImgFilePath());
    Assertions.assertEquals(PRODUCER_VALUE, standardEntity.getProducer());
    Assertions.assertEquals(CATEGORY_VALUE, standardEntity.getCategory());
  }

  @Override
  protected Good buildAnotherEntityForTest() {
    return DomainTestUtil.buildTestGood();
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Good standardEntity, String constraintName, String columnName) {
    if (IdentifiedEntity.BASIC_CONSTRAINT_NAME.equals(constraintName)) {
      withoutBasicConstraint(standardEntity, columnName);
    }
  }

  private void withoutBasicConstraint(Good standardEntity, String columnName) {
    switch (columnName) {
      case Good_.NAME: {
        withoutColumnEqualTest(standardEntity, Good::getName, Good::setName);
        return;
      }
      case Good_.PRODUCER: {
        withoutColumnEqualTest(standardEntity, Good::getProducer, Good::setProducer);
        return;
      }
      case Good_.CATEGORY: {
        withoutColumnEqualTest(standardEntity, Good::getCategory, Good::setCategory);
        return;
      }
      default: {
        return;
      }
    }
  }

  @EqualsAndHashCode(callSuper = false)
  private static class MockGood extends Good {

    MockGood(Good good) {
      super(buildCopyingGood(good));
    }

    private static Good buildCopyingGood(Good good) {
      good.setProducer(null);
      good.setCategory(null);
      return good;
    }
  }
}
