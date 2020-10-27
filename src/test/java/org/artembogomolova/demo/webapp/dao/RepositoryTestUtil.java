package org.artembogomolova.demo.webapp.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.model.Category;
import org.artembogomolova.demo.webapp.model.Good;
import org.artembogomolova.demo.webapp.model.Order;
import org.artembogomolova.demo.webapp.model.Person;
import org.artembogomolova.demo.webapp.model.PhysicalAddress;
import org.artembogomolova.demo.webapp.model.Producer;

@Slf4j
public class RepositoryTestUtil {

  public static Category buildCategory1()
  {
    Category result = new Category();
    result.setName("Category1");
    return result;
  }
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
  public static PhysicalAddress buildProducerTestAddress() {
    PhysicalAddress result = new PhysicalAddress();
    result.setPostalCode("523152");
    result.setCountry("Country1");
    result.setState("State1");
    result.setCity("City1");
    result.setStreet("Street32");
    result.setHouse("1234/42");
    result.setSpecificPart("test-producer-part");
    return result;
  }
  public static PhysicalAddress buildProducerNewTestAddress() {
    PhysicalAddress result = new PhysicalAddress();
    result.setPostalCode("523152");
    result.setCountry("Country1");
    result.setState("State1");
    result.setCity("City1");
    result.setStreet("Street32");
    result.setHouse("42");
    result.setSpecificPart("test-producer-part-2");
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

  public static Producer buildProducer() {
    Producer result=new Producer();
    result.setName("test producer");
    result.setAddress(buildProducerTestAddress());
    return result;
  }

  public static Good buildTestGood() {
    Good result=new Good();
    Category category = buildCategory1();
    result.setCategory(category);
    category.getGoods().add(result);
    result.setDescription("test good 1 description");
    result.setName("test good 1");
    result.setQuantity(2.0f);
    Producer producer = buildProducer();
    result.setProducer(producer);
    producer.getGoods().add(result);
    result.setPrice(410.0f);
    result.setImgFilePath("image.jpg");
    return result;
  }

  public static Category buildCategory2() {
    Category result=new Category();
    result.setName("Category2");
    return result;
  }
}
