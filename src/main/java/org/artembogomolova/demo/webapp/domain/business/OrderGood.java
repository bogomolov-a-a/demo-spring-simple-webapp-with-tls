package org.artembogomolova.demo.webapp.domain.business;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;

@Entity
@Table(name = "order_goods")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderGood extends IdentifiedEntity {

  private Float quantity = 0f;
  private Float effectivePrice;
  @ManyToOne()
  @JoinColumn(name = "good_id", columnDefinition = "bigint")
  private Good good;
}
