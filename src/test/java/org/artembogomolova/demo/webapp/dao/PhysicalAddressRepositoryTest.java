package org.artembogomolova.demo.webapp.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.artembogomolova.demo.webapp.model.PhysicalAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class PhysicalAddressRepositoryTest extends AbstractDaoTest<PhysicalAddress>{

  @Autowired
  private IPhysicalAddressRepository physicalAddressRepository;
  @Override
  protected Collection<PhysicalAddress> updateEntities(Collection<PhysicalAddress> savedCollection) {
    PhysicalAddress address = ((List<PhysicalAddress>) savedCollection).get(0);
    address.setCity("City2");
    address.setHouse("42/8544");
    return savedCollection;
  }

  @Override
  protected CrudRepository<PhysicalAddress, Long> getCrudRepository() {
    return physicalAddressRepository;
  }

  @Override
  protected Collection<PhysicalAddress> generateEntities() {
    Collection<PhysicalAddress>  result=new ArrayList<>();
    result.add(RepositoryTestUtil.buildTestAddress());
    return result;
  }
}
