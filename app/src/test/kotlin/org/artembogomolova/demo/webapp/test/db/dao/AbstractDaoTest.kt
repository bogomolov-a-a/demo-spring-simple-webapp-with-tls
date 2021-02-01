package org.artembogomolova.demo.webapp.test.db.dao

import java.util.*
import java.util.function.Supplier
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Validator
import org.artembogomolova.demo.webapp.main.config.ValidationConfig
import org.artembogomolova.demo.webapp.main.config.db.CustomHibernateValidatorConfiguration
import org.artembogomolova.demo.webapp.main.domain.core.IdentifiedEntity
import org.artembogomolova.demo.webapp.main.validation.UniqueMultiColumn
import org.artembogomolova.demo.webapp.test.db.AbstractDatabaseTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

@ComponentScan("org.artembogomolova.demo.webapp.test.db.dao")
@Import(CustomHibernateValidatorConfiguration::class, ValidationConfig::class)
abstract class AbstractDaoTest<T : IdentifiedEntity<T>> protected constructor(
    clazz: Class<T>,
    private val constructor: Supplier<T>
) : AbstractDatabaseTest<T>(clazz, TEST_CLASS_NAME_SUFFIX, TEST_CLASS_DISPLAY_NAME_PREFIX) {
    @Autowired
    protected lateinit var entityModifier: EntityModifier<T>

    @Autowired
    private lateinit var validator: Validator

    @Test
    @DisplayName("Test for CRUD operation for entity.")
    @Transactional
    fun crudCheck() {
        val repository: CrudRepository<T, Long> = crudRepository()
        try {
            log.info("build basic entities")
            val entities = generateEntities()
            Assertions.assertFalse(entities.isEmpty(), "entities list can't empty!")
            val receivedCollection: List<T> = entityModifier.transactionalSave(
                repository,
                entities,
                ID_COMPARATOR
            ) { exception, list -> handleExceptions(exception, list) }
            log.info("basic entities saved in test db")
            entityModifier.transactionalUpdate(
                repository,
                receivedCollection as MutableList<T>,
                { x ->
                    val result: List<T> = updateEntities(x)
                    log.info("basic entities modified")
                    result
                },
                ID_COMPARATOR
            ) { exception, list -> handleExceptions(exception, list) }
            log.info("basic entities updated in test db")
            repository.deleteAll(receivedCollection)
            Assertions.assertEquals(EMPTY_REPOSITORY_COUNT, repository.count(), "exists entities not deleted from repository " + repository.findAll())
            validateAnotherRepositoryEmpty()
            log.info("passed!")
        } finally {
            cleanRepositories()
        }
    }

    /*Exceptions are wrong.*/
    protected fun handleExceptions(e: Exception?, entities: List<T>?): Boolean = false

    protected open fun validateAnotherRepositoryEmpty() {
        //no repositories validate needed
    }

    protected abstract fun updateEntities(savedCollection: List<T>): List<T>
    protected abstract fun crudRepository(): CrudRepository<T, Long>
    protected abstract fun generateEntities(): List<T>

    @Test
    @Transactional
    @DisplayName("Test for constraint violation exception for duplicate entity creating by unique constraint.")
    fun duplicateDeniedTest() {
        val uniqueMultiColumn: UniqueMultiColumn? = testingClass.getAnnotation(UniqueMultiColumn::class.java)
        if (uniqueMultiColumn == null) {
            log.info("entity has no multi column constraint. passed!")
            return
        }
        val repository: CrudRepository<T, Long> = crudRepository()
        try {
            Arrays.stream(uniqueMultiColumn.constraints)
                .forEach { uniqueMultiColumnConstraintColumns -> duplicateDeniedTestForConstraint(repository, uniqueMultiColumnConstraintColumns) }
            log.info("passed!")
        } finally {
            repository.deleteAll()
        }
    }

    private fun duplicateDeniedTestForConstraint(
        repository: CrudRepository<T, Long>,
        uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint
    ) {
        var commonValues = buildCommonFieldValues(uniqueMultiColumnConstraint)
        if (commonValues == null) {
            commonValues = emptyMap<String, Any>()
        }
        prepareConditions(repository, uniqueMultiColumnConstraint, commonValues)
        doDuplicateDeniedTestForConstraint(repository, uniqueMultiColumnConstraint, commonValues)
        /*after each constraint test execution clean all used repositories!*/cleanRepositories()
    }

    protected fun cleanRepositories() {
        crudRepository().deleteAll()
    }

    protected abstract fun buildCommonFieldValues(
        uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint
    ): Map<String, Any?>?

    private fun doDuplicateDeniedTestForConstraint(
        repository: CrudRepository<T, Long>, uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint,
        commonValues: Map<String, Any?>
    ) {
        val uniqueMultiColumnConstraintColumnsName: String = uniqueMultiColumnConstraint.name
        /*second time to duplicate check*/try {
            tryToPersistEntityDuplicate(repository, uniqueMultiColumnConstraint, commonValues)
        } catch (e: RuntimeException) {
            if (e.cause !is ConstraintViolationException) {
                throw if (e.cause != null) RuntimeException(e.cause) else e
            }
            log.info("check for constraint '{}' passed!", uniqueMultiColumnConstraintColumnsName)
            return
        }
        throw RuntimeException("not implemented check for '$uniqueMultiColumnConstraintColumnsName'")
    }

    protected fun prepareConditions(
        repository: CrudRepository<T, Long>, uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint,
        commonValues: Map<String, Any?>
    ) {
        tryToPersistPreparedEntity(repository, uniqueMultiColumnConstraint, commonValues)
    }

    private fun tryToPersistPreparedEntity(
        repository: CrudRepository<T, Long>, uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint,
        commonValues: Map<String, Any?>
    ) {
        val entity = doPrepareDeniedTestEntity(uniqueMultiColumnConstraint, commonValues)
        log.info("prepared entity {}", entity.toString())
        entityModifier.getSavedEntityCollection(
            repository, listOf(entity)
        ) { exception, list -> handleExceptions(exception, list) }
    }

    protected abstract fun doPrepareDeniedTestEntity(
        uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint,
        commonValues: Map<String, Any?>
    ): T

    private fun tryToPersistEntityDuplicate(
        repository: CrudRepository<T, Long>,
        uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint,
        commonValues: Map<String, Any?>
    ) {
        val entity = doDuplicateDeniedTestEntity(uniqueMultiColumnConstraint, commonValues)
        log.info("entity with duplicated column values {}", entity.toString())
        entityModifier.getSavedEntityCollection(
            repository, listOf(entity)
        ) { x, y -> handleExceptions(x, y) }
    }

    protected abstract fun doDuplicateDeniedTestEntity(columns: UniqueMultiColumn.UniqueMultiColumnConstraint, commonValues: Map<String, Any?>): T

    @Test
    @DisplayName("Test for validation without another violations(not 'UniqueMultiColumnConstraint' annotations). Entity with all correct fields.")
    fun validatorTest() {
        val violations: Set<ConstraintViolation<T>> = validator.validate(buildEntityWithoutViolationEntity())
        Assertions.assertTrue(violations.isEmpty(), "entity has following violations: $violations")
    }

    protected open fun buildEntityWithoutViolationEntity(): T = constructor.get()

    @Test
    @DisplayName(
        "Test for validation with another violations(not 'UniqueMultiColumnConstraint' annotations). "
                + "Entity with one or more incorrect fields. Basically entity no-arg constructor"
    )
    fun validatorWithViolationTest() {
        val violations: Set<ConstraintViolation<T>> = validator.validate(buildWithViolationEntity())
        log.info("entity has following violations: $violations")
        Assertions.assertFalse(violations.isEmpty(), "entity has no violations!")
        /* val exceptedViolations = buildExceptedViolations()
         Assertions.assertEquals(exceptedViolations, violations, "excepted violations $exceptedViolations, actual violations: $violations\"")*/
    }

//    protected open fun buildExceptedViolations(): Set<ConstraintViolation<T>> = setOf()

    protected fun buildWithViolationEntity(): T = constructor.get()

    companion object {
        private const val TEST_CLASS_NAME_SUFFIX = "DaoTest"
        private const val TEST_CLASS_DISPLAY_NAME_PREFIX = "Dao test: "
        private val ID_COMPARATOR = Comparator.comparing<IdentifiedEntity<*>, Long?>(IdentifiedEntity<*>::id)
        internal const val EMPTY_REPOSITORY_COUNT = 0L
    }
}