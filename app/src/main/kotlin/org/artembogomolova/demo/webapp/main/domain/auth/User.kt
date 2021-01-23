package org.artembogomolova.demo.webapp.main.domain.auth

import org.artembogomolova.demo.webapp.main.domain.IdentifiedEntity
import org.artembogomolova.demo.webapp.main.domain.core.Person
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    var login: String? = null,
    var password: String? = null,
    var clientCertificateData: String? = null,
    var avatar: String? = null,

    @Column(insertable = false)
    var active: Boolean = true,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE])
    @JoinTable(
        name = "block_authorities",
        joinColumns = [JoinColumn(name = "user_id", columnDefinition = BIGINT_DEF)],
        inverseJoinColumns = [JoinColumn(name = "authority_id", columnDefinition = BIGINT_DEF)]
    )
    var blockAuthorities: MutableList<Authority> = ArrayList(),

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE])
    @JoinColumn(name = "role_id", columnDefinition = BIGINT_DEF)
    var role: Role? = null,

    @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    @JoinColumn(name = "person_id", columnDefinition = BIGINT_DEF)
    var person: Person? = null
) : IdentifiedEntity() {


    companion object {
        private const val BIGINT_DEF = "bigint"
        private const val serialVersionUID = 1L
    }
}