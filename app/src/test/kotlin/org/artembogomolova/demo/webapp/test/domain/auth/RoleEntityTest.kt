package org.artembogomolova.demo.webapp.test.domain.auth

import org.artembogomolova.demo.webapp.main.domain.auth.Role
import org.artembogomolova.demo.webapp.main.domain.auth.Role_
import org.artembogomolova.demo.webapp.test.domain.AbstractAccessorEntityTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

@DisplayName("Entity test: Role")
internal class RoleEntityTest :
    AbstractAccessorEntityTest<Role>(
        Role::class.java,
        Role::from
    ) {
    override fun buildStandardEntity(): Role = Role(NAME_VALUE, DESCRIPTION_VALUE)

    override fun containFieldCorrectValuesTest(standardEntity: Role) {
        Assertions.assertEquals(NAME_VALUE, standardEntity.name)
        Assertions.assertTrue(standardEntity.authorities.isEmpty())
        Assertions.assertTrue(standardEntity.users.isEmpty())
    }

    override fun buildAnotherEntityForTest(): Role = Role(NAME_ANOTHER_VALUE, DESCRIPTION_VALUE)

    override fun withoutBasicConstraint(standardEntity: Role, columnName: String): Boolean {
        if (Role_.NAME == columnName) {
            withoutColumnEqualTest(standardEntity, Role::name)
            return true
        }
        return false
    }

    companion object {
        private const val NAME_VALUE = "test role"
        private const val DESCRIPTION_VALUE = "test role description"
        private const val NAME_ANOTHER_VALUE = "test another role"
    }
}