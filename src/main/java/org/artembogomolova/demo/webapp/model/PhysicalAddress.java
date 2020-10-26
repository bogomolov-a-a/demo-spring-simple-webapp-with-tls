package org.artembogomolova.demo.webapp.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="addresses")
@Getter
@Setter
@NoArgsConstructor
public class PhysicalAddress implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic
  private Long id;
  private String postalCode;
  private String country;
  private String city;
  private String district;
  private String street;
  private String house;
  private Integer room;
  private String specificPart;
}
