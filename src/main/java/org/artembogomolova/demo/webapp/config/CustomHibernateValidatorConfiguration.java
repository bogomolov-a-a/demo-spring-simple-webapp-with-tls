package org.artembogomolova.demo.webapp.config;

import javax.validation.Validator;
import org.hibernate.FlushMode;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy
public class CustomHibernateValidatorConfiguration {

  @Bean
  @Lazy
  public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(final Validator validator) {
    return hibernateProperties -> {
      hibernateProperties.put("javax.persistence.validation.factory", validator);
      /*flush only on committed transaction.*/
      hibernateProperties.put("org.hibernate.flushMode", FlushMode.COMMIT.name());
    };
  }
}
