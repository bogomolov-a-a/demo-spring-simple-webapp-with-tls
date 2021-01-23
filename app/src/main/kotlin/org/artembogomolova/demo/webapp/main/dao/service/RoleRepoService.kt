package org.artembogomolova.demo.webapp.main.dao.service

import org.artembogomolova.demo.webapp.main.dao.repo.IRoleRepository
import org.artembogomolova.demo.webapp.main.domain.auth.PredefinedUserRole
import org.artembogomolova.demo.webapp.main.domain.auth.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors

@Component
class RoleRepoService {

    @Autowired
    lateinit var userRoleRepository: IRoleRepository

    fun fillAuthoritiesAndRoles() {
        val roleList: List<Role> = Arrays.stream(PredefinedUserRole.values()).map { predefinedUserRole: PredefinedUserRole -> Role(predefinedUserRole) }
            .collect(Collectors.toList())
        userRoleRepository.saveAll(roleList)
    }
}