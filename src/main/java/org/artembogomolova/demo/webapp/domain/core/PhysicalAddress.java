package org.artembogomolova.demo.webapp.domain.core;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import org.artembogomolova.demo.webapp.dao.repo.core.IPhysicalAddressRepository;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;

@Entity
@Table(name = "addresses")
@Data
@UniqueMultiColumn(repository = IPhysicalAddressRepository.class,
    constraints = {
        @UniqueMultiColumnConstraint(name = IdentifiedEntity.BASIC_CONSTRAINT_NAME,
            columnNames = {"postalCode", "country", "state", "city", "district", "street", "house", "room", "specificPart"})
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
