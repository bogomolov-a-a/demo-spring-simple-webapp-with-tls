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
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;

@Entity
@Table(name = "goods")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString(exclude = {"producer", "category", "actions", "orderPositions"})
public class Good extends IdentifiedEntity {

  private String name;
  private String description;
  private Float price;
  private String imgFilePath;
  private Float quantity;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "producer_id", columnDefinition = "bigint")
  private Producer producer;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "category_id", columnDefinition = "bigint")
  private Category category;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true, mappedBy = "good")
  private List<Action> actions = new ArrayList<>();
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true, mappedBy = "good")
  private List<OrderPosition> orderPositions = new ArrayList<>();
}
