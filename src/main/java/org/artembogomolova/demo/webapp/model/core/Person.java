package org.artembogomolova.demo.webapp.model.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.artembogomolova.demo.webapp.model.business.Order;

@Entity
@Table(name = "persons")
@Data
public class Person extends IdentifiedEntity {

  private String name;
  private String surname;
  private String patronymic;
  @Temporal(TemporalType.TIMESTAMP)
  private Date birthDate;
  private String phone;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "estate_address_id", columnDefinition = "bigint")
  private PhysicalAddress estateAddress;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH},
      mappedBy = "person",
      orphanRemoval = true)
  private List<Order> orders = new ArrayList<>();

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
