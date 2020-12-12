package org.artembogomolova.demo.webapp.test.domain.entity;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.test.AbstractClassTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public abstract class AbstractAccessorEntityTest<T extends IdentifiedEntity> extends
    AbstractClassTest<T> {

  protected static final String ENTITY_CONSTRAINT_NOT_SUPPORTED = "Entity constraint %s not supported!";
  private static final String TEST_CLASS_SUFFIX = "EntityTest";
  private static final String TEST_CLASS_DISPLAY_NAME = "Entity test: ";
  private List<String> availableConstraintNames = getAvailableConstraintNames();

  protected AbstractAccessorEntityTest(Class<T> clazz) {
    super(clazz,
        TEST_CLASS_SUFFIX,
        TEST_CLASS_DISPLAY_NAME);

  }

  protected List<String> getAvailableConstraintNames() {
    return List.of(IdentifiedEntity.BASIC_CONSTRAINT_NAME);
  }

  @Test
  @DisplayName("Test entity for equal, hashcode and all accessor methods.")
  protected void entityTest() {
    log.info("entity test started.");
    transitiveEqualTest(buildStandardEntity());
    printEntityAsString(buildStandardEntity());
    fullDuplicateEntityEqualTest(buildStandardEntity());
    withAnotherEntityEqualTest(buildStandardEntity());
    uniqueConstrantEntityTest();
    log.info("entity test passed!");
  }

  private void uniqueConstrantEntityTest() {
    UniqueMultiColumn multiColumnConstraint = getTestingClass().getAnnotation(UniqueMultiColumn.class);
    if (multiColumnConstraint == null) {
      log.warn("clazz {} has no multi column constraint.", getTestingClass().getName());
      return;
    }
    Arrays.asList(multiColumnConstraint.constraints()).forEach(this::multiColumnConstraintTest);
  }

  private void multiColumnConstraintTest(UniqueMultiColumnConstraint constraint) {
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

  private void transitiveEqualTest(T standardEntity) {
    if (standardEntity == null) {
      throw new IllegalArgumentException("standardEntity must be not null!! wrong test case!!");
    }
    log.info("transitive equal test started");
    log.info("testing entity: {}", standardEntity);
    Assertions.assertEquals(standardEntity, standardEntity);
    Assertions.assertNotEquals(standardEntity, new Object());
    Assertions.assertNotEquals(standardEntity, null);
    Assertions.assertEquals(standardEntity.hashCode(), standardEntity.hashCode());
    log.info("transitive equal test passed");
  }

  private void printEntityAsString(T standardEntity) {
    log.info("standard entity: {}" ,standardEntity.toString());
  }

  protected abstract T buildStandardEntity();

  private void fullDuplicateEntityEqualTest(T standardEntity) {
    log.info("full duplicate test started. Only natural key fields!");
    T entity = buildDuplicateEntity(standardEntity);
    log.info("testing entity: {}", standardEntity);
    log.info("testing duplicate entity: {}", entity);
    Assertions.assertEquals(standardEntity, entity);
    Assertions.assertEquals(standardEntity.hashCode(), entity.hashCode());
    log.info("full duplicate test passed. ");
  }

  protected abstract T buildDuplicateEntity(T standardEntity);

  private void withAnotherEntityEqualTest(T standardEntity) {
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
}
