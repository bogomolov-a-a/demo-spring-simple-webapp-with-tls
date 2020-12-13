package org.artembogomolova.demo.webapp.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@TestMethodOrder(value = OrderAnnotation.class)
@TestExecutionListeners(value = {DependencyInjectionTestExecutionListener.class})
public abstract class AbstractTest {

  protected AbstractTest() {
    Class<? extends AbstractTest> testingClass = this.getClass();
    DisplayName classDisplayName = testingClass.getAnnotation(DisplayName.class);
    if (classDisplayName == null) {
      throw new IllegalStateException("Test class must be have " + DisplayName.class.getName() + " annotation with test description!");
    }
    List<Method> testMethodList = buildTestMethodList(testingClass);
    Assertions.assertFalse(testMethodList.isEmpty(), "test methods not found!");
    testMethodList.forEach(
        method ->
        {
          DisplayName methodDisplayName = method.getAnnotation(DisplayName.class);
          if (methodDisplayName == null) {
            throw new IllegalStateException("Test method must be have " + DisplayName.class.getName() + " annotation with test description!");
          }
        }
    );
  }

  private List<Method> buildTestMethodList(Class<? extends AbstractTest> testingClass) {
    List<Method> result = new ArrayList<>();
    result.addAll(MethodUtils.getMethodsListWithAnnotation(testingClass, Test.class, true, true));
    result.addAll(MethodUtils.getMethodsListWithAnnotation(testingClass, RepeatedTest.class, true, true));
    result.addAll(MethodUtils.getMethodsListWithAnnotation(testingClass, ParameterizedTest.class, true, true));
    result.addAll(MethodUtils.getMethodsListWithAnnotation(testingClass, TestFactory.class, true, true));
    return result;
  }

}
