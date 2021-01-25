package org.artembogomolova.demo.webapp.main.domain.auth

import org.artembogomolova.demo.webapp.main.domain.IdentifiedEntity
import org.artembogomolova.demo.webapp.main.domain.core.Person
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

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