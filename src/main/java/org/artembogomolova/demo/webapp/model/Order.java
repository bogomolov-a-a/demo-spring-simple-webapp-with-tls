package org.artembogomolova.demo.webapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders")
@NoArgsConstructor
@Getter
@Setter
public class Order implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic
  private Long id;
  @Column(columnDefinition = "numberic")
  private Date orderDate;
  @Column(columnDefinition = "numberic")
  private Date deliverDate;
  private String orderAddressPlain;
  @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.DETACH})
  @JoinColumn(name="orderAddressId")
  private PhysicalAddress address;
  private String description;
  @Column(columnDefinition = "integer")
  private boolean payed;
  @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REMOVE},mappedBy = "order")
  private Set<Ticket> tickets;
  @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REMOVE},mappedBy = "order")
  private Set<OrderPosition> orderPositions;
}
