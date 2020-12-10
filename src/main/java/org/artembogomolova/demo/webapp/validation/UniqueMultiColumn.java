package org.artembogomolova.demo.webapp.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UniqueMultiColumnConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueMultiColumn {

  String VIOLATION_MESSAGE_TEMPLATE = "org.artembogomolova.demo.webapp.dao.validation.unique";

  String message() default VIOLATION_MESSAGE_TEMPLATE;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  UniqueMultiColumnConstraint[] constraints();

  Class<?> repository();


  @interface UniqueMultiColumnConstraint {

    String name();

    String[] value();
  }
}
