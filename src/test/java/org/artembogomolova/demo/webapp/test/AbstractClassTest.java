package org.artembogomolova.demo.webapp.test;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;

public class AbstractClassTest<T> {

  @Getter
  private Class<T> testingClass;

  protected AbstractClassTest(Class<T> testingClass, String suffix) {
    this.testingClass = testingClass;
    String exceptedTestClassName = testingClass.getSimpleName() + suffix;
    String actualTestClassName = this.getClass().getSimpleName();
    Assertions.assertEquals(exceptedTestClassName, actualTestClassName, "wrong entity class pattern.");
  }
}
