package org.artembogomolova.demo.webapp.dao;

import java.util.ArrayList;
import java.util.List;
import org.artembogomolova.demo.webapp.model.core.PhysicalAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class PhysicalAddressRepositoryTest extends AbstractDaoTest<PhysicalAddress> {

  @Autowired
  private IPhysicalAddressRepository physicalAddressRepository;

  @Override
  protected List<PhysicalAddress> updateEntities(List<PhysicalAddress> savedCollection) {
    PhysicalAddress address = savedCollection.get(0);
    address.setCity("City2");
    address.setHouse("42/8544");
    return savedCollection;
  }

  @Override
  protected CrudRepository<PhysicalAddress, Long> getCrudRepository() {
    return physicalAddressRepository;
  }

  @Override
  protected List<PhysicalAddress> generateEntities() {
    List<PhysicalAddress> result = new ArrayList<>();
    result.add(RepositoryTestUtil.buildTestAddress());
    return result;
  }
}
