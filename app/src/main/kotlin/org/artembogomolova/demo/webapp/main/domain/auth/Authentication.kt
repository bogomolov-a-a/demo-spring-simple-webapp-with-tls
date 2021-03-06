package org.artembogomolova.demo.webapp.main.domain.auth

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import kotlin.reflect.KMutableProperty1
import org.artembogomolova.demo.webapp.main.dao.repo.IAuthorityRepository
import org.artembogomolova.demo.webapp.main.dao.repo.IRoleRepository
import org.artembogomolova.demo.webapp.main.dao.repo.IUserRepository
import org.artembogomolova.demo.webapp.main.domain.core.IdentifiedEntity
import org.artembogomolova.demo.webapp.main.domain.core.Person
import org.artembogomolova.demo.webapp.main.validation.UniqueMultiColumn
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "authorities")
/*Authority has no natural key for db yet, it role_id and authority.name */
class Authority(
    @field:NotBlank
    @Column(nullable = false)
    var name: String?,
    @Column(nullable = false)
    var description: String?,
    @ManyToMany(mappedBy = "authorities")
    var roles: MutableList<Role> = mutableListOf(),
    @ManyToMany(mappedBy = "blockAuthorities")
    var users: MutableList<User> = mutableListOf(),
) : IdentifiedEntity<Authority>(), GrantedAuthority {

    constructor() : this(null, null)

    override fun buildNaturalKey(): Array<KMutableProperty1<Authority, *>> = arrayOf(Authority::name)

    override fun getAuthority(): String {
        return name!!
    }

    companion object {
        private const val serialVersionUID = 1L
        fun from(copyingEntity: Authority): Authority = Authority(
            name = copyingEntity.name,
            description = copyingEntity.description
        )
    }
}

@Entity
@Table(name = "roles")
@UniqueMultiColumn(
    repository = IRoleRepository::class,
    constraints = [UniqueMultiColumn.UniqueMultiColumnConstraint(
        name = IdentifiedEntity.NATURAL_KEY_CONSTRAINT_NAME,
        columnNames = [Role_.NAME]
    )]
)
class Role(
    @field:NotBlank
    @Column(nullable = false)
    var name: String?,
    @field:NotBlank
    @Column(nullable = false)
    var description: String?,
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "role_authorities",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id", columnDefinition = "bigint")],
        inverseJoinColumns = [JoinColumn(name = "authority_id", referencedColumnName = "id", columnDefinition = "bigint")]
    )
    val authorities: MutableList<Authority> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "role")
    val users: MutableList<User> = mutableListOf(),
) : IdentifiedEntity<Role>() {

    constructor() : this(null, null)

    override fun buildNaturalKey(): Array<KMutableProperty1<Role, *>> = arrayOf(Role::name)

    companion object {
        private const val serialVersionUID = 1L
        fun from(predefinedUserRole: PredefinedUserRole): Role {
            val result = Role(predefinedUserRole.name, predefinedUserRole.description)
            result.id = predefinedUserRole.id
            result.authorities.addAll(predefinedUserRole.privileges)
            return result
        }

        fun from(copyingEntity: Role): Role = Role(
            name = copyingEntity.name,
            description = copyingEntity.description
        )
    }
}

@Entity
@Table(name = "users")
@UniqueMultiColumn(
    repository = IUserRepository::class,
    constraints = [UniqueMultiColumn.UniqueMultiColumnConstraint(
        name = IdentifiedEntity.NATURAL_KEY_CONSTRAINT_NAME,
        /*for base yet only login as natural key*/
        columnNames = [User_.LOGIN]
    )]
)
class User(
    @field:NotBlank
    @Column(nullable = false)
    var login: String?,
    @field:NotBlank
    @Column(nullable = false)
    var password: String?,
    @field:NotBlank
    @Column(nullable = false)
    var clientCertificateData: String?,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "role_id", columnDefinition = BIGINT_DEF, nullable = false)
    var role: Role? = null,
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "person_id", columnDefinition = BIGINT_DEF, nullable = false)
    var person: Person? = null,
    @field:NotNull
    @Column(insertable = false, nullable = false)
    var active: Boolean = true,
    var avatar: String? = null,
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "block_authorities",
        joinColumns = [JoinColumn(name = "user_id", columnDefinition = BIGINT_DEF)],
        inverseJoinColumns = [JoinColumn(name = "authority_id", columnDefinition = BIGINT_DEF)]
    )
    val blockAuthorities: MutableList<Authority>? = mutableListOf(),
) : IdentifiedEntity<User>() {
    constructor() : this(null, null, null)

    override fun buildNaturalKey(): Array<KMutableProperty1<User, *>> = arrayOf(
        User::login,
        User::person
    )

    companion object {
        private const val BIGINT_DEF = "bigint"
        private const val serialVersionUID = 1L
        fun from(copyingEntity: User): User = User(
            login = copyingEntity.login,
            person = copyingEntity.person,
            password = copyingEntity.password,
            active = copyingEntity.active,
            clientCertificateData = copyingEntity.clientCertificateData,
            avatar = copyingEntity.avatar
        )
    }
}