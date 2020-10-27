package org.artembogomolova.demo.webapp.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.artembogomolova.demo.webapp.model.Person;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class PersonRepositoryTest extends AbstractDaoTest<Person>{

  @Autowired
  private IPersonRepository personRepository;
  @Autowired
  private IPhysicalAddressRepository physicalAddressRepository;
  @Autowired
  private IOrderRepository orderRepository;
  @Override
  protected Collection<Person> updateEntities(Collection<Person> savedCollection) {
    Person person = ((List<Person>) savedCollection).get(0);
    person.setPhone("+781********");
    return savedCollection;
  }

  @Override
  protected CrudRepository<Person, Long> getCrudRepository() {
    return personRepository;
  }

  @Override
  protected Collection<Person> generateEntities() {
    Collection<Person> result=new ArrayList<>();
    Person person = RepositoryTestUtil.buildPerson();
    result.add(person);
    return result;
  }

  @Override
  protected void validateAnotherRepositoryEmpty() {
    /*address repository must be empty after successful delete person.*/
    Assertions.assertEquals(physicalAddressRepository.count(),0);
    Assertions.assertEquals(orderRepository.count(),0);
  }

}
