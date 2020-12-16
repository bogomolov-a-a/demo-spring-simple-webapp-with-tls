package org.artembogomolova.demo.webapp.domain.business;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;

@Entity
@Table(name = "stock_goods")
@Getter
@Setter
@NoArgsConstructor
//@ToString(exclude = {StockGood_.GOOD})
@EqualsAndHashCode
public class StockGood extends IdentifiedEntity {

  private Float price;
  private Float quantity;
  @OneToOne
  @JoinColumn(name = "good_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  private Good good;

  public StockGood(StockGood stockGood) {
    this.setQuantity(stockGood.getQuantity());
    this.setPrice(stockGood.getPrice());
    this.setGood(new Good(stockGood.getGood()));
  }
}
