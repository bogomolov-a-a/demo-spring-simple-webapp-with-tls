package org.artembogomolova.demo.webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.artembogomolova.demo.webapp.dao")
public class RepositoryConfig {

}
