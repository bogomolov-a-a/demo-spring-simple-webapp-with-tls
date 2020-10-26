package org.artembogomolova.demo.webapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tickets")
@Data
public class Ticket  extends IdentifiedEntity{

  private Float summ;
  @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.DETACH})
  @JoinColumn(name="order_id",columnDefinition = "bigint")
  private Order order;
}
