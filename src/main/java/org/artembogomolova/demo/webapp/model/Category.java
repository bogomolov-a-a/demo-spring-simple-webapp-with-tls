package org.artembogomolova.demo.webapp.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="categories")
@Data
public class Category  extends IdentifiedEntity {

  private String name;
  private Long parentCategoryId;
}
