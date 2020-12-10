package org.artembogomolova.demo.webapp.test;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;

@TestMethodOrder(value = OrderAnnotation.class)
public abstract class AbstractTest {

  protected AbstractTest() {
    Class<? extends AbstractTest> testingClass = this.getClass();
    DisplayName classDisplayName = testingClass.getAnnotation(DisplayName.class);
    if (classDisplayName == null) {
      throw new IllegalStateException("Test class must be have " + DisplayName.class.getName() + " annotation with test description!");
    }
    Arrays.stream(testingClass.getMethods()).filter(this::testMethodAvailable).forEach(
        method ->
        {
          DisplayName methodDisplayName = method.getAnnotation(DisplayName.class);
          if (methodDisplayName == null) {
            throw new IllegalStateException("Test method must be have " + DisplayName.class.getName() + " annotation with test description!");
          }
        }
    );
  }

  private boolean testMethodAvailable(Method method) {
    return method.isAnnotationPresent(Test.class) ||
        method.isAnnotationPresent(RepeatedTest.class) ||
        method.isAnnotationPresent(ParameterizedTest.class) ||
        method.isAnnotationPresent(TestFactory.class);
  }
}
