package org.artembogomolova.demo.webapp.test.db

import org.artembogomolova.demo.webapp.main.config.RepositoryConfig
import org.artembogomolova.demo.webapp.test.AbstractClassTest
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [RepositoryConfig::class])
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = EmbeddedDatabaseConnection.NONE)
open class AbstractDatabaseTest<T> protected constructor(
    testingClass: Class<T>,
    classNameSuffix: String,
    displayNamePrefix: String
) : AbstractClassTest<T>(testingClass, classNameSuffix, displayNamePrefix)