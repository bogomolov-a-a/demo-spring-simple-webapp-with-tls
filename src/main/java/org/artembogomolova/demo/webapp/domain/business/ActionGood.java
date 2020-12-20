package org.artembogomolova.demo.webapp.domain.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.repo.business.IActionGoodRepository;
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;

@Entity
@Table(name = "action_goods")
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@UniqueMultiColumn(repository = IActionGoodRepository.class,
    constraints = @UniqueMultiColumnConstraint(name = IdentifiedEntity.BASIC_CONSTRAINT_NAME,
        columnNames = {ActionGood_.ACTION, ActionGood_.GOOD}
    ))
public class ActionGood extends IdentifiedEntity {

  @ToString.Include
  @Column(insertable = false)
  private Float quantity;
  @ToString.Include
  @Column(insertable = false)
  private Float sharePrice;

  @ManyToOne
  @JoinColumn(name = "action_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  @EqualsAndHashCode.Include
  private Action action;
  @ManyToOne
  @JoinColumn(name = "good_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  @EqualsAndHashCode.Include
  private Good good;

  public ActionGood(ActionGood actionGood) {
    this.setQuantity(actionGood.getQuantity());
    this.setSharePrice(actionGood.getSharePrice());
    /*goods do not copy*/
    this.setGood(actionGood.getGood());
    /*create new action for new actioned good*/
    this.setAction(actionGood.getAction() == null ? null : new Action(actionGood.getAction()));
  }
}
