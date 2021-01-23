package org.artembogomolova.demo.webapp.main.dao.repo

import org.artembogomolova.demo.webapp.main.domain.core.PhysicalAddress
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IPhysicalAddressRepository : CrudRepository<PhysicalAddress?, Long?>