package org.artembogomolova.demo.webapp.domain.core;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@ToString
@NoArgsConstructor
@Getter
@Setter
public class IdentifiedEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic
  @Column(columnDefinition = "integer not null primary key autoincrement")
  private Long id;

  public IdentifiedEntity(IdentifiedEntity copyingEntity) {
    this.id = copyingEntity.getId();
  }
}
