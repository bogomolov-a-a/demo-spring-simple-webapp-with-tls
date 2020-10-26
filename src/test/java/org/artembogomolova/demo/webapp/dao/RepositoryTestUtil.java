package org.artembogomolova.demo.webapp.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.model.Order;
import org.artembogomolova.demo.webapp.model.Person;
import org.artembogomolova.demo.webapp.model.PhysicalAddress;
@Slf4j
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

  public static Person buildPerson() {
    Person result = new Person();
    result.setSurname("Surname1");
    result.setName("Name1");
    result.setPatronymic("Patronymic1");
    try {
      result.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse("2010-10-27"));
    } catch (ParseException e) {
      log.warn(e.getMessage());
    }
    result.setPhone("+78*********");
    result.setEstateAddress(RepositoryTestUtil.buildTestAddress());
    return result;
  }
}
