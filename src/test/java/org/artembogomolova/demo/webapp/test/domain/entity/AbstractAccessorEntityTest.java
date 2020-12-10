package org.artembogomolova.demo.webapp.test.domain.entity;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Slf4j
@TestMethodOrder(value = OrderAnnotation.class)
public abstract class AbstractAccessorEntityTest<T extends IdentifiedEntity> {

  private static final String TEST_CLASS_SUFFIX = "EntityTest";
  private Class<T> clazz;

  protected AbstractAccessorEntityTest(Class<T> clazz) {
    this.clazz = clazz;
    String exceptedTestClassName = clazz.getSimpleName() + TEST_CLASS_SUFFIX;
    String actualTestClassName = this.getClass().getSimpleName();
    Assertions.assertEquals(exceptedTestClassName, actualTestClassName, "wrong entity class pattern.");
  }

  @Test
  @DisplayName("Test entity for equal, hashcode and all accessor methods.")
  protected void entityTest() {
    transitiveEqualTest(buildStandardEntityAndAccessorTest());
    printEntityAsString(buildStandardEntityAndAccessorTest());
    fullDuplicateEntityEqualTest(buildStandardEntityAndAccessorTest());
    withAnotherEntityEqualTest(buildStandardEntityAndAccessorTest());
    UniqueMultiColumn multiColumnConstraint = clazz.getAnnotation(UniqueMultiColumn.class);
    if (multiColumnConstraint == null) {
      log.warn("clazz {} has no multi column constraint. test passed!", clazz.getName());
      return;
    }
    Arrays.asList(multiColumnConstraint.constraints()).forEach(
        constraint ->
        {
          String constraintName = constraint.name();
          log.info("testing constraint '{}'", constraintName);
          availableConstraint(constraintName);
          Arrays.asList(constraint.columnNames()).forEach(columnName -> {
                log.info("testing column name '{}'", columnName);
                withoutPartOfUniqueConstraintEqualTest(buildStandardEntityAndAccessorTest(), constraintName, columnName);
                log.info("column name '{}' test passed!", columnName);
              }
          );
          log.info("constraint '{}' test passed!", constraintName);
        });
    log.info("entity test passed!");

  }

  protected abstract void availableConstraint(String constraintName);

  private void transitiveEqualTest(T standardEntity) {
    if (standardEntity == null) {
      throw new IllegalArgumentException("standardEntity must be not null!! wrong test case!!");
    }
    Assertions.assertEquals(standardEntity, standardEntity);
    Assertions.assertNotEquals(standardEntity, new Object());
    Assertions.assertNotEquals(standardEntity, null);
    Assertions.assertEquals(standardEntity.hashCode(), standardEntity.hashCode());
  }

  private void printEntityAsString(T standardEntity) {
    log.info("standard entity: " + standardEntity.toString());
  }

  protected abstract T buildStandardEntityAndAccessorTest();

  private void fullDuplicateEntityEqualTest(T standardEntity) {
    T entity = buildDuplicateEntity(standardEntity);
    Assertions.assertEquals(standardEntity, entity);
    Assertions.assertEquals(standardEntity.hashCode(), entity.hashCode());
  }

  protected abstract T buildDuplicateEntity(T standardEntity);


  private void withAnotherEntityEqualTest(T standardEntity) {
    T action = buildAnotherEntityForTest();
    Assertions.assertNotEquals(standardEntity, action);
    Assertions.assertNotEquals(action, standardEntity);
    Assertions.assertNotEquals(standardEntity.hashCode(), action.hashCode());
  }

  protected abstract T buildAnotherEntityForTest();

  protected abstract void withoutPartOfUniqueConstraintEqualTest(T standardEntity, String constraintName, String columnName);
}
