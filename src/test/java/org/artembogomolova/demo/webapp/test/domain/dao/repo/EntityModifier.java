package org.artembogomolova.demo.webapp.test.domain.dao.repo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;
import org.junit.jupiter.api.Assertions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EntityModifier<T extends IdentifiedEntity> {

  public List<T> transactionalSave(CrudRepository repository, List<T> entities,
      Comparator<T> comparator,
      BiFunction<Exception, List<T>, Boolean> handleExceptions) {
    List<T> savedCollection = getSavedEntityCollection(repository, entities, handleExceptions);
    List<Long> identifierCollection = getIdentifierCollection(savedCollection);
    List<T> receivedCollection = (List<T>) repository.findAllById(identifierCollection);
    validateSaveOperationData(savedCollection, receivedCollection, comparator);
    return receivedCollection;
  }

  private List<Long> getIdentifierCollection(List<T> collection) {
    return collection.stream()
        .map(IdentifiedEntity::getId)
        .collect(Collectors.toList());
  }

  protected List<T> getSavedEntityCollection(CrudRepository repository, List<T> entities,
      BiFunction<Exception, List<T>, Boolean> handleExceptions) {
    try {
      return (List<T>) repository.saveAll(entities);
    } catch (Exception e) {
      if (!handleExceptions.apply(e, entities)) {
        throw new RuntimeException(e);
      }
      return null;
    }
  }

  protected void validateSaveOperationData(List<T> savedEntityCollection, List<T> receivedEntityCollection,
      Comparator<T> comparator) {
    Assertions.assertNotNull(savedEntityCollection, "entitySavedCollection can't not be null!");
    Assertions.assertNotNull(receivedEntityCollection, "receivedEntityCollection can't not be null!");
    Collections.sort(savedEntityCollection, comparator);
    Collections.sort(receivedEntityCollection, comparator);
    Assertions.assertEquals(savedEntityCollection, receivedEntityCollection,
        "collections are different!\nExpected: " + savedEntityCollection + "\nGot:" + receivedEntityCollection);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void transactionalUpdate(CrudRepository repository, List<T> receivedCollection,
      Function<List<T>, List<T>> updateEntities,
      Comparator<T> comparator,
      BiFunction<Exception, List<T>, Boolean> handleExceptions) {
    List<T> updatedEntities = updateEntities.apply(receivedCollection);
    List<T> updatedCollection = getSavedEntityCollection(repository, updatedEntities, handleExceptions);
    /*identifiers already exists*/
    receivedCollection.clear();
    receivedCollection.addAll((List<T>) repository.findAllById(getIdentifierCollection(updatedCollection)));
    validateSaveOperationData(updatedCollection, receivedCollection, comparator);
  }
}
