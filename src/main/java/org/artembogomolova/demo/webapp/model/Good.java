package org.artembogomolova.demo.webapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="goods")
@Data
public class Good  extends IdentifiedEntity {

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
