package org.artembogomolova.demo.webapp.test.db.dao.auth

import org.artembogomolova.demo.webapp.main.dao.repo.IAuthorityRepository
import org.artembogomolova.demo.webapp.main.domain.auth.Authority
import org.artembogomolova.demo.webapp.main.domain.auth.Authority_
import org.artembogomolova.demo.webapp.main.domain.auth.Role
import org.artembogomolova.demo.webapp.main.domain.auth.User
import org.artembogomolova.demo.webapp.main.validation.UniqueMultiColumn
import org.artembogomolova.demo.webapp.test.config.AuthorityDaoTestDatasourceConfiguration
import org.artembogomolova.demo.webapp.test.db.dao.AbstractDaoTest
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.repository.CrudRepository

@Import(AuthorityDaoTestDatasourceConfiguration::class)
@DisplayName("Dao test: Authority")
internal class AuthorityDaoTest : AbstractDaoTest<Authority>(
    Authority::class.java,
    ::Authority
) {
    companion object {
        private const val NAME_VALUE = "test:authority"
        private const val DESCRIPTION_VALUE = "test description"
        private const val DESCRIPTION_ANOTHER_VALUE = "another description"


    }

    @Autowired
    private lateinit var authorityRepository: IAuthorityRepository
    override fun updateEntities(savedCollection: List<Authority>): List<Authority> {
        savedCollection[0].description = DESCRIPTION_ANOTHER_VALUE
        return savedCollection
    }

    override fun crudRepository(): CrudRepository<Authority, Long> = authorityRepository

    override fun generateEntities(): List<Authority> {
        return mutableListOf(buildAuthority())
    }

    private fun buildAuthority(): Authority {
        val result = Authority()
        result.name = NAME_VALUE
        result.description = DESCRIPTION_VALUE
        return result
    }

    override fun buildCommonFieldValues(uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint): Map<String, Any> {
        val result: MutableMap<String, Any> = HashMap()
        result[Authority_.ROLES] = mutableListOf<Role>()
        result[Authority_.USERS] = mutableListOf<User>()
        return result
    }

    override fun doPrepareDeniedTestEntity(
        uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint,
        commonValues: Map<String, Any?>
    ): Authority {
        return buildAuthority(commonValues)
    }

    override fun doDuplicateDeniedTestEntity(columns: UniqueMultiColumn.UniqueMultiColumnConstraint, commonValues: Map<String, Any?>): Authority {
        return buildAuthority(commonValues)
    }

    private fun buildAuthority(commonValues: Map<String, Any?>): Authority {
        val result = buildAuthority()
        @Suppress("UNCHECKED_CAST")
        result.roles = commonValues[Authority_.ROLES] as MutableList<Role>
        @Suppress("UNCHECKED_CAST")
        result.users = commonValues[Authority_.USERS] as MutableList<User>
        return result
    }

    override fun buildEntityWithoutViolationEntity(): Authority {
        return buildAuthority()
    }

}