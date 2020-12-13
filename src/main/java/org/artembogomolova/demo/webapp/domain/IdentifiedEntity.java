package org.artembogomolova.demo.webapp.domain;

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
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;

@MappedSuperclass
@ToString
@NoArgsConstructor
@Getter
@Setter
public class IdentifiedEntity implements Serializable {

  public static final String BASIC_CONSTRAINT_NAME = "basicConstraint";
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic
  @Column(columnDefinition = SQLite3Dialect.IDENTITY_COLUMN_DEFINITION)
  private Long id;

  public IdentifiedEntity(IdentifiedEntity copyingEntity) {
    this.id = copyingEntity.getId();
  }
}
