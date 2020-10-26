package org.artembogomolova.demo.webapp.model;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="actions")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Action {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic
  private Long id;
  private String name;
  private String description;
  @Column(columnDefinition = "numberic")
  private Date startDate;
  @Column(columnDefinition = "numberic")
  private Date endDate;
  private Float discountFixed;
  private Float discountPercent;
  @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.DETACH})
  @JoinColumn(name="category_id")
  private Category category;
  @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.DETACH})
  @JoinColumn(name="good_id")
  private Good good;
}
