package org.artembogomolova.demo.webapp.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="categories")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category  implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic
  @Column(columnDefinition = "integer not null primary key autoincrement")
  private Long id;
  private String name;
  private Integer parentCategoryId;
}
