package org.artembogomolova.demo.webapp.validation.base;

import java.lang.annotation.Annotation;
import javax.validation.ConstraintValidator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

public abstract class AbstractApplicationContextConstraintValidator<A extends Annotation, T>
    implements ConstraintValidator<A, T>,
    MessageSourceAware,
    ApplicationContextAware {

  protected MessageSource messageSource;
  protected ApplicationContext applicationContext;

  @Override
  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
