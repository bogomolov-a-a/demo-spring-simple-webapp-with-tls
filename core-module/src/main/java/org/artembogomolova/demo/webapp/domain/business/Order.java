package org.artembogomolova.demo.webapp.domain.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.domain.core.Person;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"person", "tickets", "orderPositions"})
public class Order extends IdentifiedEntity {

  @Temporal(TemporalType.TIMESTAMP)
  @Column(insertable = false)
  private Date orderDate;
  @Temporal(TemporalType.TIMESTAMP)
  private Date deliverDate;
  private String orderAddressPlain;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "order_address_id", columnDefinition = "bigint")
  private PhysicalAddress address;
  private String description;
  @Column(columnDefinition = "integer")
  private boolean payed;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE}, mappedBy = "order", orphanRemoval = true)
  private List<Ticket> tickets = new ArrayList<>();
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE}, mappedBy = "order", orphanRemoval = true)
  private List<OrderPosition> orderPositions = new ArrayList<>();
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
  @JoinColumn(name = "person_id", columnDefinition = "bigint")
  private Person person;

  public Date getOrderDate() {
    if (orderDate == null) {
      return null;
    }
    return new Date(orderDate.getTime());
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = new Date(orderDate.getTime());
  }

  public Date getDeliverDate() {
    if (deliverDate == null) {
      return null;
    }
    return new Date(deliverDate.getTime());
  }

  public void setDeliverDate(Date deliverDate) {
    this.deliverDate = new Date(deliverDate.getTime());
  }
}
