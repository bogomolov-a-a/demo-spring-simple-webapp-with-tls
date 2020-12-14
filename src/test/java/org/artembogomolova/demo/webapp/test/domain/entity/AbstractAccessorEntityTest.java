package org.artembogomolova.demo.webapp.test.domain.entity;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
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

  protected AbstractAccessorEntityTest(Class<T> clazz) {
    super(clazz,
        TEST_CLASS_SUFFIX,
        TEST_CLASS_DISPLAY_NAME);
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
    T duplicateEntity = buildDuplicateEntity(standardEntity);
    log.info("testing entity: {}", standardEntity);
    log.info("testing duplicate entity: {}", duplicateEntity);
    Assertions.assertEquals(standardEntity, duplicateEntity);
    Assertions.assertEquals(standardEntity.hashCode(), duplicateEntity.hashCode());
    log.info("full duplicate test passed. ");
  }

  protected abstract T buildDuplicateEntity(T standardEntity);

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

  protected abstract void withoutPartOfUniqueConstraintEqualTest(T standardEntity, String constraintName, String columnName);

  protected final <U> void withoutColumnEqualTest(T entity,
      Function<T, U> getter,
      BiConsumer<T, U> setter) {
    T duplicateEntity = buildDuplicateEntity(entity);
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

  private <U> void setNullFieldValueAndEquals(T duplicateEntity,
      T entity,
      BiConsumer<T, U> setter) {
    setter.accept(duplicateEntity, null);
    Assertions.assertEquals(entity, duplicateEntity);
    Assertions.assertEquals(duplicateEntity, entity);
    Assertions.assertEquals(entity.hashCode(), duplicateEntity.hashCode());
  }

  private <U> void exchangeDupStandardFieldNullValueAndNotEquals(T duplicateEntity,
      T entity,
      Function<T, U> getter,
      BiConsumer<T, U> setter) {
    setter.accept(duplicateEntity, getter.apply(entity));
    setter.accept(entity, null);
    Assertions.assertNotEquals(entity, duplicateEntity);
    Assertions.assertNotEquals(duplicateEntity, entity);
    Assertions.assertNotEquals(entity.hashCode(), duplicateEntity.hashCode());
  }

  private <U> void setNullFieldValueAndNotEquals(T duplicateEntity,
      T entity,
      BiConsumer<T, U> setter) {
    setter.accept(duplicateEntity, null);
    Assertions.assertNotEquals(entity, duplicateEntity);
    Assertions.assertNotEquals(duplicateEntity, entity);
    Assertions.assertNotEquals(entity.hashCode(), duplicateEntity.hashCode());
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
      + "Descendant class must be fake(with copy constructor)")
  void descendantClassEqualTest() {
    Function<T, ? extends T> fakeDescendantClassConstructor = getFakeDescendantClassConstructor();
    T fakeDescendantEntity = fakeDescendantClassConstructor.apply(standardEntity);
    Assertions.assertNotEquals(standardEntity, fakeDescendantEntity);
  }

  protected abstract Function<T, ? extends T> getFakeDescendantClassConstructor();
}
