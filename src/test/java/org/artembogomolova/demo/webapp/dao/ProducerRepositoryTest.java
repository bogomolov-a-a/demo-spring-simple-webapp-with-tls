package org.artembogomolova.demo.webapp.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.artembogomolova.demo.webapp.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class ProducerRepositoryTest extends AbstractDaoTest<Producer> {

  @Autowired
  private IProducerRepository producerRepository;
  @Override
  protected Collection<Producer> updateEntities(Collection<Producer> savedCollection) {
    ((List<Producer>)savedCollection).get(0).setAddress(RepositoryTestUtil.buildProducerNewTestAddress());
    return savedCollection;
  }

  @Override
  protected CrudRepository<Producer, Long> getCrudRepository() {
    return producerRepository;
  }

  @Override
  protected Collection<Producer> generateEntities() {
    List<Producer> result=new ArrayList<>();
    Producer producer = RepositoryTestUtil.buildProducer();
    result.add(producer);
    return result;
  }
}
