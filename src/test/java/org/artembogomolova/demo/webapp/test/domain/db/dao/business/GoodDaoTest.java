package org.artembogomolova.demo.webapp.test.domain.db.dao.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.dao.repo.business.ICategoryRepository;
import org.artembogomolova.demo.webapp.dao.repo.business.IGoodRepository;
import org.artembogomolova.demo.webapp.dao.repo.business.IProducerRepository;
import org.artembogomolova.demo.webapp.dao.repo.core.IPhysicalAddressRepository;
import org.artembogomolova.demo.webapp.domain.business.Good;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Dao test: Good")
class GoodDaoTest extends AbstractDaoTest<Good> {

  @Autowired
  private IGoodRepository goodRepository;
  @Autowired
  private IProducerRepository producerRepository;
  @Autowired
  private IPhysicalAddressRepository physicalAddressRepository;
  @Autowired
  private ICategoryRepository categoryRepository;

  GoodDaoTest() {
    super(Good.class);
  }

  @Override
  protected List<Good> updateEntities(List<Good> savedCollection) {
    //savedCollection.get(0);
        //.setPrice(42.3f);
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
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected Good doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint,
      Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected Good doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
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
