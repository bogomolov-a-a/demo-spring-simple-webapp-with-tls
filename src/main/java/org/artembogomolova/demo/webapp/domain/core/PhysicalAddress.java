package org.artembogomolova.demo.webapp.domain.core;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import org.artembogomolova.demo.webapp.dao.repo.core.IPhysicalAddressRepository;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;

@Entity
@Table(name = "addresses")
@Data
@UniqueMultiColumnConstraint(repository = IPhysicalAddressRepository.class,
    constraints = {
        @UniqueMultiColumnConstraintColumns(name = PhysicalAddress.BASIC_CONSTRAINT_NAME,
            value = {"postalCode", "country", "state", "city", "district", "street", "house", "room", "specificPart"})
    })
public class PhysicalAddress extends IdentifiedEntity {

  public static final String BASIC_CONSTRAINT_NAME = "basicConstraint";
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
