package org.artembogomolova.demo.webapp.test.domain.entity

import org.artembogomolova.demo.webapp.main.domain.core.IdentifiedEntity
import org.artembogomolova.demo.webapp.main.validation.UniqueMultiColumn
import org.artembogomolova.demo.webapp.test.AbstractClassTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.lang.reflect.Modifier
import javax.validation.ValidationException
import kotlin.reflect.KMutableProperty1

abstract class AbstractAccessorEntityTest<T : IdentifiedEntity<T>> protected constructor(
    clazz: Class<T>,
    private val copyMethod: Function1<T, T>,
) : AbstractClassTest<T>(
    clazz,
    TEST_CLASS_SUFFIX,
    TEST_CLASS_DISPLAY_NAME
) {
    private val availableConstraintNames = getAvailableConstraintNames()
    private var standardEntity: T? = null
    protected open fun getAvailableConstraintNames(): List<String> {
        return listOf(IdentifiedEntity.BASIC_CONSTRAINT_NAME)
    }

    @BeforeEach
    fun init() {
        standardEntity = buildStandardEntity()
    }

    @Test
    @DisplayName("Basically equal contract test")
    fun transitiveEqualTest() {
        requireNotNull(standardEntity) {
            "standardEntity must be not null!! wrong test case!!"
        }
        log.info("transitive equal test started")
        log.info("testing entity: {}", standardEntity)
        containFieldCorrectValuesTest(standardEntity!!)
        Assertions.assertEquals(standardEntity, standardEntity)
        Assertions.assertNotEquals(standardEntity, Any())
        Assertions.assertNotEquals(standardEntity, null)
        Assertions.assertEquals(standardEntity.hashCode(), standardEntity.hashCode())
        printEntityAsString()
        log.info("transitive equal test passed")
    }

    protected open fun containFieldCorrectValuesTest(standardEntity: T) {
        throw UnsupportedOperationException("not implemented yet!")
    }

    private fun printEntityAsString() {
        log.info("standard entity: {}", standardEntity.toString())
    }

    protected abstract fun buildStandardEntity(): T

    @Test
    @DisplayName("Duplicate entity(different link) equal contract test")
    fun fullDuplicateEntityEqualTest() {
        log.info("full duplicate test started. Only natural key fields!")
        val duplicateEntity = buildDuplicateEntity()
        log.info("testing entity: {}", standardEntity)
        log.info("testing duplicate entity: {}", duplicateEntity)
        Assertions.assertEquals(standardEntity, duplicateEntity)
        Assertions.assertEquals(standardEntity.hashCode(), duplicateEntity.hashCode())
        log.info("full duplicate test passed. ")
    }

    private fun buildDuplicateEntity(): T? {
        return standardEntity?.let { copyMethod.invoke(it) }
    }

    @Test
    @DisplayName("Equal contract test for different entities.")
    fun withAnotherEntityEqualTest() {
        log.info("another entity test started. Only natural key fields!")
        val entity = buildAnotherEntityForTest()
        log.info("testing entity: {}", standardEntity)
        log.info("testing another entity: {}", entity)
        Assertions.assertNotEquals(standardEntity, entity)
        Assertions.assertNotEquals(entity, standardEntity)
        Assertions.assertNotEquals(standardEntity.hashCode(), entity.hashCode())
        log.info("another entity test passed.")
    }

    protected abstract fun buildAnotherEntityForTest(): T
    private fun withoutPartOfUniqueConstraintEqualTest(standardEntity: T, constraintName: String, columnName: String) {
        if (checkBasicConstraint(standardEntity, constraintName, columnName)) {
            return
        }
        checkAlternateConstraints(standardEntity, constraintName, columnName)
    }

    private fun checkAlternateConstraints(standardEntity: T, constraintName: String, columnName: String) {
        if (!withoutAlternateConstraints(standardEntity, constraintName, columnName)) {
            throw ValidationException("One or more unique constraints have violations!")
        }
    }

    private fun checkBasicConstraint(standardEntity: T, constraintName: String, columnName: String): Boolean {
        if (IdentifiedEntity.BASIC_CONSTRAINT_NAME != constraintName) {
            return false
        }
        if (!withoutBasicConstraint(standardEntity, columnName)) {
            throw ValidationException(IdentifiedEntity.BASIC_CONSTRAINT_NAME + " has violations!")
        }
        return true
    }

    protected open fun withoutAlternateConstraints(standardEntity: T, constraintName: String, columnName: String): Boolean {
        val constraintNames = java.util.List.copyOf(availableConstraintNames)
        constraintNames.remove(IdentifiedEntity.BASIC_CONSTRAINT_NAME)
        return constraintNames.isEmpty() && IdentifiedEntity.BASIC_CONSTRAINT_NAME != constraintName
    }

    protected abstract fun withoutBasicConstraint(standardEntity: T, columnName: String): Boolean
    protected fun <U> withoutColumnEqualTest(
        entity: T,
        property: KMutableProperty1<T, U?>,
    ) {
        val duplicateEntity = buildDuplicateEntity()!!
        setNullFieldValueAndNotEquals(
            duplicateEntity,
            entity,
            property
        )
        exchangeDupStandardFieldNullValueAndNotEquals(
            duplicateEntity,
            entity,
            property
        )
        setNullFieldValueAndEquals(
            duplicateEntity,
            entity,
            property
        )
    }

    private fun <U> setNullFieldValueAndNotEquals(
        duplicateEntity: T,
        entity: T,
        property: KMutableProperty1<T, U?>,
    ) {
        log.info("'duplicate entity field set null and equals' started")
        property.set(duplicateEntity, null)
        log.info("standard entity: {}", entity)
        log.info("duplicate entity: {}", duplicateEntity)
        Assertions.assertNotEquals(entity, duplicateEntity)
        Assertions.assertNotEquals(duplicateEntity, entity)
        Assertions.assertNotEquals(entity.hashCode(), duplicateEntity.hashCode())
        log.info("'duplicate entity field set null and equals' passed.")
    }

    private fun <U> exchangeDupStandardFieldNullValueAndNotEquals(
        duplicateEntity: T,
        entity: T,
        property: KMutableProperty1<T, U?>,
    ) {
        log.info("'exchange duplicate and standard entity field null value and not equals' started.")
        property.set(duplicateEntity, property.get(entity))
        property.set(entity, null)
        log.info("standard entity: {}", entity)
        log.info("duplicate entity: {}", duplicateEntity)
        Assertions.assertNotEquals(entity, duplicateEntity)
        Assertions.assertNotEquals(duplicateEntity, entity)
        Assertions.assertNotEquals(entity.hashCode(), duplicateEntity.hashCode())
        log.info("'exchange duplicate and standard entity field null value and not equals' passed.")
    }

    private fun <U> setNullFieldValueAndEquals(
        duplicateEntity: T,
        entity: T,
        setter: KMutableProperty1<T, U?>,
    ) {
        log.info("'duplicate and standard entity field set null value and not equals' started.")
        setter.set(duplicateEntity, null)
        log.info("standard entity: {}", entity)
        log.info("duplicate entity: {}", duplicateEntity)
        Assertions.assertEquals(entity, duplicateEntity)
        Assertions.assertEquals(duplicateEntity, entity)
        Assertions.assertEquals(entity.hashCode(), duplicateEntity.hashCode())
        log.info("'duplicate and standard entity field set null value and not equals' passed.")
    }

    @Test
    @DisplayName("Unique constraint equal(multi column, without one of columns, swap column value).")
    fun uniqueConstraintEntityTest() {
        val multiColumnConstraint: UniqueMultiColumn? = testingClass.getAnnotation(UniqueMultiColumn::class.java)
        if (multiColumnConstraint == null) {
            log.warn("clazz {} has no multi column constraint.", testingClass.name)
            return
        }
        multiColumnConstraint.constraints.forEach { constraint -> multiColumnConstraintTest(constraint) }
    }

    private fun multiColumnConstraintTest(constraint: UniqueMultiColumn.UniqueMultiColumnConstraint) {
        val constraintName: String = constraint.name
        log.info("testing constraint \'{}\'", constraintName)
        availableConstraint(constraintName)
        constraint.columnNames.forEach { columnName -> withoutOneColumnTest(columnName, constraintName) }
        log.info("constraint \'{}\' test passed!", constraintName)
    }

    private fun withoutOneColumnTest(columnName: String, constraintName: String) {
        log.info("testing column name \'{}\'", columnName)
        withoutPartOfUniqueConstraintEqualTest(buildStandardEntity(), constraintName, columnName)
        log.info("column name \'{}\' test passed!", columnName)
    }

    private fun availableConstraint(constraintName: String) {
        if (availableConstraintNames.contains(constraintName)) {
            return
        }
        throw IllegalArgumentException(String.format(ENTITY_CONSTRAINT_NOT_SUPPORTED, constraintName))
    }

    @DisplayName("Validate model class has final modifier!")
    @Test
    fun finalModifierTest() {
        Assertions.assertTrue((testingClass.modifiers and Modifier.FINAL) > 0, "model class must be final!")
    }

    companion object {
        protected const val ENTITY_CONSTRAINT_NOT_SUPPORTED = "Entity constraint %s not supported!"
        private const val TEST_CLASS_SUFFIX = "EntityTest"
        private const val TEST_CLASS_DISPLAY_NAME = "Entity test: "
    }

}