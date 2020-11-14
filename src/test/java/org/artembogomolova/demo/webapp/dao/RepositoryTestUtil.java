package org.artembogomolova.demo.webapp.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.domain.business.Action;
import org.artembogomolova.demo.webapp.domain.business.Category;
import org.artembogomolova.demo.webapp.domain.business.Good;
import org.artembogomolova.demo.webapp.domain.business.Order;
import org.artembogomolova.demo.webapp.domain.business.OrderPosition;
import org.artembogomolova.demo.webapp.domain.business.Producer;
import org.artembogomolova.demo.webapp.domain.business.Ticket;
import org.artembogomolova.demo.webapp.domain.core.Person;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;

@Slf4j
public class RepositoryTestUtil {

  public static Category buildCategory1() {
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
    result.setDescription("test order description");
    Person person = buildPerson();
    result.setPerson(person);
    person.getOrders().add(result);
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
    Producer result = new Producer();
    result.setName("test producer");
    result.setAddress(buildProducerTestAddress());
    return result;
  }

  public static Good buildTestGood() {
    Good result = new Good();
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
    Category result = new Category();
    result.setName("Category2");
    return result;
  }

  public static Action buildGoodAction() {
    Action result = new Action();
    result.setDescription("test action 1 description");
    result.setStartDate(Calendar.getInstance().getTime());
    result.setEndDate(Calendar.getInstance().getTime());
    result.setName("test action 1");
    Good testGood = buildTestGood();
    result.setGood(testGood);
    testGood.getActions().add(result);
    result.setDiscountFixed(500.0f);
    return result;
  }

  public static Action buildCategoryAction() {
    Action result = new Action();
    result.setDescription("test action 1 description");
    result.setStartDate(Calendar.getInstance().getTime());
    result.setEndDate(Calendar.getInstance().getTime());
    result.setName("test action 1");
    Category category1 = buildCategory1();
    result.setCategory(category1);
    category1.getActions().add(result);
    result.setDiscountPercent(50.0f);
    return result;
  }

  public static Order buildCreatedOrder() {
    Order result = buildOrder();
    OrderPosition orderPosition = buildOrderPosition();
    result.getOrderPositions().add(orderPosition);
    orderPosition.setOrder(result);
    return result;
  }

  private static OrderPosition buildOrderPosition() {
    OrderPosition result = new OrderPosition();
    Good testGood = buildTestGood();
    result.setGood(testGood);
    testGood.getOrderPositions().add(result);
    result.setQuantity(6f);
    return result;
  }


  public static Order buildPayedOrder() {
    Order result = buildCreatedOrder();
    result.setPayed(true);
    Ticket ticket = new Ticket();
    result.getTickets().add(ticket);
    ticket.setOrder(result);
    ticket.setSumm(422.0f * 6.0f);
    return result;
  }

  public static Order buildDeliveredOrder() {
    Order result = buildPayedOrder();
    result.setDeliverDate(Calendar.getInstance().getTime());
    return result;
  }
}
