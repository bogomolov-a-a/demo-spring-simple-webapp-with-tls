package org.artembogomolova.demo.webapp.test.domain.entity.business;

import java.util.function.Function;
import org.artembogomolova.demo.webapp.domain.business.Producer;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Producer")
class ProducerEntityTest extends AbstractAccessorEntityTest<Producer> {

  ProducerEntityTest() {
    super(Producer.class);
  }


  @Override
  protected Producer buildStandardEntity() {
    return null;
  }

  @Override
  protected Producer buildDuplicateEntity(Producer standardEntity) {
    return null;
  }

  @Override
  protected Producer buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Producer standardEntity, String constraintName, String columnName) {

  }

  @Override
  protected Function<Producer, ? extends Producer> getMockDescendantClassConstructor() {
    return MockProducer::new;
  }

  private static class MockProducer extends Producer {

    MockProducer(Producer producer) {
    }
  }
}
