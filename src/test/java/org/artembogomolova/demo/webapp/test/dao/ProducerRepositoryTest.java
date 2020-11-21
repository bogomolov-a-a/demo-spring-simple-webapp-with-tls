package org.artembogomolova.demo.webapp.test.dao;

import java.util.ArrayList;
import java.util.List;
import org.artembogomolova.demo.webapp.dao.repo.IProducerRepository;
import org.artembogomolova.demo.webapp.domain.business.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class ProducerRepositoryTest extends AbstractDaoTest<Producer> {

  @Autowired
  private IProducerRepository producerRepository;

  @Override
  protected List<Producer> updateEntities(List<Producer> savedCollection) {
    savedCollection.get(0)
        .setAddress(RepositoryTestUtil.buildProducerNewTestAddress());
    return savedCollection;
  }

  @Override
  protected CrudRepository<Producer, Long> getCrudRepository() {
    return producerRepository;
  }

  @Override
  protected List<Producer> generateEntities() {
    List<Producer> result = new ArrayList<>();
    Producer producer = RepositoryTestUtil.buildProducer();
    result.add(producer);
    return result;
  }
}
