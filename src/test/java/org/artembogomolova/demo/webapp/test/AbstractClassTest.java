package org.artembogomolova.demo.webapp.test;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class AbstractClassTest<T> extends AbstractTest {

  private static final String WRONG_TEST_CLASS_NAME_PATTERN = "Wrong test class name pattern.";
  private static final String WRONG_TEST_CLASS_DISPLAY_NAME = "Wrong display name for test class.";
  @Getter
  private Class<T> testingClass;

  protected AbstractClassTest(Class<T> testingClass,
      String classNameSuffix,
      String displayNamePrefix) {
    checkTestClassNamePattern(testingClass, classNameSuffix);
    checkTestDisplayNamePattern(testingClass, displayNamePrefix);
    this.testingClass = testingClass;
  }

  private void checkTestDisplayNamePattern(Class<T> testingClass, String displayNamePrefix) {
    DisplayName displayName = this.getClass().getAnnotation(DisplayName.class);
    String exceptedDisplayName = displayNamePrefix + testingClass.getSimpleName();
    String actualDisplayName = displayName.value();
    Assertions.assertEquals(exceptedDisplayName, actualDisplayName, WRONG_TEST_CLASS_DISPLAY_NAME);
  }

  private void checkTestClassNamePattern(Class<T> testingClass, String classNameSuffix) {
    String exceptedTestClassName = testingClass.getSimpleName() + classNameSuffix;
    String actualTestClassName = this.getClass().getSimpleName();
    Assertions.assertEquals(exceptedTestClassName, actualTestClassName, WRONG_TEST_CLASS_NAME_PATTERN);
  }
}
