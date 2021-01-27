package org.artembogomolova.demo.webapp.main.config

import org.artembogomolova.demo.webapp.main.validation.ApplicationContextConstraintValidatorFactory
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor
import java.util.concurrent.TimeUnit


@Configuration
@ComponentScan("org.artembogomolova.demo.webapp.main.validation")
class ValidationConfig : ApplicationContextAware {
    private var applicationContext: ApplicationContext? = null

    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasenames("classpath:/messages")
        messageSource.setUseCodeAsDefaultMessage(false)
        messageSource.setCacheSeconds(TimeUnit.HOURS.toSeconds(1).toInt())
        messageSource.setFallbackToSystemLocale(false)
        return messageSource
    }

    @Bean
    fun validator(): LocalValidatorFactoryBean {
        val factoryBean = LocalValidatorFactoryBean()
        factoryBean.setValidationMessageSource(messageSource())
        factoryBean.constraintValidatorFactory = validationFactory()
        return factoryBean
    }

    @Bean
    fun methodValidationPostProcessor(): MethodValidationPostProcessor {
        val methodValidationPostProcessor = MethodValidationPostProcessor()
        methodValidationPostProcessor.setValidator(validator())
        return methodValidationPostProcessor
    }

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext?) {
        this.applicationContext = applicationContext
    }

    @Bean
    fun validationFactory(): ApplicationContextConstraintValidatorFactory {
        return ApplicationContextConstraintValidatorFactory(applicationContext!!)
    }
}
