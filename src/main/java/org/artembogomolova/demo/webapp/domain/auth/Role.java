package org.artembogomolova.demo.webapp.domain.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role extends IdentifiedEntity {

  private String name;
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "role_authorities",
      joinColumns = {@JoinColumn(name = "role_id", columnDefinition = "bigint")},
      inverseJoinColumns = {@JoinColumn(name = "authority_id", columnDefinition = "bigint")})
  private List<Authority> authorities = new ArrayList<>();

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true, mappedBy = "role")
  private List<User> users = new ArrayList<>();

  public Role(PredefinedUserRole predefinedUserRole) {
    this.name = predefinedUserRole.name();
    this.setId(predefinedUserRole.getId());
    authorities.addAll(predefinedUserRole.getPrivileges().stream().map(Authority::new).collect(Collectors.toList()));

  }
}
