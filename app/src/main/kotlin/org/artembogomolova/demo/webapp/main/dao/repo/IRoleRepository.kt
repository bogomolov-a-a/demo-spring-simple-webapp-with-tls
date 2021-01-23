package org.artembogomolova.demo.webapp.main.dao.repo

import org.artembogomolova.demo.webapp.main.domain.auth.Role
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IRoleRepository : CrudRepository<Role?, Long?> {
    fun findByName(name: String?): Role?
}