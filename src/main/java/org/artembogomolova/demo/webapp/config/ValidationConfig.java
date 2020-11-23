package org.artembogomolova.demo.webapp.config;

import java.util.concurrent.TimeUnit;
import org.artembogomolova.demo.webapp.validation.base.ApplicationContextConstraintValidatorFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@ComponentScan("org.artembogomolova.demo.webapp.validation")
public class ValidationConfig implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames("classpath:/messages");
    messageSource.setUseCodeAsDefaultMessage(false);
    messageSource.setCacheSeconds((int) TimeUnit.HOURS.toSeconds(1));
    messageSource.setFallbackToSystemLocale(false);
    return messageSource;
  }

  @Bean()
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
    factoryBean.setValidationMessageSource(messageSource());
    factoryBean.setConstraintValidatorFactory(validationFactory());
    return factoryBean;
  }

  @Bean
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
    methodValidationPostProcessor.setValidator(validator());
    return methodValidationPostProcessor;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Bean
  public ApplicationContextConstraintValidatorFactory validationFactory() {
    return new ApplicationContextConstraintValidatorFactory(applicationContext);
  }

}
