package org.artembogomolova.demo.webapp.test.domain.entity.business;

import org.artembogomolova.demo.webapp.domain.business.Category;
import org.artembogomolova.demo.webapp.domain.business.Good;
import org.artembogomolova.demo.webapp.domain.business.Producer;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoodEntityTest {

  @Test
  void testCategoryAccessors() {
    Good good1 = DomainTestUtil.buildTestGood();
    Assertions.assertEquals(good1, good1);
    Good good2 = DomainTestUtil.buildTestGood();
    good2.setName("test good 2");
    Assertions.assertNotEquals(good1, good2);
    Assertions.assertNotEquals(good2, good1);
    Assertions.assertNotEquals(good1, new Object());
    Assertions.assertNotEquals(good1, null);
    Assertions.assertNotEquals(good1.hashCode(), good2.hashCode());
    Assertions.assertEquals(good1.hashCode(), good1.hashCode());
    Good good3 = new Good();
    good3.setName(good1.getName());
    good3.setProducer(new Producer());
    good3.setCategory(new Category());
    Assertions.assertEquals(good1, good3);
    Assertions.assertEquals(good1.hashCode(), good3.hashCode());
/*    Good good4 = new Good();

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
    Assertions.assertNotEquals(category1.hashCode(), category5.hashCode());*/
  }
}
