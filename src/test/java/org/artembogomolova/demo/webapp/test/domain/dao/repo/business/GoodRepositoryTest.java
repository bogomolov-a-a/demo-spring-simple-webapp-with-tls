package org.artembogomolova.demo.webapp.test.domain.dao.repo.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.dao.repo.business.ICategoryRepository;
import org.artembogomolova.demo.webapp.dao.repo.business.IGoodRepository;
import org.artembogomolova.demo.webapp.dao.repo.business.IProducerRepository;
import org.artembogomolova.demo.webapp.dao.repo.core.IPhysicalAddressRepository;
import org.artembogomolova.demo.webapp.domain.business.Good;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.dao.repo.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Good entity repository test")
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
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraintColumns uniqueMultiColumnConstraintColumns) {
    return null;
  }

  @Override
  protected Good doPrepareDeniedTestEntity(UniqueMultiColumnConstraintColumns uniqueMultiColumnConstraintColumns,
      Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected Good doDuplicateDeniedTestEntity(UniqueMultiColumnConstraintColumns columns, Map<String, Object> commonValues) {
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
