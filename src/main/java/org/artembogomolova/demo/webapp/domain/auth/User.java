package org.artembogomolova.demo.webapp.domain.auth;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.core.Person;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = {"person", "role"})
public class User extends IdentifiedEntity {

  private String login;
  private String password;
  private String clientCertificateData;
  private String avatar;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE})
  @JoinColumn(name = "role_id", columnDefinition = "bigint")
  private Role role;
  @Column(insertable = false)
  private Boolean active;
  @OneToOne
  @JoinColumn(name = "person_id", columnDefinition = "bigint")
  private Person person;
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE})
  @JoinTable(name = "block_authorities",
      joinColumns = {@JoinColumn(name = "user_id", columnDefinition = "bigint")},
      inverseJoinColumns = {@JoinColumn(name = "authority_id", columnDefinition = "bigint")})
  private List<Authority> blockAuthorities = new ArrayList<>();
}
