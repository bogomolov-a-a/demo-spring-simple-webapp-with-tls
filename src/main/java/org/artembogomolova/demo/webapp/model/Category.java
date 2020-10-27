package org.artembogomolova.demo.webapp.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="categories")
@Data
public class Category  extends IdentifiedEntity {

  private String name;
  private Long parentCategoryId;
  @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.DETACH},orphanRemoval = true,mappedBy = "producer")
  private List<Good> goods=new ArrayList<>();
}
