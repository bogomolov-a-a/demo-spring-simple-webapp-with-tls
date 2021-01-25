package org.artembogomolova.demo.webapp.main.domain.auth

import org.artembogomolova.demo.webapp.main.domain.core.IdentifiedEntity
import org.artembogomolova.demo.webapp.main.domain.core.Person
import org.springframework.lang.NonNull
import org.springframework.security.core.GrantedAuthority
import java.util.stream.Collectors
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

@Entity
@Table(name = "authorities")
class Authority(
    @Column(nullable = false)
    val name: String,
    @ManyToMany(mappedBy = "authorities")
    var roles: MutableList<Role>? = mutableListOf(),
    @ManyToMany(mappedBy = "blockAuthorities")
    val users: MutableList<User>? = mutableListOf()
) : IdentifiedEntity(), GrantedAuthority {

    override fun getAuthority(): String {
        return name
    }

    override fun toString(): String = name

    companion object {
        private const val serialVersionUID = 1L
    }
}

@Entity
@Table(name = "roles")
class Role(
    @Column(nullable = false)
    val name: String,
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "role_authorities",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id", columnDefinition = "bigint")],
        inverseJoinColumns = [JoinColumn(name = "authority_id", referencedColumnName = "id", columnDefinition = "bigint")]
    )
    var authorities: MutableList<Authority>? = mutableListOf(),
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "role")
    @NonNull
    val users: MutableList<User>? = mutableListOf()
) : IdentifiedEntity() {

    constructor(predefinedUserRole: PredefinedUserRole) : this(predefinedUserRole.name) {
        id = predefinedUserRole.id
        authorities!!.addAll(predefinedUserRole.privileges.stream().map { name: String -> Authority(name) }.collect(Collectors.toList()))
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false)
    val login: String,
    @Column(nullable = false)
    val password: String,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "role_id", columnDefinition = BIGINT_DEF, nullable = false)
    val role: Role,
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "person_id", columnDefinition = BIGINT_DEF, nullable = false)
    val person: Person? = null,
    @Column(insertable = false, nullable = false)
    var active: Boolean = true,
    var clientCertificateData: String? = null,
    var avatar: String? = null,
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "block_authorities",
        joinColumns = [JoinColumn(name = "user_id", columnDefinition = BIGINT_DEF)],
        inverseJoinColumns = [JoinColumn(name = "authority_id", columnDefinition = BIGINT_DEF)]
    )
    val blockAuthorities: MutableList<Authority>? = mutableListOf()
) : IdentifiedEntity() {

    companion object {
        private const val BIGINT_DEF = "bigint"
        private const val serialVersionUID = 1L
    }
}