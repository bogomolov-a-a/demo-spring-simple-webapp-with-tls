package org.artembogomolova.demo.webapp.domain.business;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;

@Entity
@Table(name = "order_positions")
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderPosition extends IdentifiedEntity {

  @ManyToOne()
  @JoinColumn(name = "order_id", columnDefinition = "bigint")
  private Order order;
  @OneToOne()
  @JoinColumn(name = "order_goods_id", columnDefinition = "bigint")
  private OrderGood orderGood;

}
