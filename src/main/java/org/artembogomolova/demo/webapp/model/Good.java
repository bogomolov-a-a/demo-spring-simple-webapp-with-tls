package org.artembogomolova.demo.webapp.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="goods")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Good  implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic
  @Column(columnDefinition = "integer not null primary key autoincrement")
  private Long id;
  private String name;
  private String description;
  private Float price;
  private String imgFilePath;
  private Float quantity;
  @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.DETACH})
  @JoinColumn(name = "producer_id",columnDefinition = "bigint")
  private Producer producer;
  @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.DETACH})
  @JoinColumn(name = "category_id",columnDefinition = "bigint")
  private Category category;
}
