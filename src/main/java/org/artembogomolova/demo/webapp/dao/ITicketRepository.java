package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface ITicketRepository extends CrudRepository<Ticket,Long> {

}
