package org.artembogomolova.demo.webapp.main.dao.repo

import org.artembogomolova.demo.webapp.main.domain.auth.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface IUserRepository : PagingAndSortingRepository<User?, Long?> {
    fun findByLogin(login: String?): User?
}