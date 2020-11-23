package org.artembogomolova.demo.webapp.config;

import javax.validation.Validator;
import org.hibernate.FlushMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy
public class CustomHibernateValidatorConfiguration {

  @Autowired
  private Validator validator;

  @Bean
  @Lazy
  public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(final Validator validator) {
    return hibernateProperties -> {
      hibernateProperties.put("javax.persistence.validation.factory", validator);
      hibernateProperties.put("org.hibernate.flushMode", FlushMode.COMMIT.name());
    };
  }
}
