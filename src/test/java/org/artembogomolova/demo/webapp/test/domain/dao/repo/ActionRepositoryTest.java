package org.artembogomolova.demo.webapp.test.domain.dao.repo;

import java.util.ArrayList;
import java.util.List;
import org.artembogomolova.demo.webapp.dao.repo.IActionRepository;
import org.artembogomolova.demo.webapp.domain.business.Action;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class ActionRepositoryTest extends AbstractDaoTest<Action> {

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
    result.add(DomainTestUtil.buildGoodAction());
    result.add(DomainTestUtil.buildCategoryAction());
    return result;
  }
}
