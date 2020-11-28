package org.artembogomolova.demo.webapp.test.domain.dao.repo;

import java.util.ArrayList;
import java.util.List;
import org.artembogomolova.demo.webapp.dao.repo.ICategoryRepository;
import org.artembogomolova.demo.webapp.dao.repo.IGoodRepository;
import org.artembogomolova.demo.webapp.dao.repo.IPhysicalAddressRepository;
import org.artembogomolova.demo.webapp.dao.repo.IProducerRepository;
import org.artembogomolova.demo.webapp.domain.business.Good;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class GoodRepositoryTest extends AbstractDaoTest<Good> {

  @Autowired
  private IGoodRepository goodRepository;
  @Autowired
  private IProducerRepository producerRepository;
  @Autowired
  private IPhysicalAddressRepository physicalAddressRepository;
  @Autowired
  private ICategoryRepository categoryRepository;

  protected GoodRepositoryTest() {
    super(Good.class);
  }

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
    result.add(DomainTestUtil.buildTestGood());
    return result;
  }

  @Override
  protected Good doDuplicateDeniedTestEntity(UniqueMultiColumnConstraintColumns columns) {
    return null;
  }

  @Override
  protected void validateAnotherRepositoryEmpty() {
    Assertions.assertEquals(0, producerRepository.count());
    Assertions.assertEquals(0, physicalAddressRepository.count());
    categoryRepository.deleteAll();
    Assertions.assertEquals(0, categoryRepository.count());
  }
}