package org.artembogomolova.demo.webapp.main.validation

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.MessageSource
import org.springframework.context.MessageSourceAware
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory
import javax.validation.ConstraintValidator

abstract class AbstractApplicationContextConstraintValidator<A : Annotation?, T> : ConstraintValidator<A, T>, MessageSourceAware,
    ApplicationContextAware {
    protected lateinit var messageLocalSource: MessageSource
    protected lateinit var context: ApplicationContext
    override fun setMessageSource(messageSource: MessageSource) {
        messageLocalSource = messageSource
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

}

class ApplicationContextConstraintValidatorFactory(private val applicationContext: ApplicationContext) :
    SpringConstraintValidatorFactory(applicationContext.autowireCapableBeanFactory) {
    override fun <T : ConstraintValidator<*, *>?> getInstance(key: Class<T>): T {
        val result = super.getInstance(key)
        if (result is AbstractApplicationContextConstraintValidator<*, *>) {
            result.setApplicationContext(applicationContext)
        }
        return result
    }
}