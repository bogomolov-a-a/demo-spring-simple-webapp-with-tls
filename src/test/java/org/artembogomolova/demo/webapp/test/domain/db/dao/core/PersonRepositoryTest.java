package org.artembogomolova.demo.webapp.test.domain.db.dao.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.dao.repo.business.IOrderRepository;
import org.artembogomolova.demo.webapp.dao.repo.core.IPersonRepository;
import org.artembogomolova.demo.webapp.dao.repo.core.IPhysicalAddressRepository;
import org.artembogomolova.demo.webapp.domain.core.Person;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Dao test: Person")
class PersonRepositoryTest extends AbstractDaoTest<Person> {

  @Autowired
  private IPersonRepository personRepository;
  @Autowired
  private IPhysicalAddressRepository physicalAddressRepository;
  @Autowired
  private IOrderRepository orderRepository;

  PersonRepositoryTest() {
    super(Person.class);
  }

  @Override
  protected List<Person> updateEntities(List<Person> savedCollection) {
    Person person = savedCollection.get(0);
    person.setPhone("+781********");
    return savedCollection;
  }

  @Override
  protected CrudRepository<Person, Long> getCrudRepository() {
    return personRepository;
  }

  @Override
  protected List<Person> generateEntities() {
    List<Person> result = new ArrayList<>();
    Person person = DomainTestUtil.buildPerson();
    result.add(person);
    return result;
  }

  @Override
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected Person doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint,
      Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected Person doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected void validateAnotherRepositoryEmpty() {
    /*address repository must be empty after successful delete person.*/
    Assertions.assertEquals(0, physicalAddressRepository.count());
    Assertions.assertEquals(0, orderRepository.count());
  }

}
