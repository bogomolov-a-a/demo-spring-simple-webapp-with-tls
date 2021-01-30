package org.artembogomolova.demo.webapp.main.dao.repo

import org.artembogomolova.demo.webapp.main.domain.auth.Authority
import org.artembogomolova.demo.webapp.main.domain.auth.Role
import org.artembogomolova.demo.webapp.main.domain.auth.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface IAuthorityRepository : CrudRepository<Authority, Long>

@Repository
interface IRoleRepository : CrudRepository<Role, Long> {
    fun findByName(name: String?): Role?
}

@Repository
interface IUserRepository : PagingAndSortingRepository<User, Long> {
    fun findByLogin(login: String?): User?
}