package org.artembogomolova.demo.webapp.main.domain.auth

import org.artembogomolova.demo.webapp.main.domain.IdentifiedEntity
import org.springframework.security.core.GrantedAuthority
import java.util.*
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "authorities")
class Authority(
    var name: String? = null,
    @ManyToMany(mappedBy = "authorities")
    var roles: List<Role> = ArrayList(),
    @ManyToMany(mappedBy = "blockAuthorities")
    var users: List<User> = ArrayList()
) : IdentifiedEntity(), GrantedAuthority {

    override fun getAuthority(): String? {
        return name
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}