package org.artembogomolova.demo.webapp.domain.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
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
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.core.Person;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order extends IdentifiedEntity {

  @Temporal(TemporalType.TIMESTAMP)
  @Column(insertable = false)
  private Date orderDate = Calendar.getInstance().getTime();
  @Temporal(TemporalType.TIMESTAMP)
  private Date deliveryDate;
  private String orderAddressPlain;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "order_address_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  private PhysicalAddress address;
  private String description;
  @Column(columnDefinition = "integer")
  private boolean payed;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE}, mappedBy = "order", orphanRemoval = true)
  private List<Ticket> tickets = new ArrayList<>();
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE}, mappedBy = "order", orphanRemoval = true)
  private List<OrderPosition> orderPositions = new ArrayList<>();
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
  @JoinColumn(name = "person_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  private Person person;

  public Order(Order order) {
    this.setOrderDate(order.getOrderDate());
    this.setOrderAddressPlain(order.getOrderAddressPlain());
    this.setDescription(order.getDescription());
    this.getOrderPositions().addAll(order.getOrderPositions().stream().map(this::createNewOrderPosition).collect(Collectors.toList()));
  }

  public Date getOrderDate() {
    return orderDate == null ? null : new Date(orderDate.getTime());
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate == null ? null : new Date(orderDate.getTime());
  }

  public Date getDeliveryDate() {
    return deliveryDate == null ? null : new Date(deliveryDate.getTime());
  }

  public void setDeliveryDate(Date deliveryDate) {
    this.deliveryDate = deliveryDate == null ? null : new Date(deliveryDate.getTime());
  }

  private OrderPosition createNewOrderPosition(OrderPosition orderPosition) {
    OrderPosition result = new OrderPosition(orderPosition);
    result.setOrder(this);
    return result;
  }
}
