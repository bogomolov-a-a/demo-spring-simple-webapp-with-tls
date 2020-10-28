package org.artembogomolova.demo.webapp.dao;

import java.util.ArrayList;
import java.util.List;
import org.artembogomolova.demo.webapp.model.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class ActionRepositoryTest extends AbstractDaoTest<Action>{

  @Autowired
  private IActionRepository actionRepository;
  @Override
  protected List<Action> updateEntities(List<Action> savedCollection) {
    savedCollection.get(0)
        .setDiscountFixed(420.0f);
    return savedCollection;
  }

  @Override
  protected CrudRepository<Action, Long> getCrudRepository() {
    return actionRepository;
  }

  @Override
  protected List<Action> generateEntities() {
    List<Action> result = new ArrayList<>();
    result.add(RepositoryTestUtil.buildGoodAction());
    result.add(RepositoryTestUtil.buildCategoryAction());
    return result;
  }
}
