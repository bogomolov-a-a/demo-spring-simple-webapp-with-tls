package org.artembogomolova.demo.webapp.main.config.db

import org.hibernate.FlushMode
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import javax.validation.Validator

@Configuration
@Lazy
class CustomHibernateValidatorConfiguration {
    @Bean
    @Lazy
    fun hibernatePropertiesCustomizer(validator: Validator?): HibernatePropertiesCustomizer {
        return HibernatePropertiesCustomizer { hibernateProperties: MutableMap<String?, Any?> ->
            hibernateProperties["javax.persistence.validation.factory"] = validator
            /*flush only on committed transaction.*/hibernateProperties["org.hibernate.flushMode"] = FlushMode.COMMIT.name
        }
    }
}