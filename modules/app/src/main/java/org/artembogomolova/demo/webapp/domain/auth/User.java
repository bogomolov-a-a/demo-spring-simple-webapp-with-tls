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
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.core.Person;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = {"person", "role"})
public class User extends IdentifiedEntity {

  private static final String BIGINT_DEF="bigint";
  private static final Long serialVersionUID = 1L;
  private String login;
  private String password;
  private String clientCertificateData;
  private String avatar;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE})
  @JoinColumn(name = "role_id", columnDefinition = BIGINT_DEF)
  private Role role;
  @Column(insertable = false)
  private Boolean active;
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
  @JoinColumn(name = "person_id", columnDefinition = BIGINT_DEF)
  private Person person;
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE})
  @JoinTable(name = "block_authorities",
      joinColumns = {@JoinColumn(name = "user_id", columnDefinition = BIGINT_DEF)},
      inverseJoinColumns = {@JoinColumn(name = "authority_id", columnDefinition = BIGINT_DEF)})
  private List<Authority> blockAuthorities = new ArrayList<>();
}
