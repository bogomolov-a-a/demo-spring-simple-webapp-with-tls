package org.artembogomolova.demo.webapp.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.artembogomolova.demo.webapp.model.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class GoodRepositoryTest extends AbstractDaoTest<Good>{

  @Autowired
  private IGoodRepository goodRepository;
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
}
