package org.artembogomolova.demo.webapp.main.dao.repo

import org.artembogomolova.demo.webapp.main.domain.auth.Authority
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IAuthorityRepository : CrudRepository<Authority?, Long?>