package org.artembogomolova.demo.webapp.model.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.model.core.IdentifiedEntity;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "authorities")
@EqualsAndHashCode
public class Role extends IdentifiedEntity {

  private String name;
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "role_authorities",
      joinColumns = {@JoinColumn(name = "role_id", columnDefinition = "bigint")},
      inverseJoinColumns = {@JoinColumn(name = "authority_id", columnDefinition = "bigint")})
  private List<Authority> authorities = new ArrayList<>();

  public Role(PredefinedUserRole predefinedUserRole) {
    this.name = predefinedUserRole.name();
    this.setId(predefinedUserRole.getId());
    authorities.addAll(predefinedUserRole.getPrivileges().stream().map(Authority::new).collect(Collectors.toList()));

  }
}
