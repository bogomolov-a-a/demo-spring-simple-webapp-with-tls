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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.repo.core.IPersonRepository;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.auth.User;
import org.artembogomolova.demo.webapp.domain.business.Order;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;

@Entity
@Table(name = "persons")
@Getter
@Setter
@ToString(exclude = {"estateAddress", "orders", "user"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@UniqueMultiColumnConstraint(repository = IPersonRepository.class,
    constraints = {
        @UniqueMultiColumnConstraintColumns(name = Person.BASIC_CONSTRAINT_NAME,
            value = {"name", "surname", "patronymic", "birthDate"}),
        @UniqueMultiColumnConstraintColumns(name = Person.PHONE_CONSTRAINT_NAME,
            value = {"phone"})}
)
public class Person extends IdentifiedEntity {

  public static final String BASIC_CONSTRAINT_NAME = "basicConstraint";
  public static final String PHONE_CONSTRAINT_NAME = "phoneConstraint";
  @EqualsAndHashCode.Include
  private String name;
  @EqualsAndHashCode.Include
  private String surname;
  @EqualsAndHashCode.Include
  private String patronymic;
  @Temporal(TemporalType.TIMESTAMP)
  @EqualsAndHashCode.Include
  private Date birthDate;
  private String phone;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "estate_address_id", columnDefinition = "bigint")
  private PhysicalAddress estateAddress;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH},
      mappedBy = "person",
      orphanRemoval = true)
  private List<Order> orders = new ArrayList<>();
  @OneToOne
  @JoinColumn(name = "id")
  private User user;

  public Date getBirthDate() {
    if (birthDate == null) {
      return null;
    }
    return new Date(birthDate.getTime());
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = new Date(birthDate.getTime());
  }
}
