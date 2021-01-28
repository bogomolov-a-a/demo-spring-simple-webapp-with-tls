package org.artembogomolova.demo.webapp.test.domain.auth

import org.artembogomolova.demo.webapp.main.domain.auth.Role
import org.artembogomolova.demo.webapp.main.domain.auth.User
import org.artembogomolova.demo.webapp.main.domain.auth.User_
import org.artembogomolova.demo.webapp.main.domain.core.Person
import org.artembogomolova.demo.webapp.test.domain.AbstractAccessorEntityTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

@DisplayName("Entity test: User")
internal class UserEntityTest : AbstractAccessorEntityTest<User>(
    User::class.java,
    User::from
) {
    override fun buildStandardEntity(): User = User(
        login = LOGIN_VALUE,
        password = PASSWORD_VALUE,
        clientCertificateData = CERTIFICATE_DATA_VALUE,
        avatar = AVATAR_VALUE,
        active = ACTIVE_VALUE,
        person = PERSON_VALUE,
        role = ROLE_VALUE
    )


    override fun containFieldCorrectValuesTest(standardEntity: User) {
        Assertions.assertEquals(LOGIN_VALUE, standardEntity.login)
        Assertions.assertEquals(PASSWORD_VALUE, standardEntity.password)
        Assertions.assertEquals(CERTIFICATE_DATA_VALUE, standardEntity.clientCertificateData)
        Assertions.assertEquals(AVATAR_VALUE, standardEntity.avatar)
        Assertions.assertEquals(ACTIVE_VALUE, standardEntity.active)
        Assertions.assertEquals(PERSON_VALUE, standardEntity.person)
        Assertions.assertEquals(ROLE_VALUE, standardEntity.role)
        Assertions.assertTrue(standardEntity.blockAuthorities!!.isEmpty())
    }

    override fun buildAnotherEntityForTest(): User {
        val result: User = buildStandardEntity()
        result.login = LOGIN_ANOTHER_VALUE
        return result
    }

    override fun withoutBasicConstraint(standardEntity: User, columnName: String): Boolean {
        if (User_.LOGIN == columnName) {
            withoutColumnEqualTest(standardEntity, User::login)
            return true
        }
        return false
    }

    /*protected override fun withoutAlternateConstraints(standardEntity: User, constraintName: String?, columnName: String?): Boolean {
        if (User.CERTIFICATE_DATA_CONSTRAINT_NAME.equals(constraintName)) {
            withoutColumnEqualTest<Any>(standardEntity, User::getClientCertificateData, User::setClientCertificateData)
            return true
        }
        return false
    }*/

    /*protected val availableConstraintNames: List<String>
        protected get() = java.util.List.of(
            IdentifiedEntity.BASIC_CONSTRAINT_NAME,
            User.CERTIFICATE_DATA_CONSTRAINT_NAME
        )
*/
    companion object {
        private const val LOGIN_VALUE = "test login"
        private const val PASSWORD_VALUE = "password"
        private const val CERTIFICATE_DATA_VALUE = "test certificate data"
        private const val AVATAR_VALUE = "avatar data"
        private const val ACTIVE_VALUE = true
        private val PERSON_VALUE: Person = Person()
        private val ROLE_VALUE: Role = Role()
        private const val LOGIN_ANOTHER_VALUE = "test login 2"
    }
}