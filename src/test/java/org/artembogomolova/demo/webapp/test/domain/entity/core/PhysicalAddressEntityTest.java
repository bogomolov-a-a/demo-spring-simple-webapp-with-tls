package org.artembogomolova.demo.webapp.test.domain.entity.core;

import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress_;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: PhysicalAddress")
class PhysicalAddressEntityTest extends AbstractAccessorEntityTest<PhysicalAddress> {

  PhysicalAddressEntityTest() {
    super(PhysicalAddress.class);
  }

  @Override
  protected PhysicalAddress buildStandardEntity() {
    PhysicalAddress result = new PhysicalAddress();
    updateCountryLevelInfo(result);
    updateCityLevelInfo(result);
    updateHouseLevelInfo(result);
    return result;
  }

  private void updateHouseLevelInfo(PhysicalAddress result) {
    if (result.getHouse() == null) {
      result.setHouse("42");
    }
    if (result.getRoom() == null) {
      result.setRoom(420);
    }
    if (result.getSpecificPart() == null) {
      result.setSpecificPart("specific part 1");
    }

  }

  private void updateCityLevelInfo(PhysicalAddress result) {
    if (result.getCity() == null) {
      result.setCity("Saint Petersburg");
    }
    if (result.getDistrict() == null) {
      result.setDistrict("District1");
    }
    if (result.getStreet() == null) {
      result.setStreet("Street1");
    }
  }

  private void updateCountryLevelInfo(PhysicalAddress result) {
    if (result.getPostalCode() == null) {
      result.setPostalCode("190000");
    }
    if (result.getCountry() == null) {
      result.setCountry("Russia");
    }
    if (result.getState() == null) {
      result.setState("Saint Petersburg");
    }
  }

  @Override
  protected PhysicalAddress buildDuplicateEntity(PhysicalAddress standardEntity) {
    return new PhysicalAddress(standardEntity);
  }

  @Override
  protected PhysicalAddress buildAnotherEntityForTest() {
    return DomainTestUtil.buildTestAddress();
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(PhysicalAddress standardEntity, String constraintName, String columnName) {
    switch (columnName) {
      case PhysicalAddress_.POSTAL_CODE:
      case PhysicalAddress_.COUNTRY:
      case PhysicalAddress_.STATE:
      case PhysicalAddress_.CITY:
      case PhysicalAddress_.DISTRICT:
      case PhysicalAddress_.STREET:
      case PhysicalAddress_.HOUSE:
      case PhysicalAddress_.ROOM:
      case PhysicalAddress_.SPECIFIC_PART:
        break;
    }
  }
}
