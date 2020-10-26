package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.Order;
import org.artembogomolova.demo.webapp.model.PhysicalAddress;

public class RepositoryTestUtil {

  public static PhysicalAddress buildTestAddress() {
    PhysicalAddress result = new PhysicalAddress();
    result.setPostalCode("523152");
    result.setCountry("Country1");
    result.setState("State1");
    result.setCity("City1");
    result.setDistrict("District1");
    result.setStreet("Street1");
    result.setHouse("1234/42");
    result.setRoom(42);
    result.setSpecificPart("unused");
    return result;
  }

  public static PhysicalAddress buildOrderTestAddress() {
    PhysicalAddress result = new PhysicalAddress();
    result.setPostalCode("523152");
    result.setCountry("Country1");
    result.setState("State1");
    result.setCity("City1");
    result.setDistrict("District1");
    result.setStreet("Street1");
    result.setHouse("1234/42");
    result.setRoom(13);
    result.setSpecificPart("unused");
    return result;
  }

  public static Order buildOrder() {
    Order result = new Order();
    result.setAddress(buildOrderTestAddress());
    result.setDescription("test order description");
    return result;
  }
}
