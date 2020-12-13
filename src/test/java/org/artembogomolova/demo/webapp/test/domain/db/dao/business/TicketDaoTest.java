package org.artembogomolova.demo.webapp.test.domain.db.dao.business;

import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.domain.business.Ticket;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Dao test: Ticket")
class TicketDaoTest extends AbstractDaoTest<Ticket> {

  TicketDaoTest() {
    super(Ticket.class, Ticket::new);
  }

  @Override
  protected List<Ticket> updateEntities(List<Ticket> savedCollection) {
    return null;
  }

  @Override
  protected CrudRepository<Ticket, Long> getCrudRepository() {
    return null;
  }

  @Override
  protected List<Ticket> generateEntities() {
    return null;
  }

  @Override
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected Ticket doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint, Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected Ticket doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
    return null;
  }
}
