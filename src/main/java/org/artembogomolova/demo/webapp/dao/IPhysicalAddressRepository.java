package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.core.PhysicalAddress;
import org.springframework.data.repository.CrudRepository;

public interface IPhysicalAddressRepository extends CrudRepository<PhysicalAddress, Long> {

}
