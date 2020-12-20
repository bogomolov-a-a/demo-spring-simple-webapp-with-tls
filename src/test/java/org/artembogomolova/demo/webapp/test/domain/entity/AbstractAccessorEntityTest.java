package org.artembogomolova.demo.webapp.test.domain.entity;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.test.AbstractClassTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public abstract class AbstractAccessorEntityTest<T extends IdentifiedEntity> extends
    AbstractClassTest<T> {

  protected static final String ENTITY_CONSTRAINT_NOT_SUPPORTED = "Entity constraint %s not supported!";
  private static final String TEST_CLASS_SUFFIX = "EntityTest";
  private static final String TEST_CLASS_DISPLAY_NAME = "Entity test: ";
  private List<String> availableConstraintNames = getAvailableConstraintNames();
  private T standardEntity;
  private final Function<T, ? extends T> mockDescendantClassConstructor;
  private final Function<T, T> copyConstructor;

  protected AbstractAccessorEntityTest(Class<T> clazz,
      Function<T, T> copyConstructor,
      Function<T, ? extends T> mockDescendantClassConstructor) {
    super(clazz,
        TEST_CLASS_SUFFIX,
        TEST_CLASS_DISPLAY_NAME);
    this.copyConstructor = copyConstructor;
    this.mockDescendantClassConstructor = mockDescendantClassConstructor;
  }

  protected List<String> getAvailableConstraintNames() {
    return List.of(IdentifiedEntity.BASIC_CONSTRAINT_NAME);
  }

  @BeforeEach
  void init() {
    standardEntity = buildStandardEntity();
  }

  @Test
  @DisplayName("Basically equal contract test")
  void transitiveEqualTest() {
    if (standardEntity == null) {
      throw new IllegalArgumentException("standardEntity must be not null!! wrong test case!!");
    }
    log.info("transitive equal test started");
    log.info("testing entity: {}", standardEntity);
    containFieldCorrectValuesTest(standardEntity);
    Assertions.assertEquals(standardEntity, standardEntity);
    Assertions.assertNotEquals(standardEntity, new Object());
    Assertions.assertNotEquals(standardEntity, null);
    Assertions.assertEquals(standardEntity.hashCode(), standardEntity.hashCode());
    printEntityAsString(standardEntity);
    log.info("transitive equal test passed");
  }

  protected void containFieldCorrectValuesTest(T standardEntity) {
    throw new UnsupportedOperationException("not implemented yet!");
  }

  private void printEntityAsString(T standardEntity) {
    log.info("standard entity: {}", standardEntity.toString());
  }

  protected abstract T buildStandardEntity();

  @Test
  @DisplayName("Duplicate entity(different link) equal contract test")
  void fullDuplicateEntityEqualTest() {
    log.info("full duplicate test started. Only natural key fields!");
    T duplicateEntity = buildDuplicateEntity();
    log.info("testing entity: {}", standardEntity);
    log.info("testing duplicate entity: {}", duplicateEntity);
    Assertions.assertEquals(standardEntity, duplicateEntity);
    Assertions.assertEquals(standardEntity.hashCode(), duplicateEntity.hashCode());
    log.info("full duplicate test passed. ");
  }

  private T buildDuplicateEntity() {
    return copyConstructor.apply(standardEntity);
  }

  @Test
  @DisplayName("Equal contract test for different entities.")
  void withAnotherEntityEqualTest() {
    log.info("another entity test started. Only natural key fields!");
    T entity = buildAnotherEntityForTest();
    log.info("testing entity: {}", standardEntity);
    log.info("testing another entity: {}", entity);
    Assertions.assertNotEquals(standardEntity, entity);
    Assertions.assertNotEquals(entity, standardEntity);
    Assertions.assertNotEquals(standardEntity.hashCode(), entity.hashCode());
    log.info("another entity test passed.");
  }

  protected abstract T buildAnotherEntityForTest();

  private void withoutPartOfUniqueConstraintEqualTest(T standardEntity, String constraintName, String columnName) {
    if (checkBasicConstraint(standardEntity, constraintName, columnName)) {
      return;
    }
    checkAlternateConstraints(standardEntity, constraintName, columnName);
  }

  private void checkAlternateConstraints(T standardEntity, String constraintName, String columnName) {
    if (!withoutAlternateConstraints(standardEntity, constraintName, columnName)) {
      throw new ValidationException("One or more unique constraints have violations!");
    }
  }

  protected boolean checkBasicConstraint(T standardEntity, String constraintName, String columnName) {
    if (!IdentifiedEntity.BASIC_CONSTRAINT_NAME.equals(constraintName)) {
      return false;
    }
    if (!withoutBasicConstraint(standardEntity, columnName)) {
      throw new ValidationException(IdentifiedEntity.BASIC_CONSTRAINT_NAME + " has violations!");
    }
    return true;
  }

  protected boolean withoutAlternateConstraints(T standardEntity, String constraintName, String columnName) {
    List<String> constraintNames = List.copyOf(availableConstraintNames);
    constraintNames.remove(IdentifiedEntity.BASIC_CONSTRAINT_NAME);
    return constraintNames.isEmpty() && !IdentifiedEntity.BASIC_CONSTRAINT_NAME.equals(constraintName);
  }

  protected abstract boolean withoutBasicConstraint(T standardEntity, String columnName);

  protected final <U> void withoutColumnEqualTest(T entity,
      Function<T, U> getter,
      BiConsumer<T, U> setter) {
    T duplicateEntity = buildDuplicateEntity();
    setNullFieldValueAndNotEquals(duplicateEntity,
        entity,
        setter);
    exchangeDupStandardFieldNullValueAndNotEquals(duplicateEntity,
        entity,
        getter,
        setter);
    setNullFieldValueAndEquals(duplicateEntity,
        entity,
        setter);
  }

  private <U> void setNullFieldValueAndNotEquals(T duplicateEntity,
      T entity,
      BiConsumer<T, U> setter) {
    log.info("'duplicate entity field set null and equals' started");
    setter.accept(duplicateEntity, null);
    log.info("standard entity: {}", entity);
    log.info("duplicate entity: {}", duplicateEntity);
    Assertions.assertNotEquals(entity, duplicateEntity);
    Assertions.assertNotEquals(duplicateEntity, entity);
    Assertions.assertNotEquals(entity.hashCode(), duplicateEntity.hashCode());
    log.info("'duplicate entity field set null and equals' passed.");
  }


  private <U> void exchangeDupStandardFieldNullValueAndNotEquals(T duplicateEntity,
      T entity,
      Function<T, U> getter,
      BiConsumer<T, U> setter) {
    log.info("'exchange duplicate and standard entity field null value and not equals' started.");
    setter.accept(duplicateEntity, getter.apply(entity));
    setter.accept(entity, null);
    log.info("standard entity: {}", entity);
    log.info("duplicate entity: {}", duplicateEntity);
    Assertions.assertNotEquals(entity, duplicateEntity);
    Assertions.assertNotEquals(duplicateEntity, entity);
    Assertions.assertNotEquals(entity.hashCode(), duplicateEntity.hashCode());
    log.info("'exchange duplicate and standard entity field null value and not equals' passed.");
  }

  private <U> void setNullFieldValueAndEquals(T duplicateEntity,
      T entity,
      BiConsumer<T, U> setter) {
    log.info("'duplicate and standard entity field set null value and not equals' started.");
    setter.accept(duplicateEntity, null);
    log.info("standard entity: {}", entity);
    log.info("duplicate entity: {}", duplicateEntity);
    Assertions.assertEquals(entity, duplicateEntity);
    Assertions.assertEquals(duplicateEntity, entity);
    Assertions.assertEquals(entity.hashCode(), duplicateEntity.hashCode());
    log.info("'duplicate and standard entity field set null value and not equals' passed.");
  }


  @Test
  @DisplayName("Unique constraint equal(multi column, without one of columns, swap column value).")
  void uniqueConstraintEntityTest() {
    UniqueMultiColumn multiColumnConstraint = getTestingClass().getAnnotation(UniqueMultiColumn.class);
    if (multiColumnConstraint == null) {
      log.warn("clazz {} has no multi column constraint.", getTestingClass().getName());
      return;
    }
    Arrays.asList(multiColumnConstraint.constraints()).forEach(this::multiColumnConstraintTest);
  }

  void multiColumnConstraintTest(UniqueMultiColumnConstraint constraint) {
    String constraintName = constraint.name();
    log.info("testing constraint \'{}\'", constraintName);
    availableConstraint(constraintName);
    Arrays.asList(constraint.columnNames()).forEach(columnName -> withoutOneColumnTest(columnName, constraintName));
    log.info("constraint \'{}\' test passed!", constraintName);
  }

  private void withoutOneColumnTest(String columnName, String constraintName) {
    log.info("testing column name \'{}\'", columnName);
    withoutPartOfUniqueConstraintEqualTest(buildStandardEntity(), constraintName, columnName);
    log.info("column name \'{}\' test passed!", columnName);
  }


  private void availableConstraint(String constraintName) {
    if (availableConstraintNames.contains(constraintName)) {
      return;
    }
    throw new IllegalArgumentException(String.format(ENTITY_CONSTRAINT_NOT_SUPPORTED, constraintName));
  }

  @Test
  @DisplayName("Test for descendants class part equal contract(<U extends T> not equals <T>!). "
      + "Descendant class must be Mock(with copy constructor)")
  void descendantClassEqualTest() {
    log.info("Mock descendant test started.");
    T mockDescendantEntity = mockDescendantClassConstructor.apply(standardEntity);
    Class<? extends IdentifiedEntity> standardEntityClass = standardEntity.getClass();
    String standardEntityClassName = standardEntityClass.getName();
    Class<? extends IdentifiedEntity> mockDescendantEntityClass = mockDescendantEntity.getClass();
    String mockDescendantEntityClassName = mockDescendantEntityClass.getName();
    Assertions.assertTrue(standardEntityClass.isAssignableFrom(mockDescendantEntityClass),
        "class called '" + mockDescendantEntityClassName + "' is not descendant of '" + standardEntityClassName + "'");
    log.info("standard entity: {} , class: {}", standardEntity, standardEntityClassName);
    log.info("descendant entity: {} , class: {}", mockDescendantEntity, mockDescendantEntityClassName);
    Assertions.assertNotEquals(standardEntity, mockDescendantEntity);
    log.info("Mock descendant test passed.");
  }


}
