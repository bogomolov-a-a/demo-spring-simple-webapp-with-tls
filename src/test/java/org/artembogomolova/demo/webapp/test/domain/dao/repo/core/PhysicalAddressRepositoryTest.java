package org.artembogomolova.demo.webapp.test.domain.dao.repo.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.dao.repo.core.IPhysicalAddressRepository;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.dao.repo.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

@DisplayName("PhysicalAddress entity repository test")
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
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected PhysicalAddress doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint,
      Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected PhysicalAddress doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns,
      Map<String, Object> commonValues) {
    return null;
  }
}
