package org.artembogomolova.demo.webapp.dao;

import java.util.ArrayList;
import java.util.List;
import org.artembogomolova.demo.webapp.dao.repo.IOrderRepository;
import org.artembogomolova.demo.webapp.dao.repo.IPersonRepository;
import org.artembogomolova.demo.webapp.dao.repo.IPhysicalAddressRepository;
import org.artembogomolova.demo.webapp.domain.core.Person;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class PersonRepositoryTest extends AbstractDaoTest<Person> {

  @Autowired
  private IPersonRepository personRepository;
  @Autowired
  private IPhysicalAddressRepository physicalAddressRepository;
  @Autowired
  private IOrderRepository orderRepository;

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
    Person person = RepositoryTestUtil.buildPerson();
    result.add(person);
    return result;
  }

  @Override
  protected void validateAnotherRepositoryEmpty() {
    /*address repository must be empty after successful delete person.*/
    Assertions.assertEquals(0, physicalAddressRepository.count());
    Assertions.assertEquals(0, orderRepository.count());
  }

}
