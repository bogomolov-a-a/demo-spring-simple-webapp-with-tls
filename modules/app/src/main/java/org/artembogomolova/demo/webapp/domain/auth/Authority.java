package org.artembogomolova.demo.webapp.domain.auth;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"roles", "users"})
@EqualsAndHashCode
public class Authority extends IdentifiedEntity implements GrantedAuthority {

  private static final Long serialVersionUID = 1L;
  private String name;
  @ManyToMany(mappedBy = "authorities")
  private List<Role> roles = new ArrayList<>();
  @ManyToMany(mappedBy = "blockAuthorities")
  private List<User> users = new ArrayList<>();

  public Authority(String name) {
    this.name = name;
  }

  @Override
  public String getAuthority() {
    return name;
  }
}
