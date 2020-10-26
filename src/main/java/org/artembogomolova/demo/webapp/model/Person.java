package org.artembogomolova.demo.webapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="persons")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Person implements Serializable {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic
  @Column(columnDefinition = "integer not null primary key autoincrement")
  private Long id;
  private String name;
  private String surname;
  private String patronymic;
  @Column(columnDefinition = "numberic")
  private Date birthDate;
  private String phone;
  @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.DETACH})
  @JoinColumn(name="estate_address_id",columnDefinition = "bigint")
  private PhysicalAddress estateAddress;
  @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.DETACH})
  @JoinColumn(name = "person_id",columnDefinition = "bigint")
  private Set<Order> orders;

  public Date getBirthDate() {
    if(birthDate==null)
    {
      return null;
    }
    return new Date(birthDate.getTime());
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = new Date(birthDate.getTime());
  }
}
