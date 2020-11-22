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
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;

@Entity
@Table(name = "actions")
@Getter
@Setter
@ToString(exclude = {"category", "good"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Action extends IdentifiedEntity {

  @EqualsAndHashCode.Include
  @Column(unique = true)
  private String name;
  private String description;
  @Temporal(TemporalType.TIMESTAMP)
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

  public Date getStartDate() {
    if (startDate == null) {
      return null;
    }
    return new Date(startDate.getTime());
  }

  public void setStartDate(Date startDate) {
    this.startDate = new Date(startDate.getTime());
  }

  public Date getEndDate() {
    if (endDate == null) {
      return null;
    }
    return new Date(endDate.getTime());
  }

  public void setEndDate(Date endDate) {
    this.endDate = new Date(endDate.getTime());
  }
}
