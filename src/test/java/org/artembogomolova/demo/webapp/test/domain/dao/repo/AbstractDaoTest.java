package org.artembogomolova.demo.webapp.test.domain.dao.repo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.config.CustomHibernateValidatorConfiguration;
import org.artembogomolova.demo.webapp.config.ValidationConfig;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;
import org.artembogomolova.demo.webapp.test.domain.AbstractDatabaseTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@TestExecutionListeners(value = {DependencyInjectionTestExecutionListener.class})
@ComponentScan("org.artembogomolova.demo.webapp.test.domain.dao.repo")
@Import({CustomHibernateValidatorConfiguration.class, ValidationConfig.class})
@Slf4j
public abstract class AbstractDaoTest<T extends IdentifiedEntity> extends AbstractDatabaseTest {

  protected static final Comparator ID_COMPARATOR = Comparator.comparing(IdentifiedEntity::getId);
  @Autowired
  protected EntityModifier entityModifier;
  private Class<T> clazz;

  protected AbstractDaoTest(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Test
  @Transactional
  void crudCheck() {
    CrudRepository repository = getCrudRepository();
    try {
      log.info("build basic entities");
      List<T> entities = generateEntities();
      Assertions.assertFalse(entities == null || entities.isEmpty(), "entities list can't empty!");
      List<T> receivedCollection = entityModifier.transactionalSave(repository,
          entities,
          ID_COMPARATOR,
          (x, y) -> this.handleExceptions((Exception) x, (List<T>) y));
      log.info("basic entities saved in test db");
      entityModifier.transactionalUpdate(repository,
          receivedCollection,
          x -> {
            List<T> result = this.updateEntities((List<T>) x);
            log.info("basic entities modified");
            return result;
          },
          ID_COMPARATOR,
          (x, y) -> this.handleExceptions((Exception) x, (List<T>) y));
      log.info("basic entities updated in test db");
      repository.deleteAll(receivedCollection);
      Assertions.assertEquals(0, repository.count(), "exists entities not deleted from repository " + repository.findAll());
      validateAnotherRepositoryEmpty();
      log.info("passed!");
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

  @Test
  @Transactional
  void duplicateDeniedTest() {
    UniqueMultiColumnConstraint uniqueMultiColumnConstraint = clazz.getAnnotation(UniqueMultiColumnConstraint.class);
    if (uniqueMultiColumnConstraint == null) {
      log.info("entity has no multicolumn constraint. passed!");
      return;
    }
    CrudRepository<T, Long> repository = getCrudRepository();
    try {
      Arrays.stream(uniqueMultiColumnConstraint.constraints()).forEach(
          uniqueMultiColumnConstraintColumns ->
              duplicateDeniedTestForConstraint(repository, uniqueMultiColumnConstraintColumns)
      );
      validateAnotherRepositoryEmpty();
      log.info("passed!");
    } finally {
      if (repository != null) {
        repository.deleteAll();
      }
    }

  }

  private void duplicateDeniedTestForConstraint(CrudRepository<T, Long> repository, UniqueMultiColumnConstraintColumns uniqueMultiColumnConstraintColumns) {
    String uniqueMultiColumnConstraintColumnsName = uniqueMultiColumnConstraintColumns.name();
    tryToPersistEntityDuplicate(repository, uniqueMultiColumnConstraintColumns);
    /*second time to duplicate check*/
    try {
      tryToPersistEntityDuplicate(repository, uniqueMultiColumnConstraintColumns);
    } catch (RuntimeException e) {
      if (!(e.getCause() instanceof ConstraintViolationException)) {
        throw e.getCause() != null ? new RuntimeException(e.getCause()) : e;
      }

      log.info("check for constraint '{}' passed!", uniqueMultiColumnConstraintColumnsName);
      return;
    }
    throw new RuntimeException("not implemented check for '" + uniqueMultiColumnConstraintColumnsName + "'");
  }

  private void tryToPersistEntityDuplicate(CrudRepository repository,
      UniqueMultiColumnConstraintColumns uniqueMultiColumnConstraintColumns) {
    entityModifier.getSavedEntityCollection(repository,
        Collections.singletonList(doDuplicateDeniedTestEntity(uniqueMultiColumnConstraintColumns)),
        (x, y) -> this.handleExceptions((Exception) x, (List<T>) y));
  }

  protected abstract T doDuplicateDeniedTestEntity(UniqueMultiColumnConstraintColumns columns);

}
