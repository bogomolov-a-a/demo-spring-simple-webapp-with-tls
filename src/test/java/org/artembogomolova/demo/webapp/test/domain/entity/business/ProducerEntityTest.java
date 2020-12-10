package org.artembogomolova.demo.webapp.test.domain.entity.business;

import org.artembogomolova.demo.webapp.domain.business.Producer;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Producer")
class ProducerEntityTest extends AbstractAccessorEntityTest<Producer> {

  ProducerEntityTest() {
    super(Producer.class);
  }

  @Override
  protected void availableConstraint(String constraintName) {

  }

  @Override
  protected Producer buildStandardEntityAndAccessorTest() {
    return null;
  }

  @Override
  protected Producer buildDuplicateEntity(Producer standardEntity) {
    return null;
  }

  @Override
  protected Producer buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Producer standardEntity, String constraintName, String columnName) {

  }
}
