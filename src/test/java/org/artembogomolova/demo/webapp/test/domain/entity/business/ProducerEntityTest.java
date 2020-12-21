package org.artembogomolova.demo.webapp.test.domain.entity.business;

import lombok.EqualsAndHashCode;
import org.artembogomolova.demo.webapp.domain.business.Good;
import org.artembogomolova.demo.webapp.domain.business.Producer;
import org.artembogomolova.demo.webapp.domain.business.Producer_;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Producer")
class ProducerEntityTest extends AbstractAccessorEntityTest<Producer> {


  private static final String NAME_VALUE = "test producer";
  private static final String CONTACT_PHONE_VALUE = "+7-812-000-00-81";
  private static final String UNIQUE_CODE_VALUE = "13b180df-7504-4acf-a7f6-b0d65f7e36d2";
  private static final PhysicalAddress ADDRESS_VALUE = new PhysicalAddress();

  ProducerEntityTest() {
    super(Producer.class,
        Producer::new,
        MockProducer::new);
  }


  @Override
  protected Producer buildStandardEntity() {
    Producer result = new Producer();
    result.setName(NAME_VALUE);
    result.setContactPhone(CONTACT_PHONE_VALUE);
    result.setAddress(ADDRESS_VALUE);
    result.setUniqueCode(UNIQUE_CODE_VALUE);
    return result;
  }

  @Override
  protected void containFieldCorrectValuesTest(Producer standardEntity) {
    Assertions.assertEquals(NAME_VALUE, standardEntity.getName());
    Assertions.assertEquals(CONTACT_PHONE_VALUE, standardEntity.getContactPhone());
    Assertions.assertEquals(ADDRESS_VALUE, standardEntity.getAddress());
    Assertions.assertEquals(UNIQUE_CODE_VALUE, standardEntity.getUniqueCode());
    Assertions.assertTrue(standardEntity.getGoods().isEmpty());
  }

  @Override
  protected Producer buildAnotherEntityForTest() {
    return DomainTestUtil.buildProducer();
  }

  @Override
  protected boolean withoutBasicConstraint(Producer standardEntity, String columnName) {
    if (Producer_.UNIQUE_CODE.equals(columnName)) {
      withoutColumnEqualTest(standardEntity, Producer::getUniqueCode, Producer::setUniqueCode);
      return true;
    }
    return false;
  }

  @EqualsAndHashCode(callSuper = false)
  private static class MockProducer extends Producer {

    MockProducer(Producer producer) {
      super(buildCopyingProducer(producer));
    }

    private static Producer buildCopyingProducer(Producer producer) {
      producer.getGoods().add(new Good());
      return producer;
    }
  }
}
