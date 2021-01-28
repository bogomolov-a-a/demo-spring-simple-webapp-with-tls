package org.artembogomolova.demo.webapp.test.db.dao.auth

import org.artembogomolova.demo.webapp.main.dao.repo.IUserRepository
import org.artembogomolova.demo.webapp.main.domain.auth.Authority
import org.artembogomolova.demo.webapp.main.domain.auth.Role
import org.artembogomolova.demo.webapp.main.domain.auth.User
import org.artembogomolova.demo.webapp.main.validation.UniqueMultiColumn
import org.artembogomolova.demo.webapp.test.db.dao.AbstractDaoTest
import org.artembogomolova.demo.webapp.test.db.dao.DomainTestUtil
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository

@DisplayName("Dao test: User")
internal class UserDaoTest : AbstractDaoTest<User>(
    User::class.java,
    ::User
) {
    @Autowired
    private lateinit var userRepository: IUserRepository

    override fun updateEntities(savedCollection: List<User>): List<User> {
        savedCollection[0].password = USER_ANOTHER_PASSWORD
        return savedCollection
    }

    override fun crudRepository(): CrudRepository<User, Long> = userRepository

    override fun generateEntities(): List<User> = listOf(buildUser())

    override fun buildCommonFieldValues(uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint): Map<String, Any?>? = null

    override fun doPrepareDeniedTestEntity(uniqueMultiColumnConstraint: UniqueMultiColumn.UniqueMultiColumnConstraint, commonValues: Map<String, Any?>): User =
        buildUser()

    override fun doDuplicateDeniedTestEntity(columns: UniqueMultiColumn.UniqueMultiColumnConstraint, commonValues: Map<String, Any?>): User = buildUser()

    private fun buildUser(): User {
        val role = Role(
            name = ROLE_NAME_VALUE,
            description = ROLE_DESCRIPTION_VALUE
        )
        role.authorities.add(
            Authority(
                name = AUTHORITY_NAME_VALUE,
                description = AUTHORITY_DESCRIPTION_VALUE
            )
        )
        return User(
            login = USER_LOGIN,
            password = USER_PASSWORD,
            person = DomainTestUtil.buildPerson(),
            role = role
        )
    }

    companion object {
        private const val USER_LOGIN = "user"
        private const val USER_PASSWORD = "pass"
        private const val USER_ANOTHER_PASSWORD = "pa$$"
        private const val ROLE_NAME_VALUE = "ROLE"
        private const val ROLE_DESCRIPTION_VALUE = "test role description for user"
        private const val AUTHORITY_NAME_VALUE = "person:test_get"
        private const val AUTHORITY_DESCRIPTION_VALUE = "test authority user person info get"
    }
}