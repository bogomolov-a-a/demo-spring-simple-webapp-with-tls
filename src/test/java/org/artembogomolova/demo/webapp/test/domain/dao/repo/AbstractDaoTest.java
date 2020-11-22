package org.artembogomolova.demo.webapp.test.domain.dao.repo;

import java.util.Comparator;
import java.util.List;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;
import org.artembogomolova.demo.webapp.test.domain.AbstractDatabaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@TestExecutionListeners(value = {DependencyInjectionTestExecutionListener.class})
@ComponentScan("org.artembogomolova.demo.webapp.test.domain.dao.repo")
abstract class AbstractDaoTest<T extends IdentifiedEntity> extends AbstractDatabaseTest {

  protected static final Comparator ID_COMPARATOR = Comparator.comparing(IdentifiedEntity::getId);
  @Autowired
  private EntityModifier entityModifier;

  @Test
  @Transactional
  void crudCheck() {
    CrudRepository repository = getCrudRepository();
    try {
      List<T> entities = generateEntities();
      Assertions.assertFalse(entities == null || entities.isEmpty(), "entities list can't empty!");
      List<T> receivedCollection = entityModifier.transactionalSave(repository,
          entities,
          ID_COMPARATOR,
          (x, y) -> this.handleExceptions((Exception) x, (List<T>) y));
      entityModifier.transactionalUpdate(repository,
          receivedCollection,
          x -> this.updateEntities((List<T>) x),
          ID_COMPARATOR,
          (x, y) -> this.handleExceptions((Exception) x, (List<T>) y));
      repository.deleteAll(receivedCollection);
      Assertions.assertEquals(0, repository.count(), "exists entities not deleted from repository " + repository.findAll());
      validateAnotherRepositoryEmpty();
    } finally {
      if (repository != null) {
        repository.deleteAll();
      }
    }
  }

  protected boolean handleExceptions(Exception e, List<T> entities) {
    /*Exceptions are wrong.*/
    return false;
  }

  protected void validateAnotherRepositoryEmpty() {
    //no repositories validate needed
  }

  protected abstract List<T> updateEntities(List<T> savedCollection);

  protected abstract CrudRepository<T, Long> getCrudRepository();

  protected abstract List<T> generateEntities();


}
