package org.artembogomolova.demo.webapp.domain.auth;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.repo.auth.IAuthorityRepository;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
@NoArgsConstructor
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@UniqueMultiColumn(repository = IAuthorityRepository.class,
    constraints = @UniqueMultiColumnConstraint(name = IdentifiedEntity.BASIC_CONSTRAINT_NAME,
        columnNames = {Authority_.NAME}))
public class Authority extends IdentifiedEntity implements GrantedAuthority {

  @NotBlank
  @Setter
  @ToString.Include
  @EqualsAndHashCode.Include
  private String name;
  @NotBlank
  @ToString.Include
  @Setter
  private String description;
  @ManyToMany(mappedBy = "authorities")
  private List<Role> roles = new ArrayList<>();
  @ManyToMany(mappedBy = "blockAuthorities")
  private List<User> users = new ArrayList<>();

  public Authority(String name) {
    this.name = name;
    this.description = name;
  }

  @Override
  public String getAuthority() {
    return name;
  }

  public Authority(Authority copyingEntity) {
    this.setName(copyingEntity.getName());
    this.setDescription(copyingEntity.getDescription());
  }
}
