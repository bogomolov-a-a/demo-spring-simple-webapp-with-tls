package org.artembogomolova.demo.webapp.domain.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.repo.business.IActionRepository;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;

@Entity
@Table(name = "actions")
@Getter
@ToString(exclude = {"category", "goods"}, callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@UniqueMultiColumnConstraint(repository = IActionRepository.class,
    constraints = {@UniqueMultiColumnConstraintColumns(name = Action.BASIC_CONSTRAINT_NAME,
        value = {Action.NAME_FIELD_NAME, Action.START_DATE_FIELD_NAME}
    )}
)
public class Action extends IdentifiedEntity {

  public static final String BASIC_CONSTRAINT_NAME = "basicConstraint";
  public static final String NAME_FIELD_NAME = "name";
  public static final String START_DATE_FIELD_NAME = "startDate";
  @Setter
  @EqualsAndHashCode.Include
  private String name;
  @Setter
  private String description;
  @Temporal(TemporalType.TIMESTAMP)
  @EqualsAndHashCode.Include
  private Date startDate;
  @Temporal(TemporalType.TIMESTAMP)
  private Date endDate;
  @Setter
  private Float discountFixed;
  @Setter
  private Float discountPercent;
  @OneToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "category_id", columnDefinition = "bigint")
  @Setter
  private Category category;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "action")
  private List<Good> goods = new ArrayList<>();
  @Column(name = "category_id", columnDefinition = "bigint", insertable = false, updatable = false)
  @Setter
  private Long categoryId;

  public Action(Action copyingEntity) {
    super(copyingEntity);
    this.setName(copyingEntity.getName());
    this.setCategoryId(copyingEntity.getCategoryId());
    this.setDescription(copyingEntity.getDescription());
    this.setDiscountFixed(copyingEntity.getDiscountFixed());
    this.setDiscountPercent(copyingEntity.getDiscountPercent());
    this.setStartDate(copyingEntity.getStartDate());
    this.setEndDate(copyingEntity.getEndDate());
    if(copyingEntity.getCategory()!=null) {
      this.setCategory(new Category(copyingEntity.getCategory()));
    }
    copyingEntity.getGoods().forEach(good->
    {
      this.goods.add(new Good(good));
    });
  }

  public Date getStartDate() {
    return startDate != null ? new Date(startDate.getTime()) : null;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate != null ? new Date(startDate.getTime()) : null;
  }

  public Date getEndDate() {
    return endDate != null ? new Date(endDate.getTime()) : null;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate != null ? new Date(endDate.getTime()) : null;
  }
}
