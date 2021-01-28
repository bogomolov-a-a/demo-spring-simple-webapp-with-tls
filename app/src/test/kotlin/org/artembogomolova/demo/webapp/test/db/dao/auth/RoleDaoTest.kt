package org.artembogomolova.demo.webapp.test.db.dao.auth

import org.artembogomolova.demo.webapp.main.dao.repo.IRoleRepository
import org.artembogomolova.demo.webapp.main.domain.auth.Authority
import org.artembogomolova.demo.webapp.main.domain.auth.Role
import org.artembogomolova.demo.webapp.main.validation.UniqueMultiColumn
import org.artembogomolova.demo.webapp.test.db.dao.AbstractDaoTest
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository

@DisplayName("Dao test: Role")
internal class RoleDaoTest : AbstractDaoTest<Role>(Role::class.java, ::Role) {

    @Autowired
    private lateinit var roleRepository: IRoleRepository

    override fun updateEntities(savedCollection: List<Role>): List<Role> {
        savedCollection[0].description = ROLE_DESCRIPTION_ANOTHER_VALUE
        return savedCollection
    }

    override fun crudRepository(): CrudRepository<Role, Long> = roleRepository

    override fun generateEntities(): List<Role> {
        return mutableListOf(buildRole())
    }

    override fun buildCommonFieldValues(uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint): Map<String, Any>? = null

    override fun doPrepareDeniedTestEntity(uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint, commonValues: Map<String, Any?>): Role {
        return buildRole()
    }

    private fun buildRole(): Role {
        val result = Role(
            name = ROLE_NAME_VALUE,
            description = ROLE_DESCRIPTION_VALUE
        )
        result.authorities.add(buildAuthority())
        return result
    }

    private fun buildAuthority(): Authority = Authority(
        name = AUTHORITY_NAME_VALUE,
        description = AUTHORITY_DESCRIPTION_VALUE
    )

    override fun doDuplicateDeniedTestEntity(columns: UniqueMultiColumn.UniqueMultiColumnConstraint, commonValues: Map<String, Any?>): Role {
        return buildRole()
    }

    companion object {
        private const val ROLE_NAME_VALUE = "ROLE"
        private const val ROLE_DESCRIPTION_VALUE = "test description"
        private const val ROLE_DESCRIPTION_ANOTHER_VALUE = "test:authority_another"
        private const val AUTHORITY_NAME_VALUE = "test:get"
        private const val AUTHORITY_DESCRIPTION_VALUE = "test authority description"
    }
}