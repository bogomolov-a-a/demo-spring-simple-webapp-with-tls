package org.artembogomolova.demo.webapp.domain.business;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.repo.business.IGoodRepository;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;

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
  private Float price;
  @Setter
  private String imgFilePath;
  @Setter
  private Float quantity;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "producer_id", columnDefinition = "bigint")
  @Setter
  private Producer producer;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "category_id", columnDefinition = "bigint")
  @Setter
  private Category category;
  @ManyToOne()
  @JoinColumn(name = "action_id", columnDefinition = "bigint")
  @Setter
  private Action action;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true, mappedBy = "good")
  private List<OrderPosition> orderPositions = new ArrayList<>();

  public Good(Good good) {
    super(good);
  }
}
