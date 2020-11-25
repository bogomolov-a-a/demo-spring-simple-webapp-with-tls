package org.artembogomolova.demo.webapp.domain.business;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.repo.IActionRepository;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;

@Entity
@Table(name = "actions")
@Getter
@Setter
@ToString(exclude = {"category", "good"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@UniqueMultiColumnConstraint(repository = IActionRepository.class,
    constraints = {@UniqueMultiColumnConstraintColumns(name = Action.BASIC_CONSTRAINT,
        value = {"name", "categoryId", "goodId", "startDate"}
    )}
)
public class Action extends IdentifiedEntity {

  public static final String BASIC_CONSTRAINT = "actionUniqueConstraint";
  @EqualsAndHashCode.Include
  private String name;
  private String description;
  @Temporal(TemporalType.TIMESTAMP)
  @EqualsAndHashCode.Include
  private Date startDate;
  @Temporal(TemporalType.TIMESTAMP)
  private Date endDate;
  private Float discountFixed;
  private Float discountPercent;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "category_id", columnDefinition = "bigint")
  private Category category;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "good_id", columnDefinition = "bigint")
  private Good good;
  @EqualsAndHashCode.Include
  @Column(name = "good_id", columnDefinition = "bigint", insertable = false, updatable = false)
  private Long goodId;
  @EqualsAndHashCode.Include
  @Column(name = "category_id", columnDefinition = "bigint", insertable = false, updatable = false)
  private Long categoryId;

  public Date getStartDate() {
    if (startDate == null) {
      return null;
    }
    return new Date(startDate.getTime());
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate != null ? new Date(startDate.getTime()) : null;
  }

  public Date getEndDate() {
    if (endDate == null) {
      return null;
    }
    return new Date(endDate.getTime());
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate != null ? new Date(endDate.getTime()) : null;
  }
}
