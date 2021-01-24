package org.artembogomolova.demo.webapp.main.dao.service

import org.artembogomolova.demo.webapp.main.dao.repo.IAuthorityRepository
import org.artembogomolova.demo.webapp.main.dao.repo.IRoleRepository
import org.artembogomolova.demo.webapp.main.dao.repo.IUserRepository
import org.artembogomolova.demo.webapp.main.domain.auth.Authority
import org.artembogomolova.demo.webapp.main.domain.auth.PredefinedUserRole
import org.artembogomolova.demo.webapp.main.domain.auth.User
import org.artembogomolova.demo.webapp.main.domain.core.Person
import org.artembogomolova.demo.webapp.main.domain.core.PhysicalAddress
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Component
@Transactional
class UserRepoService {
    @Autowired
    lateinit var userRepository: IUserRepository

    @Autowired
    lateinit var userRoleRepository: IRoleRepository

    @Autowired
    lateinit var authorityRepository: IAuthorityRepository

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
        println("created person: $person")
        role.users.add(result)
        println("super user role: $role")
        role.authorities.stream()
            .sorted(Comparator.comparing { obj: Authority -> obj.name })
            .forEach { x: Authority? -> println("authority enabled:  $x") }
        userRepository.save(result)
    }

    private fun createPredefinedSuperUserPerson(): Person {
        val result = Person(
            name = CHANGE_IT,
            surname = CHANGE_IT,
            patronymic = CHANGE_IT,
            phone = CHANGE_IT,
            estateAddress = buildSuperUserAddress()
        )
        result.birthDate = Date(LocalDateTime.now().toInstant(ZoneOffset.UTC).epochSecond)
        return result
    }

    private fun buildSuperUserAddress(): PhysicalAddress = PhysicalAddress(
        country = PREDEFINED_ADMIN_ACCOUNT_COUNTRY,
        postalCode = PREDEFINED_ADMIN_ACCOUNT_POSTAL_CODE,
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
            .sorted(Comparator.comparing { obj: Authority -> obj.name })
            .forEach { x: Authority? -> println("authority enabled: $x") }
        userRepository.save(result)
    }

    private fun createPredefinedGuestPerson(): Person {
        val result = Person(
            name = PREDEFINED_GUEST_ACCOUNT_LOGIN,
            surname = PREDEFINED_GUEST_ACCOUNT_LOGIN,
            patronymic = PREDEFINED_GUEST_ACCOUNT_LOGIN,
            phone = PREDEFINED_GUEST_ACCOUNT_LOGIN,
            estateAddress = buildGuestAddress()
        )
        result.birthDate = Date(LocalDateTime.now().toInstant(ZoneOffset.UTC).epochSecond)
        return result
    }


    private fun buildGuestAddress(): PhysicalAddress = PhysicalAddress(
        country = PREDEFINED_GUEST_ACCOUNT_LOGIN,
        postalCode = PREDEFINED_GUEST_ACCOUNT_LOGIN,
        city = PREDEFINED_GUEST_ACCOUNT_LOGIN,
        house = PREDEFINED_GUEST_ACCOUNT_LOGIN
    )

    val isFirstStart: Boolean
        get() = userRepository.count() == 0L && userRoleRepository.count() == 0L && authorityRepository.count() == 0L
    val guestUserToken: Authentication
        get() {
            val user = userRepository.findByLogin(PREDEFINED_GUEST_ACCOUNT_LOGIN) ?: throw IllegalStateException("user can't be null!")
            val userLogin = user.login
            return AnonymousAuthenticationToken(userLogin, userLogin, user.role.authorities)
        }

    companion object {
        private const val PREDEFINED_ADMIN_ACCOUNT_LOGIN = "admin"
        private const val PREDEFINED_ADMIN_ACCOUNT_COUNTRY = "Russia"
        private const val PREDEFINED_ADMIN_ACCOUNT_POSTAL_CODE = "190000"
        private const val PREDEFINED_ADMIN_ACCOUNT_CITY_NAME = "Saint Petersburg"
        private const val CHANGE_IT = "changeit"
        private const val PREDEFINED_GUEST_ACCOUNT_LOGIN = "guest"
    }
}