package org.artembogomolova.demo.webapp.main.dao.repo

import org.artembogomolova.demo.webapp.main.domain.core.Person
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IPersonRepository : CrudRepository<Person?, Long?>