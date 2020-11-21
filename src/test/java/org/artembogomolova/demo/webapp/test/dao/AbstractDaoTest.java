package org.artembogomolova.demo.webapp.test.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles({"test"})
@Slf4j
@AutoConfigureTestDatabase(replace = Replace.NONE, connection = EmbeddedDatabaseConnection.NONE)
abstract class AbstractDaoTest<T extends IdentifiedEntity> {

  protected static final Comparator ID_COMPARATOR = Comparator.comparing(IdentifiedEntity::getId);

  @Test
  void crudCheck() {
    CrudRepository repository = getCrudRepository();
    try {
      List<T> entities = generateEntities();
      Assertions.assertFalse(entities == null || entities.isEmpty(), "entities list can't empty!");
      List<T> savedCollection = getSavedEntityCollection(repository, entities);
      List<Long> identifierCollection = savedCollection.stream()
          .map(IdentifiedEntity::getId)
          .collect(Collectors.toList());
      List<T> receivedCollection = (List<T>) repository.findAllById(identifierCollection);
      validateSaveOperationData(savedCollection, receivedCollection);
      receivedCollection.clear();
      List<T> updatedEntities = updateEntities(savedCollection);
      List<T> updatedCollection = getSavedEntityCollection(repository, updatedEntities);
      /*identifiers already exists*/
      receivedCollection.addAll((List<T>) repository.findAllById(identifierCollection));
      validateSaveOperationData(updatedCollection, receivedCollection);
      repository.deleteAll(receivedCollection);
      Assertions.assertEquals(0, repository.count(), "exists entities not deleted from repository " + repository.findAll());
      validateAnotherRepositoryEmpty();
    } finally {
      if (repository != null) {
        repository.deleteAll();
      }
    }
  }

  protected List<T> getSavedEntityCollection(CrudRepository repository, List<T> entities) {
    try {
      return (List<T>) repository.saveAll(entities);
    } catch (Exception e) {
      if (!handleExceptions(e, entities)) {
        throw new RuntimeException(e);
      }
      return null;
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

  protected void validateSaveOperationData(List<T> savedEntityCollection, List<T> receivedEntityCollection) {
    Assertions.assertNotNull(savedEntityCollection, "entitySavedCollection can't not be null!");
    Assertions.assertNotNull(receivedEntityCollection, "receivedEntityCollection can't not be null!");
    Collections.sort(savedEntityCollection, ID_COMPARATOR);
    Collections.sort(receivedEntityCollection, ID_COMPARATOR);
    Assertions.assertEquals(savedEntityCollection, receivedEntityCollection,
        "collections are different!\nExpected: " + savedEntityCollection + "\nGot:" + receivedEntityCollection);
  }


}
