package org.artembogomolova.demo.webapp.test.domain.dao.repo;

import java.util.ArrayList;
import java.util.List;
import org.artembogomolova.demo.webapp.dao.repo.IPhysicalAddressRepository;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class PhysicalAddressRepositoryTest extends AbstractDaoTest<PhysicalAddress> {

  @Autowired
  private IPhysicalAddressRepository physicalAddressRepository;

  protected PhysicalAddressRepositoryTest() {
    super(PhysicalAddress.class);
  }

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
    result.add(DomainTestUtil.buildTestAddress());
    return result;
  }

  @Override
  protected PhysicalAddress doDuplicateDeniedTestEntity(UniqueMultiColumnConstraintColumns columns) {
    return null;
  }
}
