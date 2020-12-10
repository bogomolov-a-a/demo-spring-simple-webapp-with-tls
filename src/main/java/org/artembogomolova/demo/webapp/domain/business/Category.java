package org.artembogomolova.demo.webapp.domain.business;

import java.util.ArrayList;
import java.util.List;
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
@ToString(exclude = {"goods", "actions"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@UniqueMultiColumn(repository = ICategoryRepository.class,
    constraints = @UniqueMultiColumnConstraint(name = IdentifiedEntity.BASIC_CONSTRAINT_NAME,
        value = {}))//{Category.NAME_FIELD_NAME, Category.PARENT_CATEGORY_ID_NAME}))
public class Category extends IdentifiedEntity {

  @Setter
  @EqualsAndHashCode.Include
  private String name;
  @Setter
  @EqualsAndHashCode.Include
  private Long parentCategoryId;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true, mappedBy = "category")
  private List<Good> goods = new ArrayList<>();
  @OneToOne(mappedBy = "category", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
  @Setter
  private Action action;

  public Category(Category category) {
    super(category);
    this.setName(category.getName());
    this.setParentCategoryId(category.getParentCategoryId());
    this.getGoods().addAll(category.getGoods());
    this.setAction(category.getAction());
  }
}
