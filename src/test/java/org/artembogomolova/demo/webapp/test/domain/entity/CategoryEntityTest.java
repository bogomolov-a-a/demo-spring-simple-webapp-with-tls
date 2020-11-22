package org.artembogomolova.demo.webapp.test.domain.entity;

import org.artembogomolova.demo.webapp.domain.business.Category;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryEntityTest {

  @Test
  void testCategoryAccessors() {
    Category category1 = DomainTestUtil.buildCategory1();
    Assertions.assertEquals(category1, category1);
    Category category2 = DomainTestUtil.buildCategory2();
    Assertions.assertNotEquals(category1, category2);
    Assertions.assertNotEquals(category2, category1);
    Assertions.assertNotEquals(category1, new Object());
    Assertions.assertNotEquals(category1, null);
    Assertions.assertNotEquals(category1.hashCode(), category2.hashCode());
    Assertions.assertEquals(category1.hashCode(), category1.hashCode());
    Category category3 = new Category();
    category3.setName(category1.getName());
    Assertions.assertEquals(category1, category3);
    Assertions.assertEquals(category1.hashCode(), category3.hashCode());
    Category category4 = new Category();
    Assertions.assertNotEquals(category1, category4);
    Assertions.assertNotEquals(category4, category1);
    Assertions.assertNotEquals(category1.hashCode(), category4.hashCode());
    category4.setName(null);
    category1.setName(null);
    Assertions.assertEquals(category1, category4);
    Category category5 = new Category();
    category5.setParentCategoryId(1L);
    Assertions.assertNotEquals(category1, category5);
    Assertions.assertNotEquals(category5, category1);
    Assertions.assertNotEquals(category5, category4);
    Assertions.assertNotEquals(category4, category5);
    Assertions.assertNotEquals(category5.hashCode(), category4.hashCode());
    Assertions.assertNotEquals(category1.hashCode(), category5.hashCode());
  }
}
