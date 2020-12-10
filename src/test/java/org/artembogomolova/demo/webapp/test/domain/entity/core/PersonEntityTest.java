package org.artembogomolova.demo.webapp.test.domain.entity.core;

import org.artembogomolova.demo.webapp.domain.core.Person;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Person")
class PersonEntityTest extends AbstractAccessorEntityTest<Person> {

  PersonEntityTest() {
    super(Person.class);
  }

  @Override
  protected void availableConstraint(String constraintName) {

  }

  @Override
  protected Person buildStandardEntityAndAccessorTest() {
    return null;
  }

  @Override
  protected Person buildDuplicateEntity(Person standardEntity) {
    return null;
  }

  @Override
  protected Person buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Person standardEntity, String constraintName, String columnName) {

  }
}
