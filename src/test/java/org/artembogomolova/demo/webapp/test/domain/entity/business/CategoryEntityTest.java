package org.artembogomolova.demo.webapp.test.domain.entity.business;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.domain.business.Action;
import org.artembogomolova.demo.webapp.domain.business.Category;
import org.artembogomolova.demo.webapp.domain.business.Category_;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@Slf4j
@DisplayName("Entity test: Category")
class CategoryEntityTest extends AbstractAccessorEntityTest<Category> {

  private static final String NAME_VALUE = "test category";
  private static final Long PARENT_CATEGORY_ID_VALUE = 1L;
  private static final Action ACTION_VALUE = new Action();

  CategoryEntityTest() {
    super(Category.class,
        Category::new,
        MockCategory::new);
  }

  @Override
  protected Category buildStandardEntity() {
    Category result = new Category();
    result.setName(NAME_VALUE);
    result.setParentCategoryId(PARENT_CATEGORY_ID_VALUE);
    result.setAction(ACTION_VALUE);
    return result;
  }

  @Override
  protected void containFieldCorrectValuesTest(Category standardEntity) {
    Assertions.assertEquals(NAME_VALUE, standardEntity.getName());
    Assertions.assertEquals(PARENT_CATEGORY_ID_VALUE, standardEntity.getParentCategoryId());
    Assertions.assertEquals(ACTION_VALUE, standardEntity.getAction());
    Assertions.assertTrue(standardEntity.getGoods().isEmpty());
  }

  @Override
  protected Category buildAnotherEntityForTest() {
    return DomainTestUtil.buildCategory1();
  }

  @Override
  protected boolean withoutBasicConstraint(Category standardEntity, String columnName) {
    switch (columnName) {
      case Category_.NAME: {
        withoutColumnEqualTest(standardEntity, Category::getName, Category::setName);
        return true;
      }
      case Category_.PARENT_CATEGORY_ID: {
        withoutColumnEqualTest(standardEntity, Category::getParentCategoryId, Category::setParentCategoryId);
        return true;
      }
      default: {
        return false;
      }
    }
  }


  @EqualsAndHashCode(callSuper = false)
  private static class MockCategory extends Category {

    MockCategory(Category category) {
      super(buildCopyingCategory(category));
    }

    private static Category buildCopyingCategory(Category category) {
      category.setAction(null);
      return category;
    }
  }
}
