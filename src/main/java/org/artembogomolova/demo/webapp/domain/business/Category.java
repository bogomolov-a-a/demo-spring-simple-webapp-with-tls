package org.artembogomolova.demo.webapp.domain.business;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@ToString(exclude = {"goods", "actions"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category extends IdentifiedEntity {

  @Setter
  @EqualsAndHashCode.Include
  @Column(unique = true)
  private String name;
  @Setter
  @EqualsAndHashCode.Include
  @Column(unique = true)
  private Long parentCategoryId;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true, mappedBy = "producer")
  private List<Good> goods = new ArrayList<>();
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true, mappedBy = "good")
  private List<Action> actions = new ArrayList<>();
}
