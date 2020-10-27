package org.artembogomolova.demo.webapp.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.artembogomolova.demo.webapp.model.Good;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class GoodRepositoryTest extends AbstractDaoTest<Good>{

  @Autowired
  private IGoodRepository goodRepository;
  @Autowired
  private IProducerRepository producerRepository;
  @Autowired
  private IPhysicalAddressRepository physicalAddressRepository;
  @Autowired
  private ICategoryRepository categoryRepository;

  @Override
  protected Collection<Good> updateEntities(Collection<Good> savedCollection) {
    ((List<Good>)savedCollection).get(0).setPrice(42.3f);
    return savedCollection;
  }

  @Override
  protected CrudRepository<Good, Long> getCrudRepository() {
    return goodRepository;
  }

  @Override
  protected Collection<Good> generateEntities() {
    List<Good> result= new ArrayList<>();
    result.add(RepositoryTestUtil.buildTestGood());
    return result;
  }

  @Override
  protected void validateAnotherRepositoryEmpty() {
    Assertions.assertEquals(producerRepository.count(),0);
    Assertions.assertEquals(physicalAddressRepository.count(),0);
    categoryRepository.deleteAll();
    Assertions.assertEquals(categoryRepository.count(),0);
  }
}
