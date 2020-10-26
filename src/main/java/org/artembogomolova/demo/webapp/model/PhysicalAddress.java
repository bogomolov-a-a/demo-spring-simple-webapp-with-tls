package org.artembogomolova.demo.webapp.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="addresses")
@Data
public class PhysicalAddress extends IdentifiedEntity {

  private String postalCode;
  private String country;
  private String state;
  private String city;
  private String district;
  private String street;
  private String house;
  private Integer room;
  private String specificPart;
}
