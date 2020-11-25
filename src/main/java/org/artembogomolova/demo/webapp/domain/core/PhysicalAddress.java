package org.artembogomolova.demo.webapp.domain.core;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import org.artembogomolova.demo.webapp.dao.repo.IPhysicalAddressRepository;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;

@Entity
@Table(name = "addresses")
@Data
@UniqueMultiColumnConstraint(repository = IPhysicalAddressRepository.class,
    constraints = {
        @UniqueMultiColumnConstraintColumns({"postalCode", "country", "state", "city", "district", "street", "house", "room", "specificPart"})
    })
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
