package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.core.PhysicalAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPhysicalAddressRepository extends CrudRepository<PhysicalAddress, Long> {

}
