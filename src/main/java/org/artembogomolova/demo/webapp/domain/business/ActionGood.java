package org.artembogomolova.demo.webapp.domain.business;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;

@Entity
@Table(name = "action_goods")
@Getter
@Setter
@NoArgsConstructor
/*@ToString(exclude = {ActionGood_.ACTION,
    ActionGood_.GOOD})*/
public class ActionGood extends IdentifiedEntity {

  private Float quantity = 1f;
  private Float sharePrice = 0f;
  @ManyToOne
  @JoinColumn(name = "action_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  private Action action;
  @ManyToOne
  @JoinColumn(name = "good_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  private Good good;

  public ActionGood(ActionGood actionGood) {
    this.setQuantity(actionGood.getQuantity());
    this.setSharePrice(actionGood.getSharePrice());
    /*goods do not copy*/
    this.setGood(actionGood.getGood());
    /*create new action for new actioned good*/
    this.setAction(new Action(actionGood.getAction()));
  }
}
