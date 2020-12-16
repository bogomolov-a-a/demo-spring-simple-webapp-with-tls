package org.artembogomolova.demo.webapp.domain.business;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;

@Entity
@Table(name = "actions")
@Getter
@ToString(exclude = {"category", "goods"}, callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@UniqueMultiColumn(repository = IActionRepository.class,
    constraints = {@UniqueMultiColumnConstraint(name = IdentifiedEntity.BASIC_CONSTRAINT_NAME,
        columnNames = {}//{Action_.NAME_FIELD_NAME, Action_.START_DATE_FIELD_NAME}
    )}
)
public class Action extends IdentifiedEntity {

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
  @JoinColumn(name = "category_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  @Setter
  private Category category;
  @Column(name = "category_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION, insertable = false, updatable = false)
  @Setter
  private Long categoryId;

  public Action(Action copyingEntity) {
    this.setName(copyingEntity.getName());
    this.setCategoryId(copyingEntity.getCategoryId());
    this.setDescription(copyingEntity.getDescription());
    this.setDiscountFixed(copyingEntity.getDiscountFixed());
    this.setDiscountPercent(copyingEntity.getDiscountPercent());
    this.setStartDate(copyingEntity.getStartDate());
    this.setEndDate(copyingEntity.getEndDate());
    if (copyingEntity.getCategory() != null) {
      this.setCategory(new Category(copyingEntity.getCategory()));
    }
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
