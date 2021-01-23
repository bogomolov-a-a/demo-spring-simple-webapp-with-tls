package org.artembogomolova.demo.webapp.main.domain.auth

import org.artembogomolova.demo.webapp.main.domain.IdentifiedEntity
import java.util.stream.Collectors
import javax.persistence.*

@Entity
@Table(name = "roles")
class Role() : IdentifiedEntity() {
    constructor(predefinedUserRole: PredefinedUserRole) : this() {
        name = predefinedUserRole.name
        id = predefinedUserRole.id
        authorities.addAll(predefinedUserRole.privileges.stream().map { name: String -> Authority(name) }.collect(Collectors.toList()))
    }

    var name: String? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "role_authorities",
        joinColumns = [JoinColumn(name = "role_id", columnDefinition = "bigint")],
        inverseJoinColumns = [JoinColumn(name = "authority_id", columnDefinition = "bigint")]
    )
    var authorities: MutableList<Authority> = ArrayList()

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH], orphanRemoval = true, mappedBy = "role")
    var users: MutableList<User> = ArrayList()

    companion object {
        private const val serialVersionUID = 1L
    }
}