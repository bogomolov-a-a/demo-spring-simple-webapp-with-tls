package org.artembogomolova.demo.webapp.test.domain.entity.business;

import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.domain.business.Category;
import org.artembogomolova.demo.webapp.domain.business.Category_;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@Slf4j
@DisplayName("Entity test: Category")
class CategoryEntityTest extends AbstractAccessorEntityTest<Category> {

  CategoryEntityTest() {
    super(Category.class,
        Category::new,
        MockCategory::new);
  }

  @Override
  protected Category buildStandardEntity() {
    Category result = new Category();
    updateSimpleField(result);
    result.getGoods().add(DomainTestUtil.buildTestGood());
    return result;
  }


  private void updateSimpleField(Category result) {
    if (result.getName() == null) {
      result.setName("standard category");
    }
    if (result.getParentCategoryId() == null) {
      result.setParentCategoryId(1L);
    }
  }

  @Override
  protected Category buildAnotherEntityForTest() {
    return DomainTestUtil.buildCategory1();
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Category standardEntity, String constraintName, String columnName) {
    switch (columnName) {
      case Category_.NAME: {
        assertWithoutNameEquals(standardEntity);
        return;
      }
      case Category_.PARENT_CATEGORY_ID: {
        assertWithoutParentCategoryIdEquals(standardEntity);
        return;
      }
    }
  }

  private void assertWithoutNameEquals(Category standard) {
    Category category = new Category();
    category.setParentCategoryId(standard.getParentCategoryId());
    category.setName(standard.getName());
    Assertions.assertEquals(standard, category);
    category.setName(null);
    Assertions.assertNotEquals(standard, category);
    category.setName(standard.getName());
    standard.setName(null);
    Assertions.assertNotEquals(standard, category);
    category.setName(null);
    Assertions.assertEquals(standard, category);
    Assertions.assertEquals(standard.hashCode(), category.hashCode());
  }

  private void assertWithoutParentCategoryIdEquals(Category standard) {
    Category category = new Category();
    category.setName(standard.getName());
    category.setParentCategoryId(standard.getParentCategoryId());
    Assertions.assertEquals(standard, category);
    category.setParentCategoryId(null);
    Assertions.assertNotEquals(standard, category);
    category.setParentCategoryId(standard.getParentCategoryId());
    standard.setParentCategoryId(null);
    Assertions.assertNotEquals(standard, category);
    category.setParentCategoryId(null);
    Assertions.assertEquals(standard, category);
    Assertions.assertEquals(standard.hashCode(), category.hashCode());
  }

  private static class MockCategory extends Category {

    MockCategory(Category category) {
    }
  }
}
