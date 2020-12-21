package org.artembogomolova.demo.webapp.domain.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.repo.core.IPersonRepository;
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;
import org.artembogomolova.demo.webapp.domain.ConstraintPatterns;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.auth.User;
import org.artembogomolova.demo.webapp.domain.business.Order;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;

@Entity
@Table(name = Person.PERSONS_TABLE)
@NoArgsConstructor
@Getter
@ToString(callSuper = true,
    onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@UniqueMultiColumn(repository = IPersonRepository.class,
    constraints = {
        @UniqueMultiColumnConstraint(name = IdentifiedEntity.BASIC_CONSTRAINT_NAME,
            columnNames = {Person_.NAME,
                Person_.SURNAME,
                Person_.PATRONYMIC,
                Person_.BIRTH_DATE}),
        @UniqueMultiColumnConstraint(name = Person.PHONE_CONSTRAINT_NAME,
            columnNames = {Person_.PHONE}),
        @UniqueMultiColumnConstraint(name = Person.EMAIL_CONSTRAINT_NAME,
            columnNames = {Person_.EMAIL})}
)
public class Person extends IdentifiedEntity {

  public static final String PHONE_CONSTRAINT_NAME = "phoneConstraint";
  public static final String EMAIL_CONSTRAINT_NAME = "emailConstraint";
  private static final String ESTATE_ADDRESS_ID_COLUMN = "estate_address_id";
  static final String PERSONS_TABLE = "persons";
  @NotBlank
  @EqualsAndHashCode.Include
  @ToString.Include
  @Setter
  private String name;
  @NotBlank
  @ToString.Include
  @EqualsAndHashCode.Include
  @Setter
  private String surname;
  @NotBlank
  @ToString.Include
  @EqualsAndHashCode.Include
  @Setter
  private String patronymic;
  @PastOrPresent
  @Temporal(TemporalType.TIMESTAMP)
  @ToString.Include
  @EqualsAndHashCode.Include
  @NotNull
  private Date birthDate;
  @NotBlank
  @Pattern(regexp = ConstraintPatterns.PHONE_PATTERN)
  @ToString.Include
  @Setter
  @EqualsAndHashCode.Include
  private String phone;
  @NotBlank
  @Email
  @ToString.Include
  @Setter
  @EqualsAndHashCode.Include
  private String email;
  @NotNull
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = ESTATE_ADDRESS_ID_COLUMN, columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  @ToString.Include
  @Setter
  private PhysicalAddress estateAddress;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH},
      mappedBy = "person",
      orphanRemoval = true)
  private List<Order> orders = new ArrayList<>();
  @OneToOne
  @JoinColumn(name = "id")
  @Setter
  private User user;

  public Person(Person copyingEntity) {
    this.setName(copyingEntity.getName());
    this.setSurname(copyingEntity.getSurname());
    this.setPatronymic(copyingEntity.getPatronymic());
    this.setBirthDate(copyingEntity.getBirthDate());
    this.setPhone(copyingEntity.getPhone());
    this.setEmail(copyingEntity.getEmail());
    this.setEstateAddress(copyingEntity.getEstateAddress());
  }

  public Date getBirthDate() {
    return birthDate == null ? null : new Date(birthDate.getTime());
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate == null ? null : new Date(birthDate.getTime());
  }
}
