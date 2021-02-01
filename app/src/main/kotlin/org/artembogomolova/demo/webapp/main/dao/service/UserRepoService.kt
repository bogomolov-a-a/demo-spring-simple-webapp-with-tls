package org.artembogomolova.demo.webapp.main.dao.service

import java.time.LocalDateTime
import java.time.ZoneOffset
import org.artembogomolova.demo.webapp.main.dao.repo.IAuthorityRepository
import org.artembogomolova.demo.webapp.main.dao.repo.IRoleRepository
import org.artembogomolova.demo.webapp.main.dao.repo.IUserRepository
import org.artembogomolova.demo.webapp.main.domain.auth.Authority
import org.artembogomolova.demo.webapp.main.domain.auth.PredefinedUserRole
import org.artembogomolova.demo.webapp.main.domain.auth.User
import org.artembogomolova.demo.webapp.main.domain.core.CountryCode
import org.artembogomolova.demo.webapp.main.domain.core.Person
import org.artembogomolova.demo.webapp.main.domain.core.PhysicalAddress
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UserRepoService @Autowired constructor(
    private val userRepository: IUserRepository,
    private val userRoleRepository: IRoleRepository,
    private val authorityRepository: IAuthorityRepository
) {


    fun createPredefinedSuperUser(passwordEncoder: PasswordEncoder) {
        val person = createPredefinedSuperUserPerson()
        val role = userRoleRepository.findByName(PredefinedUserRole.ADMIN.name) ?: throw IllegalStateException("role can't be null!")
        val result = User(
            login = PREDEFINED_ADMIN_ACCOUNT_LOGIN,
            password = passwordEncoder.encode(CHANGE_IT),
            clientCertificateData = CHANGE_IT,
            person = person,
            role = role
        )
        person.user = result
        println("created person: $person")
        role.users.add(result)
        println("super user role: $role")
        role.authorities.stream()
            .sorted(Comparator.comparing { obj: Authority -> obj.name!! })
            .forEach { x: Authority? -> println("authority enabled:  $x") }
        userRepository.save(result)
    }

    private fun createPredefinedSuperUserPerson(): Person = Person(
        name = CHANGE_IT,
        surname = CHANGE_IT,
        patronymic = CHANGE_IT,
        phone = EMPTY_PHONE,
        estateAddress = buildSuperUserAddress(),
        birthDate = LocalDateTime.now().toInstant(ZoneOffset.UTC).epochSecond
    )


    private fun buildSuperUserAddress(): PhysicalAddress = PhysicalAddress(
        countryCode = PREDEFINED_ADMIN_ACCOUNT_COUNTRY,
        postalCode = PREDEFINED_ACCOUNT_POSTAL_CODE,
        city = PREDEFINED_ADMIN_ACCOUNT_CITY_NAME,
        house = CHANGE_IT
    )

    fun createPredefinedGuestUser(passwordEncoder: PasswordEncoder) {
        val person = createPredefinedGuestPerson()
        val role = userRoleRepository.findByName(PredefinedUserRole.GUEST.name) ?: throw IllegalStateException("role can't be null!")
        val result = User(
            login = PREDEFINED_GUEST_ACCOUNT_LOGIN,
            password = passwordEncoder.encode(
                PREDEFINED_GUEST_ACCOUNT_LOGIN
            ),
            clientCertificateData = PREDEFINED_GUEST_ACCOUNT_LOGIN,
            role = role,
            person = person
        )
        println("created person: $person")
        println("super user role: $role")
        role.users.add(result)
        role.authorities.stream()
            .sorted(Comparator.comparing { obj: Authority -> obj.name!! })
            .forEach { x: Authority? -> println("authority enabled: $x") }
        userRepository.save(result)
    }

    private fun createPredefinedGuestPerson(): Person = Person(
        name = PREDEFINED_GUEST_ACCOUNT_LOGIN,
        surname = PREDEFINED_GUEST_ACCOUNT_LOGIN,
        patronymic = PREDEFINED_GUEST_ACCOUNT_LOGIN,
        phone = EMPTY_PHONE,
        estateAddress = buildGuestAddress(),
        birthDate = LocalDateTime.now().toInstant(ZoneOffset.UTC).epochSecond
    )


    private fun buildGuestAddress(): PhysicalAddress = PhysicalAddress(
        countryCode = PREDEFINED_GUEST_ACCOUNT_COUNTRY,
        postalCode = PREDEFINED_ACCOUNT_POSTAL_CODE,
        city = PREDEFINED_GUEST_ACCOUNT_LOGIN,
        house = PREDEFINED_GUEST_ACCOUNT_LOGIN
    )

    val isFirstStart: Boolean
        get() = userRepository.count() == 0L && userRoleRepository.count() == 0L && authorityRepository.count() == 0L
    val guestUserToken: Authentication
        get() {
            val user = userRepository.findByLogin(PREDEFINED_GUEST_ACCOUNT_LOGIN) ?: throw IllegalStateException("user can't be null!")
            val userLogin = user.login
            return AnonymousAuthenticationToken(userLogin, userLogin, user.role!!.authorities)
        }

    fun corruptedDatabase(): Boolean {
        return userRoleRepository.count() > 0 &&
                (userRepository.findByLogin(PREDEFINED_ADMIN_ACCOUNT_LOGIN) == null ||
                        userRepository.findByLogin(PREDEFINED_GUEST_ACCOUNT_LOGIN) == null)
    }

    companion object {
        const val PREDEFINED_ADMIN_ACCOUNT_LOGIN = "admin"
        private val PREDEFINED_ADMIN_ACCOUNT_COUNTRY = CountryCode.RU
        private val PREDEFINED_GUEST_ACCOUNT_COUNTRY = CountryCode.RU
        private const val PREDEFINED_ACCOUNT_POSTAL_CODE = "190000"
        private const val PREDEFINED_ADMIN_ACCOUNT_CITY_NAME = "Saint Petersburg"
        private const val CHANGE_IT = "changeit"
        const val PREDEFINED_GUEST_ACCOUNT_LOGIN = "guest"
        private const val EMPTY_PHONE = "+0-000-000-00-00"
    }
}