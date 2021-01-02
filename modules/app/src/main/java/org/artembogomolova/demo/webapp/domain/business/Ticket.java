package org.artembogomolova.demo.webapp.domain.business;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;

@Entity
@Table(name = "tickets")
@Data
public class Ticket extends IdentifiedEntity {

  private static final Long serialVersionUID = 1L;
  private Float summ;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "order_id", columnDefinition = "bigint")
  private Order order;
}
