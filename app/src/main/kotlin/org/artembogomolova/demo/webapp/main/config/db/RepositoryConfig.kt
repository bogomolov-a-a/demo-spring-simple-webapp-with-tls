package org.artembogomolova.demo.webapp.main.config.db

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["org.artembogomolova.demo.webapp.main.dao.repo"])
@EntityScan(basePackages = ["org.artembogomolova.demo.webapp.main.domain"])
class RepositoryConfig 