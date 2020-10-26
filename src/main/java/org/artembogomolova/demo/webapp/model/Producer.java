package org.artembogomolova.demo.webapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="producers")
@Data
public class Producer extends IdentifiedEntity{

  private String name;
  @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.DETACH})
  @JoinColumn(name="producer_address_id",columnDefinition = "bigint")
  private PhysicalAddress address;
}
