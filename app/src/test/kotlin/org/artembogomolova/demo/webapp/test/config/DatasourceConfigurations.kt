package org.artembogomolova.demo.webapp.test.config

import javax.sql.DataSource
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DriverManagerDataSource

abstract class BasicDatasourceConfiguration {
    @Bean
    @Primary
    open fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.url = "jdbc:sqlite:build/${this::class.java.simpleName}.db"
        return dataSource
    }
}
/*Context*/
@TestConfiguration
class TestWithoutConditionDatasourceConfiguration : BasicDatasourceConfiguration()

@TestConfiguration
class RerunApplicationContextTestDatasourceConfiguration : BasicDatasourceConfiguration()

@TestConfiguration
class UserRepoServiceCorrectTestDatasourceConfiguration : BasicDatasourceConfiguration()

@TestConfiguration
class HttpsProtocolExchangeOnTestConfiguration : BasicDatasourceConfiguration()
/*Entity*/
@TestConfiguration
class PersonDaoTestDatasourceConfiguration : BasicDatasourceConfiguration()

@TestConfiguration
class PhysicalAddressDaoTestDatasourceConfiguration : BasicDatasourceConfiguration()

@TestConfiguration
class AuthorityDaoTestDatasourceConfiguration : BasicDatasourceConfiguration()

@TestConfiguration
class RoleDaoTestConfiguration : BasicDatasourceConfiguration()

@TestConfiguration
class UserDaoTestConfiguration : BasicDatasourceConfiguration()