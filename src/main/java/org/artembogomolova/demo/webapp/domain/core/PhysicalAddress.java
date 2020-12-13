package org.artembogomolova.demo.webapp.domain.core;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.repo.core.IPhysicalAddressRepository;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;

@Entity
@Table(name = PhysicalAddress.PHYSICAL_ADDRESSES_TABLE)
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode
@UniqueMultiColumn(repository = IPhysicalAddressRepository.class,
    constraints = {
        @UniqueMultiColumnConstraint(name = IdentifiedEntity.BASIC_CONSTRAINT_NAME,
            columnNames = {
                PhysicalAddress_.POSTAL_CODE,
                PhysicalAddress_.COUNTRY,
                PhysicalAddress_.STATE,
                PhysicalAddress_.CITY,
                PhysicalAddress_.DISTRICT,
                PhysicalAddress_.STREET,
                PhysicalAddress_.HOUSE,
                PhysicalAddress_.ROOM,
                PhysicalAddress_.SPECIFIC_PART
            })
    })
public class PhysicalAddress extends IdentifiedEntity {

  static final String PHYSICAL_ADDRESSES_TABLE = "physical_addresses";
  @NotBlank
  private String postalCode;
  @NotBlank
  private String country;
  private String state;
  @NotBlank
  private String city;
  private String district;
  private String street;
  @NotBlank
  private String house;
  private Integer room;
  private String specificPart;

  public PhysicalAddress(PhysicalAddress copyingEntity) {
    this.setPostalCode(copyingEntity.getPostalCode());
    this.setCountry(copyingEntity.getCountry());
    this.setState(copyingEntity.getState());
    this.setCity(copyingEntity.getCity());
    this.setDistrict(copyingEntity.getDistrict());
    this.setStreet(copyingEntity.getStreet());
    this.setHouse(copyingEntity.getHouse());
    this.setRoom(copyingEntity.getRoom());
    this.setSpecificPart(copyingEntity.getSpecificPart());
  }
}
