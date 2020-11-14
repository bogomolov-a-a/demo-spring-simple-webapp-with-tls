package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.business.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketRepository extends CrudRepository<Ticket, Long> {

}
