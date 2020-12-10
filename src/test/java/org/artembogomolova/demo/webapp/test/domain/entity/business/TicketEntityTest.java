package org.artembogomolova.demo.webapp.test.domain.entity.business;

import org.artembogomolova.demo.webapp.domain.business.Ticket;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Ticket")
class TicketEntityTest extends AbstractAccessorEntityTest<Ticket> {

  TicketEntityTest() {
    super(Ticket.class);
  }

  @Override
  protected void availableConstraint(String constraintName) {

  }

  @Override
  protected Ticket buildStandardEntityAndAccessorTest() {
    return null;
  }

  @Override
  protected Ticket buildDuplicateEntity(Ticket standardEntity) {
    return null;
  }

  @Override
  protected Ticket buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Ticket standardEntity, String constraintName, String columnName) {

  }
}
