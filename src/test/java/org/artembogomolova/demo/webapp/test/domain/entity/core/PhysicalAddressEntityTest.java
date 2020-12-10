package org.artembogomolova.demo.webapp.test.domain.entity.core;

import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;

class PhysicalAddressEntityTest extends AbstractAccessorEntityTest<PhysicalAddress> {

  PhysicalAddressEntityTest() {
    super(PhysicalAddress.class);
  }

  @Override
  protected void availableConstraint(String constraintName) {

  }

  @Override
  protected PhysicalAddress buildStandardEntityAndAccessorTest() {
    return null;
  }

  @Override
  protected PhysicalAddress buildDuplicateEntity(PhysicalAddress standardEntity) {
    return null;
  }

  @Override
  protected PhysicalAddress buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(PhysicalAddress standardEntity, String constraintName, String columnName) {

  }
}
