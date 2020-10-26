package org.artembogomolova.demo.webapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="order_positions")
@Data

public class OrderPosition extends IdentifiedEntity {

  private Float discount;
  private Float quantity;
  @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REMOVE})
  @JoinColumn(name="order_id",columnDefinition = "bigint")
  private Order order;
  @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REMOVE})
  @JoinColumn(name="good_id",columnDefinition = "bigint")
  private Good good;
  @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REMOVE})
  @JoinColumn(name="action_id",columnDefinition = "bigint")
  private Action action;
}
