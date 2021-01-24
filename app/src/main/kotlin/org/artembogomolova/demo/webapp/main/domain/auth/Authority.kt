package org.artembogomolova.demo.webapp.main.domain.auth

import org.artembogomolova.demo.webapp.main.domain.IdentifiedEntity
import org.springframework.security.core.GrantedAuthority
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "authorities")
class Authority(
    @Column(nullable = false)
    val name: String,
    @ManyToMany(mappedBy = "authorities")
    var roles: MutableList<Role> = mutableListOf(),
    @ManyToMany(mappedBy = "blockAuthorities")
    val users: MutableList<User> = mutableListOf()
) : IdentifiedEntity(), GrantedAuthority {


    override fun getAuthority(): String? {
        return name
    }

    override fun toString(): String = name

    companion object {
        private const val serialVersionUID = 1L
    }
}