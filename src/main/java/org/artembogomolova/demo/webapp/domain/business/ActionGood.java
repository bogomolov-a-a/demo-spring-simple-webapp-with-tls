package org.artembogomolova.demo.webapp.domain.business;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
  @JoinColumn(name = "action_id", columnDefinition = "bigint")
  private Action action;
  @ManyToOne
  @JoinColumn(name = "good_id", columnDefinition = "bigint")
  private Good good;
}
