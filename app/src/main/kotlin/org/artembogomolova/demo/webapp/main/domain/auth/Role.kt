package org.artembogomolova.demo.webapp.main.domain.auth

import org.artembogomolova.demo.webapp.main.domain.IdentifiedEntity
import org.springframework.lang.NonNull
import java.util.stream.Collectors
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

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