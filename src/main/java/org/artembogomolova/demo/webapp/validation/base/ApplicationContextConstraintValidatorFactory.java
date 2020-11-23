package org.artembogomolova.demo.webapp.validation.base;

import javax.validation.ConstraintValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

public class ApplicationContextConstraintValidatorFactory extends SpringConstraintValidatorFactory {

  private ApplicationContext applicationContext;

  public ApplicationContextConstraintValidatorFactory(ApplicationContext applicationContext) {
    super(applicationContext.getAutowireCapableBeanFactory());
    this.applicationContext = applicationContext;
  }

  @Override
  public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
    T result = super.getInstance(key);
    if (result instanceof AbstractApplicationContextConstraintValidator) {
      ((AbstractApplicationContextConstraintValidator) result).setApplicationContext(applicationContext);
    }
    return result;
  }

}
