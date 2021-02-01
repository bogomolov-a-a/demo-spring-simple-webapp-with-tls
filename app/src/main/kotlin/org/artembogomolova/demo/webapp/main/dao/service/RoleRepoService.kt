package org.artembogomolova.demo.webapp.main.dao.service

import java.util.Arrays
import java.util.stream.Collectors
import org.artembogomolova.demo.webapp.main.dao.repo.IRoleRepository
import org.artembogomolova.demo.webapp.main.domain.auth.PredefinedUserRole
import org.artembogomolova.demo.webapp.main.domain.auth.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RoleRepoService @Autowired constructor(
    private val userRoleRepository: IRoleRepository
) {

    fun fillAuthoritiesAndRoles() {
        val roleList: List<Role> = Arrays.stream(PredefinedUserRole.values()).map { predefinedUserRole: PredefinedUserRole -> Role.from(predefinedUserRole) }
            .collect(Collectors.toList())
        userRoleRepository.saveAll(roleList)
    }
}