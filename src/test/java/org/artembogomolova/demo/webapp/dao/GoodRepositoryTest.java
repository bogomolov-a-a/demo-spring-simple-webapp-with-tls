package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.Good;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public class GoodRepositoryTest extends AbstractDaoTest<Good> {

  @Autowired
  private IGoodRepository goodRepository;
  @Autowired
  private IProducerRepository producerRepository;
  @Autowired
  private IPhysicalAddressRepository physicalAddressRepository;
  @Autowired
  private ICategoryRepository categoryRepository;

  @Override
  protected List<Good> updateEntities(List<Good> savedCollection) {
    savedCollection.get(0)
        .setPrice(42.3f);
    return savedCollection;
  }

  @Override
  protected CrudRepository<Good, Long> getCrudRepository() {
    return goodRepository;
  }

  @Override
  protected List<Good> generateEntities() {
    List<Good> result = new ArrayList<>();
    result.add(RepositoryTestUtil.buildTestGood());
    return result;
  }

  @Override
  protected void validateAnotherRepositoryEmpty() {
    Assertions.assertEquals(0, producerRepository.count());
    Assertions.assertEquals(0, physicalAddressRepository.count());
    categoryRepository.deleteAll();
    Assertions.assertEquals(0, categoryRepository.count());
  }
}
