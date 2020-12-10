package org.artembogomolova.demo.webapp.domain.business;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;

@Entity
@Table(name = "goods")
@NoArgsConstructor
@Getter
@ToString(exclude = {"producer", "category", "actions", "orderPositions"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Good extends IdentifiedEntity {

  @Setter
  private String name;
  @Setter
  private String description;
  @Setter
  private String imgFilePath;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "producer_id", columnDefinition = "bigint")
  @Setter
  private Producer producer;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "category_id", columnDefinition = "bigint")
  @Setter
  private Category category;

  public Good(Good good) {
    super(good);
  }
}
