package org.artembogomolova.demo.webapp.test.db.dao

import java.util.Collections
import java.util.function.BiFunction
import java.util.function.Function
import java.util.stream.Collectors
import org.artembogomolova.demo.webapp.main.domain.core.IdentifiedEntity
import org.junit.jupiter.api.Assertions
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
class EntityModifier<T : IdentifiedEntity<*>> {
    fun transactionalSave(
        repository: CrudRepository<T, Long>, entities: List<T>,
        comparator: Comparator<*>,
        handleExceptions: BiFunction<Exception, List<T>, Boolean>
    ): List<T> {
        val savedCollection = getSavedEntityCollection(repository, entities, handleExceptions)
        val identifierCollection = getIdentifierCollection(savedCollection!!)
        val receivedCollection = repository.findAllById(identifierCollection).toMutableList()
        validateSaveOperationData(savedCollection, receivedCollection, comparator)
        return receivedCollection
    }

    private fun getIdentifierCollection(collection: List<T>): MutableList<Long> {
        return collection.stream()
            .map(IdentifiedEntity<*>::id)
            .collect(Collectors.toList())
    }

    fun getSavedEntityCollection(
        repository: CrudRepository<T, Long>,
        entities: List<T>,
        handleExceptions: BiFunction<Exception, List<T>, Boolean>
    ): List<T>? = (try {
        repository.saveAll(entities).toMutableList()
    } catch (e: Exception) {
        if (!handleExceptions.apply(e, entities)) {
            throw RuntimeException(e)
        }
        null
    })


    fun validateSaveOperationData(
        savedEntityCollection: List<T>,
        receivedEntityCollection: List<T>,
        comparator: Comparator<*>
    ) {
        Assertions.assertNotNull(savedEntityCollection, "entitySavedCollection can't not be null!")
        Assertions.assertNotNull(receivedEntityCollection, "receivedEntityCollection can't not be null!")
        @Suppress("UNCHECKED_CAST")
        Collections.sort(savedEntityCollection, comparator as Comparator<in T>)
        Collections.sort(receivedEntityCollection, comparator)
        Assertions.assertEquals(
            savedEntityCollection, receivedEntityCollection,
            "collections are different!\nExpected: $savedEntityCollection\nGot:$receivedEntityCollection"
        )
    }

    fun transactionalUpdate(
        repository: CrudRepository<T, Long>,
        receivedCollection: MutableList<T>,
        updateEntities: Function<List<T>, List<T>>,
        comparator: Comparator<*>,
        handleExceptions: BiFunction<Exception, List<T>, Boolean>
    ) {
        val updatedEntities = updateEntities.apply(receivedCollection)
        val updatedCollection = getSavedEntityCollection(repository, updatedEntities, handleExceptions)
        /*identifiers already exists*/
        receivedCollection.clear()
        receivedCollection.addAll((repository.findAllById(getIdentifierCollection(updatedCollection!!))))
        validateSaveOperationData(updatedCollection, receivedCollection, comparator)
    }
}

