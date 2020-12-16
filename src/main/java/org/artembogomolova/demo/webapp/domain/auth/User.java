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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.core.Person;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User extends IdentifiedEntity {

  private String login;
  private String password;
  private String clientCertificateData;
  private String avatar;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE})
  @JoinColumn(name = "role_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  private Role role;
  @Column(insertable = false)
  private Boolean active;
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
  @JoinColumn(name = "person_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  private Person person;
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE})
  @JoinTable(name = "block_authorities",
      joinColumns = {@JoinColumn(name = "user_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)},
      inverseJoinColumns = {@JoinColumn(name = "authority_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)})
  private List<Authority> blockAuthorities = new ArrayList<>();

  public User(User user) {
    this.setLogin(user.getLogin());
    this.setPassword(user.getPassword());
    this.setActive(user.getActive());
    this.setClientCertificateData(user.getClientCertificateData());
    this.setAvatar(user.getAvatar());
    /*role set as is*/
    this.setRole(user.getRole());
    /*person copy*/
    this.setPerson(new Person(user.getPerson()));

  }
}
