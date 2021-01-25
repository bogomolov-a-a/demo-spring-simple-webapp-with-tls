package org.artembogomolova.demo.webapp.test

import org.apache.commons.lang3.reflect.MethodUtils
import org.artembogomolova.demo.webapp.main.util.getLogger
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.function.Consumer

@TestMethodOrder(value = MethodOrderer.OrderAnnotation::class)
@TestExecutionListeners(value = [DependencyInjectionTestExecutionListener::class])
open class AbstractTest protected constructor() {
    protected val log = getLogger(this::class.java)
    private fun buildTestMethodList(testingClass: Class<out AbstractTest?>): List<Method> {
        val result: MutableList<Method> = ArrayList()
        result.addAll(MethodUtils.getMethodsListWithAnnotation(testingClass, Test::class.java, true, true))
        result.addAll(MethodUtils.getMethodsListWithAnnotation(testingClass, RepeatedTest::class.java, true, true))
        result.addAll(MethodUtils.getMethodsListWithAnnotation(testingClass, ParameterizedTest::class.java, true, true))
        result.addAll(MethodUtils.getMethodsListWithAnnotation(testingClass, TestFactory::class.java, true, true))
        return result
    }

    init {
        val testingClass: Class<out AbstractTest?> = this.javaClass
        testingClass.getAnnotation(DisplayName::class.java)
            ?: throw IllegalStateException("Test class called '${testingClass.name}' must be have " + DisplayName::class.java.name + " annotation with test description!")
        val testMethodList = buildTestMethodList(testingClass)
        Assertions.assertFalse(testMethodList.isEmpty(), "test methods not found!")
        testMethodList.forEach(
            Consumer { method: Method ->
                method.getAnnotation(DisplayName::class.java)
                    ?: throw IllegalStateException("Test method called '${method.name}' must be have " + DisplayName::class.java.name + " annotation with test description!")
            }
        )
    }
}

open class AbstractClassTest<T> protected constructor(
    testingClass: Class<T>,
    classNameSuffix: String,
    displayNamePrefix: String
) : AbstractTest() {
    private val testingClass: Class<T>
    private fun checkTestDisplayNamePattern(testingClass: Class<T>, displayNamePrefix: String) {
        val displayName = this.javaClass.getAnnotation(DisplayName::class.java)
        val exceptedDisplayName = displayNamePrefix + testingClass.simpleName
        val actualDisplayName: String = displayName.value
        Assertions.assertEquals(exceptedDisplayName, actualDisplayName, WRONG_TEST_CLASS_DISPLAY_NAME)
    }

    private fun checkTestClassNamePattern(testingClass: Class<T>, classNameSuffix: String) {
        val exceptedTestClassName = testingClass.simpleName + classNameSuffix
        val actualTestClassName = this.javaClass.simpleName
        Assertions.assertEquals(exceptedTestClassName, actualTestClassName, WRONG_TEST_CLASS_NAME_PATTERN)
    }

    companion object {
        private const val WRONG_TEST_CLASS_NAME_PATTERN = "Wrong test class name pattern."
        private const val WRONG_TEST_CLASS_DISPLAY_NAME = "Wrong display name for test class."
    }

    init {
        checkTestClassNamePattern(testingClass, classNameSuffix)
        checkTestDisplayNamePattern(testingClass, displayNamePrefix)
        this.testingClass = testingClass
    }
}