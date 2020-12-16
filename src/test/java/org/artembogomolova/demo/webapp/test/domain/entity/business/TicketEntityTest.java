package org.artembogomolova.demo.webapp.test.domain.entity.business;

import org.artembogomolova.demo.webapp.domain.business.Ticket;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Ticket")
class TicketEntityTest extends AbstractAccessorEntityTest<Ticket> {

  TicketEntityTest() {
    super(Ticket.class,
        Ticket::new,
        MockTicket::new);
  }


  @Override
  protected Ticket buildStandardEntity() {
    return null;
  }

  @Override
  protected Ticket buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Ticket standardEntity, String constraintName, String columnName) {

  }

  private static class MockTicket extends Ticket {

    MockTicket(Ticket ticket) {
    }
  }
}
