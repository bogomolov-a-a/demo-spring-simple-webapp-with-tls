package org.artembogomolova.demo.webapp.test.domain.entity.core;

import lombok.EqualsAndHashCode;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress_;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: PhysicalAddress")
class PhysicalAddressEntityTest extends AbstractAccessorEntityTest<PhysicalAddress> {

  private static final String POSTAL_CODE_VALUE = "190000";
  private static final String COUNTRY_VALUE = "Russia";
  private static final String STATE_VALUE = "Saint Petersburg";
  private static final String CITY_VALUE = "Saint Petersburg";
  private static final String DISTRICT_VALUE = "District1";
  private static final String STREET_VALUE = "Street1";
  private static final String HOUSE_VALUE = "House1";
  private static final Integer ROOM_VALUE = 10;
  private static final String SPECIFIC_PART_VALUE = "SpecificPart1";

  PhysicalAddressEntityTest() {
    super(PhysicalAddress.class,
        PhysicalAddress::new,
        MockPhysicalAddress::new);
  }

  @Override
  protected PhysicalAddress buildStandardEntity() {
    PhysicalAddress result = new PhysicalAddress();
    result.setPostalCode(POSTAL_CODE_VALUE);
    result.setCountry(COUNTRY_VALUE);
    result.setState(STATE_VALUE);
    result.setCity(CITY_VALUE);
    result.setDistrict(DISTRICT_VALUE);
    result.setStreet(STREET_VALUE);
    result.setHouse(HOUSE_VALUE);
    result.setRoom(ROOM_VALUE);
    result.setSpecificPart(SPECIFIC_PART_VALUE);
    return result;
  }

  @Override
  protected void containFieldCorrectValuesTest(PhysicalAddress standardEntity) {
    Assertions.assertEquals(POSTAL_CODE_VALUE, standardEntity.getPostalCode());
    Assertions.assertEquals(COUNTRY_VALUE, standardEntity.getCountry());
    Assertions.assertEquals(STATE_VALUE, standardEntity.getState());
    Assertions.assertEquals(CITY_VALUE, standardEntity.getCity());
    Assertions.assertEquals(DISTRICT_VALUE, standardEntity.getDistrict());
    Assertions.assertEquals(STREET_VALUE, standardEntity.getStreet());
    Assertions.assertEquals(HOUSE_VALUE, standardEntity.getHouse());
    Assertions.assertEquals(ROOM_VALUE, standardEntity.getRoom());
    Assertions.assertEquals(SPECIFIC_PART_VALUE, standardEntity.getSpecificPart());
  }


  @Override
  protected PhysicalAddress buildAnotherEntityForTest() {
    return DomainTestUtil.buildTestAddress();
  }

  @Override
  protected boolean withoutBasicConstraint(PhysicalAddress standardEntity, String columnName) {
    if (isCountryPartColumnName(columnName)) {
      return withoutCountryPartEqualTest(standardEntity, columnName);
    }
    if (isCityLevelPartColumnName(columnName)) {
      return withoutCityPartEqualTest(standardEntity, columnName);
    }
    if (isHouseLevelPartColumnName(columnName)) {
      return withoutHousePartEqualTest(standardEntity, columnName);
    }
    return false;
  }


  private boolean withoutHousePartEqualTest(PhysicalAddress standardEntity, String columnName) {
    switch (columnName) {
      case PhysicalAddress_.HOUSE: {
        withoutColumnEqualTest(standardEntity, PhysicalAddress::getHouse, PhysicalAddress::setHouse);
        return true;
      }
      case PhysicalAddress_.ROOM: {
        withoutColumnEqualTest(standardEntity, PhysicalAddress::getRoom, PhysicalAddress::setRoom);
        return true;
      }
      case PhysicalAddress_.SPECIFIC_PART: {
        withoutColumnEqualTest(standardEntity, PhysicalAddress::getSpecificPart, PhysicalAddress::setSpecificPart);
        return true;
      }
      default: {
        return false;
      }
    }
  }

  private boolean isHouseLevelPartColumnName(String columnName) {
    return PhysicalAddress_.HOUSE.equals(columnName) || PhysicalAddress_.ROOM.equals(columnName) ||
        PhysicalAddress_.SPECIFIC_PART.equals(columnName);
  }

  private boolean withoutCityPartEqualTest(PhysicalAddress standardEntity, String columnName) {
    switch (columnName) {
      case PhysicalAddress_.CITY: {
        withoutColumnEqualTest(standardEntity, PhysicalAddress::getCity, PhysicalAddress::setCity);
        return true;
      }
      case PhysicalAddress_.DISTRICT: {
        withoutColumnEqualTest(standardEntity, PhysicalAddress::getDistrict, PhysicalAddress::setDistrict);
        return true;
      }
      case PhysicalAddress_.STREET: {
        withoutColumnEqualTest(standardEntity, PhysicalAddress::getStreet, PhysicalAddress::setStreet);
        return true;
      }
      default: {
        return false;
      }
    }
  }

  private boolean isCityLevelPartColumnName(String columnName) {
    return PhysicalAddress_.CITY.equals(columnName) || PhysicalAddress_.DISTRICT.equals(columnName) ||
        PhysicalAddress_.STREET.equals(columnName);
  }

  private boolean withoutCountryPartEqualTest(PhysicalAddress standardEntity, String columnName) {
    switch (columnName) {
      case PhysicalAddress_.POSTAL_CODE: {
        withoutColumnEqualTest(standardEntity, PhysicalAddress::getPostalCode, PhysicalAddress::setPostalCode);
        return true;
      }
      case PhysicalAddress_.COUNTRY: {
        withoutColumnEqualTest(standardEntity, PhysicalAddress::getCountry, PhysicalAddress::setCountry);
        return true;
      }
      case PhysicalAddress_.STATE: {
        withoutColumnEqualTest(standardEntity, PhysicalAddress::getState, PhysicalAddress::setState);
        return true;
      }
      default:
        return false;
    }
  }

  private boolean isCountryPartColumnName(String columnName) {
    return PhysicalAddress_.POSTAL_CODE.equals(columnName) || PhysicalAddress_.COUNTRY.equals(columnName) ||
        PhysicalAddress_.STATE.equals(columnName);
  }

  @EqualsAndHashCode(callSuper = false)
  private static class MockPhysicalAddress extends PhysicalAddress {

    public MockPhysicalAddress(PhysicalAddress physicalAddress) {
      super(physicalAddress);
    }
  }
}
