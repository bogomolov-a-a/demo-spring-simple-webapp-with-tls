package org.artembogomolova.demo.webapp.test.domain.dao.repo;

import java.util.ArrayList;
import java.util.List;
import org.artembogomolova.demo.webapp.dao.repo.IProducerRepository;
import org.artembogomolova.demo.webapp.domain.business.Producer;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

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
  protected Producer doDuplicateDeniedTestEntity(UniqueMultiColumnConstraintColumns columns) {
    return null;
  }
}
