package org.artembogomolova.demo.webapp.test.domain.entity.core;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import lombok.EqualsAndHashCode;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.core.Person;
import org.artembogomolova.demo.webapp.domain.core.Person_;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Person")
class PersonEntityTest extends AbstractAccessorEntityTest<Person> {

  private static final String NAME_VALUE = "TestName";
  private static final String SURNAME_VALUE = "TestSurname";
  private static final String PATRONYMIC_VALUE = "TestPatronymic";
  private static final Date BIRTH_DATE_VALUE = Calendar.getInstance().getTime();
  private static final String PHONE_VALUE = "+7-812-000-00-00";
  private static final String EMAIL_VALUE = "test_email@gmail.com";
  private static final PhysicalAddress PHYSICAL_ADDRESS_VALUE = DomainTestUtil.buildTestAddress();

  PersonEntityTest() {
    super(Person.class);
  }

  @Override
  protected Person buildStandardEntity() {
    Person result = new Person();
    result.setName(NAME_VALUE);
    result.setSurname(SURNAME_VALUE);
    result.setPatronymic(PATRONYMIC_VALUE);
    result.setBirthDate(BIRTH_DATE_VALUE);
    result.setPhone(PHONE_VALUE);
    result.setEmail(EMAIL_VALUE);
    result.setEstateAddress(PHYSICAL_ADDRESS_VALUE);
    return result;
  }

  @Override
  protected void containFieldCorrectValuesTest(Person standardEntity) {
    Assertions.assertEquals(NAME_VALUE, standardEntity.getName());
    Assertions.assertEquals(SURNAME_VALUE, standardEntity.getSurname());
    Assertions.assertEquals(PATRONYMIC_VALUE, standardEntity.getPatronymic());
    Assertions.assertEquals(BIRTH_DATE_VALUE, standardEntity.getBirthDate());
    Assertions.assertEquals(PHONE_VALUE, standardEntity.getPhone());
    Assertions.assertEquals(EMAIL_VALUE, standardEntity.getEmail());
    Assertions.assertEquals(PHYSICAL_ADDRESS_VALUE, standardEntity.getEstateAddress());
    Assertions.assertEquals(null, standardEntity.getUser());
    Assertions.assertTrue(standardEntity.getOrders().isEmpty());
  }

  @Override
  protected List<String> getAvailableConstraintNames() {
    return List.of(IdentifiedEntity.BASIC_CONSTRAINT_NAME, Person.PHONE_CONSTRAINT_NAME,
        Person.EMAIL_CONSTRAINT_NAME);
  }

  @Override
  protected Person buildDuplicateEntity(Person standardEntity) {
    return new Person(standardEntity);
  }

  @Override
  protected Person buildAnotherEntityForTest() {
    return DomainTestUtil.buildPerson();
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Person standardEntity, String constraintName, String columnName) {
    switch (constraintName) {
      case IdentifiedEntity.BASIC_CONSTRAINT_NAME: {
        basicConstraintTest(standardEntity, columnName);
        return;
      }
      case Person.PHONE_CONSTRAINT_NAME: {
        withoutColumnEqualTest(standardEntity, Person::getPhone, Person::setPhone);
        return;
      }
      case Person.EMAIL_CONSTRAINT_NAME: {
        withoutColumnEqualTest(standardEntity, Person::getEmail, Person::setEmail);
        return;
      }
      default:
        return;
    }
  }

  private void basicConstraintTest(Person standardEntity, String columnName) {
    if (Person_.BIRTH_DATE.equals(columnName)) {
      withoutColumnEqualTest(standardEntity, Person::getBirthDate, Person::setBirthDate);
      return;
    }
    personNamesBlockTest(standardEntity, columnName);
  }

  private void personNamesBlockTest(Person standardEntity, String columnName) {
    switch (columnName) {
      case Person_.NAME: {
        withoutColumnEqualTest(standardEntity, Person::getName, Person::setName);
        return;
      }
      case Person_.PATRONYMIC: {
        withoutColumnEqualTest(standardEntity, Person::getPatronymic, Person::setPatronymic);
        return;
      }
      case Person_.SURNAME: {
        withoutColumnEqualTest(standardEntity, Person::getSurname, Person::setSurname);
        return;
      }
      default:
        return;
    }
  }

  @Override
  protected Function<Person, ? extends Person> getMockDescendantClassConstructor() {
    return MockPerson::new;
  }

  @EqualsAndHashCode(callSuper = false)
  private static class MockPerson extends Person {

    MockPerson(Person person) {
      super(person);
    }
  }
}
