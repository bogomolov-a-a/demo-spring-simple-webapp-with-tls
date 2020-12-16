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
import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role extends IdentifiedEntity {

  @NotBlank
  @Setter
  @EqualsAndHashCode.Include
  @ToString.Include
  private String name;

  @NotBlank
  @Setter
  @ToString.Include
  private String description;
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "role_authorities",
      joinColumns = {@JoinColumn(name = "role_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)},
      inverseJoinColumns = {@JoinColumn(name = "authority_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)})
  private List<Authority> authorities = new ArrayList<>();

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true, mappedBy = "role")
  private List<User> users = new ArrayList<>();

  public Role(PredefinedUserRole predefinedUserRole) {
    String roleName = predefinedUserRole.name();
    this.setName(roleName);
    this.setDescription(roleName);
    this.setId(predefinedUserRole.getId());
    authorities.addAll(predefinedUserRole.getPrivileges().stream().map(Authority::new).collect(Collectors.toList()));

  }

  public Role(Role role) {
    this.setName(role.getName());
    this.setDescription(role.getDescription());
  }
}
