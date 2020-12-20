package org.artembogomolova.demo.webapp.domain.business;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.repo.business.ICategoryRepository;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@UniqueMultiColumn(repository = ICategoryRepository.class,
    constraints = @UniqueMultiColumnConstraint(name = IdentifiedEntity.BASIC_CONSTRAINT_NAME,
        columnNames = {Category_.NAME, Category_.PARENT_CATEGORY_ID}))
public class Category extends IdentifiedEntity {

  @Setter
  @EqualsAndHashCode.Include
  @ToString.Include
  private String name;
  @Setter
  @EqualsAndHashCode.Include
  @ToString.Include
  private Long parentCategoryId;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true, mappedBy = "category")
  private List<Good> goods = new ArrayList<>();
  @OneToOne(mappedBy = "category", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
  @Setter
  private Action action;

  public Category(Category category) {
    this.setName(category.getName());
    this.setParentCategoryId(category.getParentCategoryId());
    /*copy goods from another category, example "Royal canin Urinary S/O for...(cats,dogs)". It's goods for different categories, but with alike names and
     * one producer.*/
    this.getGoods().addAll(goods.stream().map(Good::new).collect(Collectors.toList()));
    /*alike action copy.*/
    this.setAction(category.getAction() == null ? null : new Action(category.getAction()));
  }
}
