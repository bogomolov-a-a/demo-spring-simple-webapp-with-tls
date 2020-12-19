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
import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.repo.auth.IUserRepository;
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.core.Person;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@UniqueMultiColumn(repository = IUserRepository.class,
    constraints = {@UniqueMultiColumnConstraint(name = IdentifiedEntity.BASIC_CONSTRAINT_NAME,
        columnNames = {User_.LOGIN}),
        @UniqueMultiColumnConstraint(name = User.CERTIFICATE_DATA_CONSTRAINT_NAME,
            columnNames = {User_.CLIENT_CERTIFICATE_DATA})})
public class User extends IdentifiedEntity {

  public static final String CERTIFICATE_DATA_CONSTRAINT_NAME = "certificateDataConstraint";
  @NotBlank
  @EqualsAndHashCode.Include
  @ToString.Include
  @Setter
  private String login;
  @NotBlank
  @ToString.Include
  @Setter
  private String password;
  @NotBlank
  @EqualsAndHashCode.Include
  @ToString.Include
  @Setter
  private String clientCertificateData;
  @ToString.Include
  @Setter
  private String avatar;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE})
  @JoinColumn(name = "role_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  @Setter
  private Role role;
  @Setter
  @Column(insertable = false)
  private Boolean active;
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
  @JoinColumn(name = "person_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  @Setter
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
    this.setPerson(user.getPerson() == null ? null : new Person(user.getPerson()));

  }
}
