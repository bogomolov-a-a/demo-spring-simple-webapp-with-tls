package org.artembogomolova.demo.webapp.test.domain.dao.repo.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.dao.repo.business.IProducerRepository;
import org.artembogomolova.demo.webapp.domain.business.Producer;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.dao.repo.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Producer entity repository test")
public class ProducerRepositoryTest extends AbstractDaoTest<Producer> {

  @Autowired
  private IProducerRepository producerRepository;

  protected ProducerRepositoryTest() {
    super(Producer.class);
  }

  @Override
  protected List<Producer> updateEntities(List<Producer> savedCollection) {
    savedCollection.get(0)
        .setAddress(DomainTestUtil.buildProducerNewTestAddress());
    return savedCollection;
  }

  @Override
  protected CrudRepository<Producer, Long> getCrudRepository() {
    return producerRepository;
  }

  @Override
  protected List<Producer> generateEntities() {
    List<Producer> result = new ArrayList<>();
    Producer producer = DomainTestUtil.buildProducer();
    result.add(producer);
    return result;
  }

  @Override
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected Producer doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint,
      Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected Producer doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
    return null;
  }
}
