package org.artembogomolova.demo.webapp.test.domain.auth

import org.artembogomolova.demo.webapp.main.domain.auth.Authority
import org.artembogomolova.demo.webapp.main.domain.auth.Authority_
import org.artembogomolova.demo.webapp.test.domain.AbstractAccessorEntityTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

@DisplayName("Entity test: Authority")
internal class AuthorityEntityTest : AbstractAccessorEntityTest<Authority>(
    Authority::class.java,
    Authority::from
) {
    override fun buildStandardEntity(): Authority = Authority(NAME_VALUE, DESCRIPTION_VALUE)

    override fun containFieldCorrectValuesTest(standardEntity: Authority) {
        Assertions.assertEquals(NAME_VALUE, standardEntity.name)
        Assertions.assertEquals(NAME_VALUE, standardEntity.authority)
        Assertions.assertTrue(standardEntity.roles.isEmpty())
        Assertions.assertTrue(standardEntity.users.isEmpty())
        Assertions.assertEquals(DESCRIPTION_VALUE, standardEntity.description)
    }

    override fun buildAnotherEntityForTest(): Authority = Authority(NAME_ANOTHER_VALUE, DESCRIPTION_VALUE)

    override fun withoutBasicConstraint(standardEntity: Authority, columnName: String): Boolean {
        if (Authority_.NAME == columnName) {
            withoutColumnEqualTest(standardEntity, Authority::name)
            return true
        }
        return false
    }


    companion object {
        private const val NAME_VALUE = "test:authority"
        private const val DESCRIPTION_VALUE = "test description"
        private const val NAME_ANOTHER_VALUE = "test:authority_another"
    }
}


